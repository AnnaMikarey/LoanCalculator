import { Component, EventEmitter, Input, OnChanges, OnInit, Optional, Output, SimpleChanges } from '@angular/core';
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

  constructor() { }

  postForm: FormGroup;

  ngOnInit(): void {

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
    this.resetDepositToDefault();
    //calls server for calculation on initial load, maybe not needed if data is provided on first load
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

  resetDepositToDefault() {
    this.maxDeposit = this.defaultPriceOfProperty;
    this.minDeposit = this.defaultPriceOfProperty * this.minInitialDeposit / 100;
  }

  setPriceOfProperty(event: any) {
    this.postForm.controls['priceOfProperty'].setValue(parseInt(event.target.value));
    if (!this.postForm.controls['priceOfProperty'].errors) {
      this.maxDeposit = parseInt(event.target.value);
      this.minDeposit = parseInt(event.target.value) * this.minInitialDeposit / 100;
    } else {
      if (this.postForm.controls['priceOfProperty'].errors['max']) {
        this.minDeposit = this.postForm.controls['priceOfProperty'].errors['max'].max * this.minInitialDeposit / 100;
        this.maxDeposit = this.postForm.controls['priceOfProperty'].errors['max'].max;
        //need to be just else and get min values
      } else if (this.postForm.controls['priceOfProperty'].errors['min']) {
        this.minDeposit = this.postForm.controls['priceOfProperty'].errors['min'].min * this.minInitialDeposit / 100;
        this.maxDeposit = this.postForm.controls['priceOfProperty'].errors['min'].min;
      }
    }
    this.changeDepositGlobal();
  }

  changeDepositGlobal() {
    this.postForm.controls['deposit'].setValue((this.postForm.get('priceOfProperty').value) * (this.postForm.get('depositPercent').value) / 100);
  }

  changeDepositPercent(event: any) {
    this.postForm.controls['depositPercent'].setValue((parseInt(event.target.value) || 0) * 100 / this.postForm.get('priceOfProperty')!.value);
  }

  setMortgagePeriod(event: any) {
    this.postForm.controls['mortgagePeriod'].setValue(parseInt(event.target.value));
  }

  // onInput(event: any){
  //   this.postForm.controls[event.target.getAttribute('formControlName')].setValue(parseInt(event.target.value)||0)
  // }

  onSubmit(event?: any) {
    if (!this.postForm.valid) {
      const alteredField = this.postForm.controls[event.target.getAttribute('formControlName')].errors;

      if (alteredField['min']) {
        this.postForm.controls[event.target.getAttribute('formControlName')].setValue(alteredField['min'].min);

      } else if (alteredField['max']) {
        this.postForm.controls[event.target.getAttribute('formControlName')].setValue(alteredField['max'].max);

      } else {
        this.postForm.reset();
        this.resetDepositToDefault();
      }

      for (let i in this.postForm.controls) {
        if (this.postForm.controls[i].status == "INVALID") {
          if (i == "deposit") {
            this.postForm.controls['deposit'].setValue(this.postForm.get('priceOfProperty').value * this.postForm.get('depositPercent').value / 100);
          } else if (i == "depositPercent") {
            this.postForm.controls['depositPercent'].setValue(this.postForm.get('deposit').value * 100 / this.postForm.get('priceOfProperty').value);
          }
        }
      }
    }
    this.onUserInput.emit(this.postForm.value as UserData);
  }
}