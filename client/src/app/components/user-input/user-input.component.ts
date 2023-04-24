import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnChanges, OnInit, Optional, Output, SimpleChanges } from '@angular/core';
import { AbstractControl, DefaultValueAccessor, FormBuilder, FormControl, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { UserData } from '../../Types/UserData';

const fb = new FormBuilder().nonNullable;

@Component({
  selector: 'app-user-input',
  templateUrl: './user-input.component.html',
  styleUrls: ['./user-input.component.css']
})
export class UserInputComponent implements OnInit {

  @Output() onUserInput: EventEmitter<UserData> = new EventEmitter();
  // @Input() minPriceOfProperty!: number;
  // @Input() maxPriceOfProperty!: number;
  // @Input() defaultPriceOfProperty!: number;
  // @Input() minInitialDeposit!: number;

  @Input() maxPropertyPrice!: number;
  @Input() minPropertyPrice!: number;
  @Input() defaultPropertyPrice!: number;
  @Input() minDepositPercent!: number;

  @Input() defaultInitialDeposit!: number;
  @Input() defaultMortgagePeriod!: number;
  @Input() defaultSalary!: number;
  @Input() defaultFinancialObligation!: number;

  // maxPropertyPrice: number
  // minPropertyPrice: number
  // defaultPropertyPrice: number
  // minDepositPercent: number
  // defaultInitialDeposit: number
  // defaultMortgagePeriod: number
  // defaultSalary: number
  // defaultFinancialObligation: number

  minMortgagePeriod: number = 1;
  maxMortgagePeriod: number = 30;
  maxDeposit: number;
  minDeposit: number;
  depositPercentMaxLength: number;
  initialAnnuityLinear: string = "annuity"
  constructor() { }
  postForm: FormGroup;

  ngOnInit(): void {
    this.depositPercentMaxLength = 3
    this.postForm = fb.group(
      {
        propertyPrice: [this.defaultPropertyPrice, [Validators.min(this.minPropertyPrice), Validators.max(this.maxPropertyPrice), Validators.pattern(/[0-9]/), Validators.required]],
        initialDeposit: [(this.defaultPropertyPrice * this.minDepositPercent / 100), [Validators.pattern(/[0-9]/), Validators.required, (control: AbstractControl) => Validators.max(this.maxDeposit)(control), (control: AbstractControl) => Validators.min(this.minDeposit)(control)]],
        depositPercent: [this.minDepositPercent, [Validators.required, Validators.min(this.minDepositPercent), Validators.max(100)]],
        mortgagePeriod: [this.defaultMortgagePeriod, [Validators.required, Validators.pattern(/[0-9]/), Validators.min(this.minMortgagePeriod), Validators.max(this.maxMortgagePeriod)]],
        salary: [this.defaultSalary, [Validators.pattern(/[0-9]/), Validators.min(0), Validators.required],],
        financialObligation: [this.defaultFinancialObligation, [Validators.pattern(/[0-9]/), Validators.min(0), Validators.required],],
        annuityLinear: [this.initialAnnuityLinear, [],],
    //     propertyPrice: number,
    // initialDeposit: number,
    // salary: number,
    // financialObligation: number,
    // mortgagePeriod: number
      },
      { updateOn: 'change' },
    );
    this.postForm.get('propertyPrice').valueChanges.subscribe(value => {
      this.postForm.controls['initialDeposit'].setValue((value || 0) * (this.postForm.get('depositPercent').value) / 100);
      this.resetDepositMinMax();
    })
    this.resetDepositMinMax();
    //this.onSubmit();
  }

  get propertyPrice() {
    return this.postForm.get('propertyPrice') as FormControl<number>;
  }
  get initialDeposit() {
    return this.postForm.get('initialDeposit') as FormControl<number>;
  }
  get depositPercent() {
    return this.postForm.get('depositPercent') as FormControl<number>;
  }
  get mortgagePeriod() {
    return this.postForm.get('mortgagePeriod') as FormControl<number>;
  }
  get salary() {
    return this.postForm.get('salary') as FormControl<number>;
  }
  get financialObligation() {
    return this.postForm.get('financialObligation') as FormControl<number>;
  }
  get annuityLinear() {
    return this.postForm.get('annuityLinear') as FormControl<string>;
  }

  resetDepositMinMax() {
    this.maxDeposit = this.postForm.get('propertyPrice').value;
    this.minDeposit = this.postForm.get('propertyPrice').value * this.minDepositPercent / 100;
  }

  setPriceOfProperty(event: any) {
    // if(event.target.getAttribute('class').includes("ng-untouched")){
    // 
    // }else 
    if (/[^0-9]/.test(event.target.value)) {
      event.target.value = event.target.value.split``.filter(value => /[0-9]/.test(value)).join``
    }
    this.postForm.controls['propertyPrice'].setValue(Math.abs(parseInt(event.target.value)) || 0);
  }

  setDeposit(event: any) {
    if (/[^0-9]/.test(event.target.value)) {
      event.target.value = event.target.value.split``.filter(value => /[0-9]/.test(value)).join``
    }
    this.postForm.controls['initialDeposit'].setValue(Math.abs(parseInt(event.target.value)) || 0);
    this.postForm.controls['depositPercent'].setValue(Math.abs(parseInt(event.target.value) || 0) * 100 / this.postForm.get('propertyPrice').value);
  }

  setAnnuityLinear(event: any) {
    this.postForm.controls['annuityLinear'].setValue(this.postForm.get('annuityLinear').value == "annuity" ? "linear" : "annuity");
  }

  setDepositPercent(event: any) {
    if (/[^0-9.]/.test(event.target.value) || (event.target.value).split``.filter(value => value == ".").length > 1) {
      event.target.value = event.target.value.split``.filter(value => /[0-9.]/.test(value)).join``;
    }
    this.depositPercentMaxLength = (event.target.value).split``.filter(value => value == ".").length == 1 ? 5 : 3;
    this.postForm.controls['depositPercent'].setValue(parseFloat(event.target.value) || 0);
    this.changeDeposit();
  }

  changeDeposit() {
    this.postForm.controls['initialDeposit'].setValue(Math.round((this.postForm.get('propertyPrice').value) * (this.postForm.get('depositPercent').value) / 100));
  }

  //not needed for now
  // onFocus(event: any) {
  //   event.target.value = parseFloat(event.target.value.split` `.join``)
  // }
  // onBlur(event: any) {
  //   var parts = event.target.value.toString().split(".");
  //   parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, " ");
  //   event.target.value=parts.join(".");
  // }

  checkIfNotNumber(event: any) {
    if (/[^0-9]/.test(event.target.value)) {
      event.target.value = event.target.value.split``.filter(value => /[0-9]/.test(value)).join``
    }
    this.postForm.controls[event.target.getAttribute('formControlName')].setValue(Math.abs(parseInt(event.target.value)) || 0);
  }

  counter: any;
  depositPercentPlus() {
    this.counter = setInterval(() => {
      if (parseInt(this.postForm.get('depositPercent').value) < 100) {
        this.postForm.controls["depositPercent"].setValue(parseInt(this.postForm.get('depositPercent').value) + 1);
        this.changeDeposit();
      }
    }, 50);
  }
  depositPercentMinus() {
    this.counter = setInterval(() => {
      if (parseInt(this.postForm.get('depositPercent').value) > this.minDepositPercent) {
        this.postForm.controls["depositPercent"].setValue(parseInt(this.postForm.get('depositPercent').value) - 1);
        this.changeDeposit();
      }
    }, 50);
  }

  onSubmit(event?: any) {
    clearInterval(this.counter)

    if (!this.postForm.valid) {

      if (!event) {

        if (this.postForm.controls["depositPercent"].errors['min']) {
          this.postForm.controls["depositPercent"].setValue(this.minDepositPercent);
          this.postForm.controls["initialDeposit"].setValue(this.minDeposit);
        } else {
          this.postForm.controls["depositPercent"].setValue(99);
          this.postForm.controls["initialDeposit"].setValue(this.maxDeposit);
        }

      } else {

        const alteredField = this.postForm.controls[event.target.getAttribute('formControlName')].errors;

        if (alteredField['max']) {

          if (event.target.getAttribute('formControlName') == "depositPercent" || event.target.getAttribute('formControlName') == "deposit") {
            this.postForm.controls["depositPercent"].setValue(99);
          } else {
            this.postForm.controls[event.target.getAttribute('formControlName')].setValue(alteredField['max'].max);
          }
          this.resetDepositMinMax();

          if (event.target.getAttribute('formControlName') == "initialDeposit") {
            this.postForm.controls["depositPercent"].setValue(99);
          }

        } else {

          if (event.target.getAttribute('formControlName') == "propertyPrice") {
            this.postForm.controls["propertyPrice"].setValue(this.minPropertyPrice);
          } else if (event.target.getAttribute('formControlName') == "depositPercent") {
            this.postForm.controls["depositPercent"].setValue(this.minDepositPercent);
          } else if (event.target.getAttribute('formControlName') == "initialDeposit") {
            this.postForm.controls["depositPercent"].setValue(this.minDepositPercent);
            this.postForm.controls["initialDeposit"].setValue(this.minDeposit);
          } else if (event.target.getAttribute('formControlName') == "mortgagePeriod") {
            this.postForm.controls["mortgagePeriod"].setValue(this.minMortgagePeriod);
          } else {
            this.postForm.controls[event.target.getAttribute('formControlName')].setValue(0);
          }
        }

      }
      this.changeDeposit();
    }
    let temp:UserData={"propertyPrice":this.postForm.get('propertyPrice').value,
    "initialDeposit":this.postForm.get('initialDeposit').value,
    "mortgagePeriod":this.postForm.get('mortgagePeriod').value,
    "salary":this.postForm.get('salary').value,
    "financialObligation":this.postForm.get('financialObligation').value,
    }
    this.onUserInput.emit(temp);

    //this.onUserInput.emit(this.postForm.value as UserData);
  }
}