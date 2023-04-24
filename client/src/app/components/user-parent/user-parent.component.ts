import { Component, EventEmitter, OnChanges, OnDestroy, OnInit, Output } from '@angular/core';
import { Observable, Observer, Subscription, catchError, EMPTY } from 'rxjs';
import { CalculatorService } from 'src/app/services/calculator.service';
import { InitialData } from '../../Types/InitialData';
import { UserData } from '../../Types/UserData';
import { CalculatedValues } from '../../Types/CalculatedValues';
import { UserUiService } from 'src/app/services/user-ui.service';


@Component({
  selector: 'app-user-parent',
  templateUrl: './user-parent.component.html',
  styleUrls: ['./user-parent.component.css']
})
export class UserParentComponent implements OnInit, OnDestroy {//, OnChanges {
  subscription!: Subscription
  data!: InitialData;
  calculations!: CalculatedValues;
  obligation!: number;


  error: boolean;
  errorM: string;


  euriborRate: number
  euriborDate: string
  bankInterestRate: number
  annualInterestRate: number
  contractFee: number
  registrationFee: number
  monthlyBankFee: number


  salary!: number;
  monthlyPaymentError!: boolean;
  //@Output() onCalculationGet: EventEmitter<Data> = new EventEmitter()
  constructor(private calculatorService: CalculatorService, private userUiService: UserUiService) {


  }


 


  ngOnInit(): void {
    this.subscription = this.calculatorService.getInitialData().subscribe((d) => {
      this.data = d[0]; this.calculations = d[1];
      this.euriborRate = d[0].euriborRate;
      this.euriborDate = d[0].euriborDate
      this.bankInterestRate = d[0].bankInterestRate
      this.annualInterestRate = d[0].annualInterestRate
      this.contractFee = d[0].contractFee
      this.registrationFee = d[0].registrationFee
      this.monthlyBankFee = d[0].monthlyBankFee
    });


  }

  postUserData(userData: UserData) {

    this.userUiService.toggleLoading(true);

    this.calculatorService.postUserDataGetsCalc(userData).subscribe((calculatedValues) => {
      this.calculations = calculatedValues;this.userUiService.toggleLoading(false);
      console.error("postUserDataGetsCalc", this.calculations);  this.error = false
    },
      (error) => { console.log("error", error); this.error = true; this.errorM = error.error.message })
      
  }



  ngOnDestroy() {
    this.subscription.unsubscribe();
  }


  checkError() {
    //"error?"
    //console.log("checkerorr"+this.calculations.monthlyPayment+this.obligation,this.salary*40/100)
    //this.monthlyPaymentError = this.calculations.monthlyPayment + this.obligation > this.salary * 40 / 100;
  }
}



