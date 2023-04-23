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
  annualInterestRateTotal!: number;
  euroiborRate!: number;
  contractFee!: number;
  bankFee!: number;
  mortgageRegistrationFee!: number;

  //monthly payment (if monthly payment + financial obligations >40% of salary then error)
  @Input() monthlyPaymentError!: boolean;
  constructor() { }
  ngOnChanges(changes: SimpleChanges): void {
    this.requestedLoanAmount = this.format(this.calculations?.requestedLoanAmount);
    this.maxLoanAvailable = this.format(this.calculations?.maxLoanAvailable);
    this.monthlyPayment = this.format(this.calculations?.monthlyPayment);
    this.annualInterestRateTotal = this.format(this.calculations?.annualInterestRateTotal);
    this.euroiborRate = this.format(this.calculations?.euroborAndRate);
    this.contractFee = this.format(this.calculations?.contractFee);
    this.bankFee = this.format(this.calculations?.bankFee);
    this.mortgageRegistrationFee = this.format(this.calculations?.mortgageRegistration);

    //for testing; make array of values and apply formatting via map
    // this.requestedLoanAmount = this.format(this.calculations?.requestedLoanAmount);
    // this.maxAvailableLoanAmount = this.format(this.calculations?.maxAvailableLoanAmount);
    // this.monthlyPaymentAmount = this.format(this.calculations?.monthlyPaymentAmount);
    // this.euriborDate = this.format(this.calculations?.euriborDate);
    // this.euriborRate = this.format(this.calculations?.euriborRate);
    // this.bankInterestRate = this.format(this.calculations?.bankInterestRate);
    // this.annualInterestRate = this.format(this.calculations?.annualInterestRate);
    // this.contractFee = this.format(this.calculations?.contractFee);
    // this.registrationFee = this.format(this.calculations?.registrationFee);
    // this.monthlyBankFee = this.format(this.calculations?.monthlyBankFee);

  }

  format(number: number) {
    if (number == null) return 0;
    return Math.round((number + Number.EPSILON) * 100) / 100;
  }

}
