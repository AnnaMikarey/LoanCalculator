import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CalculatedValues } from '../../Types/CalculatedValues';
import { Observable } from 'rxjs';


@Component({
 selector: 'app-user-output',
 templateUrl: './user-output.component.html',
 styleUrls: ['./user-output.component.css']
})
export class UserOutputComponent implements OnChanges {
 //data fetch is mocked
 @Input() calculations!: CalculatedValues;


 requestedLoanAmount!: number;
 maxLoanAvailable!: number;
 monthlyPayment!: number;


 @Input() euriborRate: number;
 @Input() euriborDate: number;
 @Input() bankInterestRate: number;
 @Input() annualInterestRate: number;
 @Input() contractFee: number;
 @Input() registrationFee: number;
 @Input() monthlyBankFee: number;
 //monthly payment (if monthly payment + financial obligations >40% of salary then error)
 @Input() monthlyPaymentError!: boolean;
 constructor() { }
 ngOnChanges(changes: SimpleChanges): void {
   this.requestedLoanAmount = this.format(this.calculations?.requestedLoanAmount);
   this.maxLoanAvailable = this.format(this.calculations?.maxAvailableLoanAmount);
   this.monthlyPayment = this.format(this.calculations?.monthlyPaymentAmount);
 }


 format(number: number) {
   if (number == null) return 0;
   return Math.round((number + Number.EPSILON) * 100) / 100;
 }


}



