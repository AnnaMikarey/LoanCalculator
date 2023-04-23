import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { AdminService } from '../services/admin.service';
import { EuriborService } from '../services/euribor.service';
import { forkJoin, take } from 'rxjs';

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

  validatorsNum = [Validators.required, Validators.pattern(/^\d+\.?\d*$/)];
  validatorsPercent = [
    ...this.validatorsNum,
    Validators.min(0),
    Validators.max(100),
  ];

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
        this.adminEuriborDate =
          euriborData['non_central_bank_rates'][4]['last_updated'];
        this.adminForm.patchValue({
          adminEuriborRate:
            euriborData['non_central_bank_rates'][4]['rate_pct'],
        });
      });
  }

  saveChanges() {
    this.adminForm.value['adminEuriborDate'] = this.adminEuriborDate;
    this.adminService.postData(this.adminForm.value).pipe(take(1)).subscribe();
  }

  discardChanges() {
    this.ngOnInit();
  }

  handleMinMaxPrices() {
    const priceMin = this.adminForm.value.adminMinPropertyPrice;
    const priceMax = this.adminForm.value.adminMaxPropertyPrice;

    if (priceMin > priceMax) {
      this.adminForm.patchValue({
        adminMinPropertyPrice: priceMax,
        adminMaxPropertyPrice: priceMin,
      });
    }
  }
}
