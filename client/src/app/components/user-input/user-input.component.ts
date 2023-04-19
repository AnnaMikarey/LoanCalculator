
import { Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges } from '@angular/core';
import { AbstractControl, FormBuilder, FormControl, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { UserData } from '../../Types/UserData';
import { Observable } from 'rxjs';

const fb = new FormBuilder().nonNullable;

const isNanOrNullOrUndefined: ValidatorFn = (control) => {
  return (isNaN(control.value) || control.value == null || control.value == undefined) ? { isNanOrNullOrUndefined: true } : null;
};



// function minMaxValidator(numberMin: number, numberMax: number): ValidatorFn {
//   return (control: AbstractControl): ValidationErrors | null => {
//     //console.log("i work " + numberMin,numberMax)
//     //todo naming in return object for specific error
//     if (parseInt(control.value) < numberMin) {
//       return { minMaxValidator: true }
//     }
//     if (parseInt(control.value) > numberMax) {
//       return { minMaxValidator: true }
//     }
//     return null;
//   };
// }

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

  constructor() {

  }
  postForm = fb.group(
    {//Validators.required Validators.min(this.minPriceOfProperty) minMaxValidator(this.minPriceOfProperty, this.maxPriceOfProperty)
      priceOfProperty: [0, [Validators.min(this.maxPriceOfProperty), Validators.max(this.maxPriceOfProperty), isNanOrNullOrUndefined, Validators.required]],
      deposit: [0, [isNanOrNullOrUndefined,]],
      depositPercent: [0, [isNanOrNullOrUndefined, Validators.min(this.minInitialDeposit), Validators.max(100)]],
      mortgagePeriod: [0, [isNanOrNullOrUndefined, Validators.min(this.minMortgagePeriod), Validators.max(this.maxMortgagePeriod)]],
      salary: [0, [isNanOrNullOrUndefined, Validators.min(0)],],
      financialObligation: [0, [isNanOrNullOrUndefined, Validators.min(0)],],
    },
    { updateOn: 'blur' },
  );
  ngOnInit(): void {
    this.postForm.setValue({
      priceOfProperty: this.defaultPriceOfProperty,
      deposit: this.defaultPriceOfProperty * this.minInitialDeposit / 100,
      depositPercent: this.minInitialDeposit,
      mortgagePeriod: 1,
      salary: 0,
      financialObligation: 0
    })
    this.onSubmit()
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

  setPriceOfPropertyAndChangeDeposit(event: any) {
    this.postForm.controls.priceOfProperty.setValue(parseInt(event.target.value))
    this.postForm.controls.deposit.setValue(parseInt(event.target.value) * this.postForm.get('depositPercent')!.value / 100)
  }
  changeDeposit(event: any) {
    this.postForm.controls.deposit.setValue(parseInt(event.target.value) * this.postForm.get('priceOfProperty')!.value / 100)
  }
  changeDepositPercent(event: any) {
    this.postForm.controls.depositPercent.setValue(parseInt(event.target.value) * 100 / this.postForm.get('priceOfProperty')!.value)
  }


  onInput(event: any) {
    // if (isNaN(event.target.value) || !/[0-9]/.test(event.target.value)) {
    //   event.target.value = 0;
    //   return
    // }
    // if (event.target.value[0] == 0 && event.target.value[1] !== ".") {
    //   event.target.value = event.target.value[1];
    //   return
    // } if (!/[0-9.]/.test(event.key) && event.code !== 'Enter' && event.code !== 'Tab' && event.code !== 'Backspace'
    //   && event.code !== 'ArrowLeft' && event.code !== 'ArrowRight') {
    //   event.preventDefault()
    //   return
    // }

  }

  onSubmit() {
    if (this.postForm.valid) {
      this.onUserInput.emit(this.postForm.value as UserData);
    } else {
      //todo delete console log
      console.log("post not valid")
    }
  }

}
