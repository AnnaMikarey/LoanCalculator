import { Component, OnInit } from '@angular/core';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
  ValidationErrors,
} from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { EuriborService } from '../../services/euribor.service';
import { forkJoin, take } from 'rxjs';

export function checkIfLessThanZero(
  control: AbstractControl
): ValidationErrors | null {
  const value = control.value;
  if (value !== null && value <= 0) {
    return { lessThanZeroError: true };
  }
  return null;
}

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
})
export class AdminDashboardComponent implements OnInit {
  constructor(
    private adminService: AdminService,
    private euriborService: EuriborService
  ) {}

  adminForm: FormGroup;
  adminEuriborDate!: string;
  isFieldChanged = false;
  errorMsg: string;

  validatorsNum = [
    Validators.required,
    Validators.pattern(/^\d+\.?\d*$/),
    checkIfLessThanZero,
  ];
  validatorsPercent = [...this.validatorsNum, Validators.max(100)];

  ngOnInit() {
    this.adminForm = new FormGroup({
      adminEuriborRate: new FormControl(null, this.validatorsPercent),
      adminBankMargin: new FormControl(null, this.validatorsPercent),
      adminMinPropertyPrice: new FormControl(null, this.validatorsNum),
      adminMaxPropertyPrice: new FormControl(null, this.validatorsNum),
      adminDefaultPropertyPrice: new FormControl(null, this.validatorsNum),
      adminMinDepositPercent: new FormControl(null, this.validatorsPercent),
      adminContractFee: new FormControl(null, this.validatorsNum),
      adminMonthlyBankFee: new FormControl(null, this.validatorsNum),
      adminRegistrationFee: new FormControl(null, this.validatorsNum),
    });

    forkJoin({
      adminData: this.adminService.getData(),
      euriborData: this.euriborService.getRates(),
    })
      .pipe(take(1))
      .subscribe(({ adminData, euriborData }) => {
        this.adminForm.patchValue(adminData);
        const dateFromAPI =
          euriborData['non_central_bank_rates'][4]['last_updated'];
        let parts = dateFromAPI.split('-');
        this.adminEuriborDate = `${parts[2]}/${parts[0]}/${parts[1]}`;
        this.adminForm.patchValue({
          adminEuriborRate:
            euriborData['non_central_bank_rates'][4]['rate_pct'],
        });
      });
  }

  saveChanges() {
    this.adminForm.value['adminEuriborDate'] = this.adminEuriborDate;
    this.adminService.postData(this.adminForm.value).pipe(take(1)).subscribe();
    setTimeout(() => {
      this.ngOnInit();
    }, 500);
    this.isFieldChanged = false;
    this.errorMsg = null;
  }

  discardChanges() {
    this.ngOnInit();
    this.isFieldChanged = false;
    this.errorMsg = null;
  }

  getMinMaxPrices() {
    const priceMin = this.adminForm.value.adminMinPropertyPrice;
    const priceMax = this.adminForm.value.adminMaxPropertyPrice;
    return { min: priceMin, max: priceMax };
  }

  setDefaultPriceIfNeeded() {
    const { min, max } = this.getMinMaxPrices();
    let priceDef = this.adminForm.value.adminDefaultPropertyPrice;

    if (priceDef < min || priceDef > max) {
      this.isFieldChanged = true;
      this.errorMsg = 'THE DEFAULT PRICE VALUE IS INVALID';

      this.adminForm.setErrors({
        invalidPrice: true,
      });
    } else {
      this.adminForm.setErrors(null);
      this.errorMsg = '';
    }
  }

  swapMinMaxPricesIfNeeded() {
    const { min, max } = this.getMinMaxPrices();
    if (min > max && max != null) {
      const priceDef = (min + max) / 2;
      this.adminForm.patchValue({
        adminMinPropertyPrice: max,
        adminMaxPropertyPrice: min,
        adminDefaultPropertyPrice: priceDef,
      });
      this.isFieldChanged = true;
      this.errorMsg =
        'WARNING!!! ONLYSAVE CHANGES IF THE VALUES HERE ARE CORRECT, AS IT MAY CAUSE ERRORS IN CALCULATIONS!!!';
    }
  }

  updateFormValues() {
    this.setDefaultPriceIfNeeded();
    this.swapMinMaxPricesIfNeeded();
  }

  handleMinMaxPrices() {
    this.updateFormValues();
  }

  onFocus(event: any) {
    event.target.select();
  }
}
