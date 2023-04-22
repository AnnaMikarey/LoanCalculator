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
  @Input() minPriceOfProperty!: number;
  @Input() maxPriceOfProperty!: number;
  @Input() defaultPriceOfProperty!: number;
  @Input() minInitialDeposit!: number;
  minMortgagePeriod: number = 1;
  maxMortgagePeriod: number = 30;
  maxDeposit: number;
  minDeposit: number;
  depositPercentMaxLength: number;
  constructor() { }
  postForm: FormGroup;

  ngOnInit(): void {
    this.depositPercentMaxLength = 3
    this.postForm = fb.group(
      {
        priceOfProperty: [this.defaultPriceOfProperty, [Validators.min(this.minPriceOfProperty), Validators.max(this.maxPriceOfProperty), Validators.pattern(/[0-9]/), Validators.required]],
        deposit: [(this.defaultPriceOfProperty * this.minInitialDeposit / 100), [Validators.pattern(/[0-9]/), Validators.required, (control: AbstractControl) => Validators.max(this.maxDeposit)(control), (control: AbstractControl) => Validators.min(this.minDeposit)(control)]],
        depositPercent: [this.minInitialDeposit, [Validators.required, Validators.min(this.minInitialDeposit), Validators.max(100)]],
        mortgagePeriod: [this.minMortgagePeriod, [Validators.required, Validators.pattern(/[0-9]/), Validators.min(this.minMortgagePeriod), Validators.max(this.maxMortgagePeriod)]],
        salary: [0, [Validators.pattern(/[0-9]/), Validators.min(0), Validators.required],],
        financialObligation: [0, [Validators.pattern(/[0-9]/), Validators.min(0), Validators.required],],
      },
      { updateOn: 'change' },
    );
    this.postForm.get('priceOfProperty').valueChanges.subscribe(x => {
      this.postForm.controls['deposit'].setValue((x || 0) * (this.postForm.get('depositPercent').value) / 100);
      this.resetDepositMinMax();
    })
    this.resetDepositMinMax();
    this.onSubmit();
  }

  get priceOfProperty() {
    return this.postForm.get('priceOfProperty') as FormControl<number>;
  }
  get deposit() {
    return this.postForm.get('deposit') as FormControl<number>;
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

  resetDepositMinMax() {
    this.maxDeposit = this.postForm.get('priceOfProperty').value;
    this.minDeposit = this.postForm.get('priceOfProperty').value * this.minInitialDeposit / 100;
  }

  setPriceOfProperty(event: any) {
    if (/[^0-9]/.test(event.target.value)) {
      event.target.value = event.target.value.slice(0, -1);
    }
    this.postForm.controls['priceOfProperty'].setValue(Math.abs(parseInt(event.target.value)) || 0);
  }

  setDeposit(event: any) {
    if (/[^0-9]/.test(event.target.value)) {
      event.target.value = event.target.value.slice(0, -1);
    }
    this.postForm.controls['deposit'].setValue(Math.abs(parseInt(event.target.value)) || 0);
    this.postForm.controls['depositPercent'].setValue(Math.abs(parseInt(event.target.value) || 0) * 100 / this.postForm.get('priceOfProperty').value);
  }

  setDepositPercent(event: any) {
    if (/[^0-9.]/.test(event.target.value) || (event.target.value).split``.filter(x => x == ".").length > 1) {
      event.target.value = event.target.value.slice(0, -1);
      event.target.value = event.target.value.split``.filter(x => /[0-9.]/.test(x)).join``;
    }
    this.depositPercentMaxLength = (event.target.value).split``.filter(x => x == ".").length == 1 ? 5 : 3;
    this.postForm.controls['depositPercent'].setValue(event.target.value || 0);
    this.changeDeposit();
  }

  changeDeposit() {
    this.postForm.controls['deposit'].setValue(Math.round((this.postForm.get('priceOfProperty').value) * (this.postForm.get('depositPercent').value) / 100));
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
      event.target.value = event.target.value.slice(0, -1);
    }
    this.postForm.controls[event.target.getAttribute('formControlName')].setValue(Math.abs(parseInt(event.target.value)) || 0);
  }

  counter: any;
  depositPercentPlus() {
    this.counter = setInterval(() => {
      this.postForm.controls["depositPercent"].setValue(parseInt(this.postForm.get('depositPercent').value) + 1);
      this.changeDeposit();
    }, 250);
  }
  depositPercentMinus() {
    this.counter = setInterval(() => {
      this.postForm.controls["depositPercent"].setValue(parseInt(this.postForm.get('depositPercent').value) - 1);
      this.changeDeposit();
    }, 250);
  }


  onSubmit(event?: any) {
    clearInterval(this.counter)

    if (!this.postForm.valid) {

      if (!event) {

        if (this.postForm.controls["depositPercent"].errors['min']) {
          this.postForm.controls["depositPercent"].setValue(this.minInitialDeposit);
          this.postForm.controls["deposit"].setValue(this.minDeposit);
        } else {
          this.postForm.controls["depositPercent"].setValue(100);
          this.postForm.controls["deposit"].setValue(this.maxDeposit);
        }
      } else {

        const alteredField = this.postForm.controls[event.target.getAttribute('formControlName')].errors;

        if (alteredField['max']) {

          if (event.target.getAttribute('formControlName') == "depositPercent" || event.target.getAttribute('formControlName') == "deposit") {
            this.postForm.controls["depositPercent"].setValue(100);
          } else {
            this.postForm.controls[event.target.getAttribute('formControlName')].setValue(alteredField['max'].max);
          }
          this.resetDepositMinMax();

          if (event.target.getAttribute('formControlName') == "deposit") {
            this.postForm.controls["depositPercent"].setValue(100);
          }

        } else {

          if (event.target.getAttribute('formControlName') == "priceOfProperty") {
            this.postForm.controls["priceOfProperty"].setValue(this.minPriceOfProperty);
          } else if (event.target.getAttribute('formControlName') == "depositPercent") {
            this.postForm.controls["depositPercent"].setValue(this.minInitialDeposit);
          } else if (event.target.getAttribute('formControlName') == "deposit") {
            this.postForm.controls["depositPercent"].setValue(this.minInitialDeposit);
            this.postForm.controls["deposit"].setValue(this.minDeposit);
          } else if (event.target.getAttribute('formControlName') == "mortgagePeriod") {
            this.postForm.controls["mortgagePeriod"].setValue(this.minMortgagePeriod);
          } else {
            this.postForm.controls[event.target.getAttribute('formControlName')].setValue(0);
          }
        }

      }
      this.changeDeposit();
    }
    this.onUserInput.emit(this.postForm.value as UserData);
  }
}