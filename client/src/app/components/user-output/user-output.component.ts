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
    this.requestedLoanAmount = this.format(this.calculations?.requestedLoanAmount)
    this.maxLoanAvailable = this.format(this.calculations?.maxLoanAvailable)
    this.monthlyPayment = this.format(this.calculations?.monthlyPayment)
    this.annualInterestRateTotal = this.format(this.calculations?.annualInterestRateTotal)
    this.euroiborRate = this.format(this.calculations?.euroborAndRate)
    this.contractFee = this.format(this.calculations?.contractFee)
    this.bankFee = this.format(this.calculations?.bankFee)
    this.mortgageRegistrationFee = this.format(this.calculations?.mortgageRegistration)
  }

  format(num: number) {
    if (num == null) return 0
    return Math.round((num + Number.EPSILON) * 100) / 100
  }

}
