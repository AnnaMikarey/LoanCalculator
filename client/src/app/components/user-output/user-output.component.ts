import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CalculatedValues } from '../../Types/CalculatedValues';
import { Observable, Observer } from 'rxjs';

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
  @Input() euriborDate: string;
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

  // fun(n: number): string {
  //   if (n < 0) {
  //     return "Time is up"
  //   }
  //   let secs = ~~(n / 1000)
  //   let minutes = ~~(secs / 60)
  //   secs = secs - minutes * 60
  //   let hours = ~~(minutes / 60)
  //   minutes = minutes - hours * 60
  //   let days = ~~(hours / 24)
  //   hours = hours - days * 24

  //   return `time left: ${days} days ${String(hours).padStart(2,"0")}:${String(minutes).padStart(2,"0")}:${String(secs).padStart(2,"0")}`

  // }

  // time = new Observable<string>((observer: Observer<string>) => {
  //   setInterval(() => observer.next(this.fun(new Date("2024-07-12").getTime() - new Date().getTime())), 1000);//new Date(2024,6,12)
  // });
  // ngOnDestroy() {
  //   this.time;
  // }
}