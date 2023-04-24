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

  //test
  euriborRate:number 

  salary!: number;
  monthlyPaymentError!: boolean;
  //@Output() onCalculationGet: EventEmitter<Data> = new EventEmitter()
  constructor(private calculatorService: CalculatorService, private userUiService: UserUiService) {

  }
  ngOnInit(): void {

    //this.subscription = this.calculatorService.getData().subscribe((d) => { this.data = d; });
    //need to be tested
    //this.subscription = this.calculatorService.getInitialData().subscribe((d) => { this.data = d[0];  this.calculations = d[1];});
    this.subscription = this.calculatorService.getInitialData().subscribe((d) => { this.data = d[0];  this.calculations = d[1];
    this.euriborRate=d[0].euriborRate});

  }
  // ngOnChanges(): void {

  // }

  // postUserData(userData: UserData) {

  //   this.userUiService.toggleLoading();
  //   console.log("submiting")
  //   //mock calculation
  //   this.salary = userData.salary
  //   this.obligation = userData.financialObligation
  //   this.calculatorService.postUserData(userData).pipe(catchError((error) => {
  //     console.error('Error posting input data: ', error);
  //     return EMPTY;
  //   })).subscribe((userData) => {
  //     this.userUiService.changeAnnuityLinear(userData.annuityLinear)
  //     console.log("Posting userData" + Object.entries(userData))
  //     this.calculatorService.postCalculations({
  //       requestedLoanAmount: userData.priceOfProperty - userData.deposit,
  //       contractFee: this.data.contractFee,
  //       bankFee: this.data.bankFee * userData.mortgagePeriod,
  //       mortgageRegistration: this.data.mortgageRegistrationFee,
  //       euroborAndRate: this.data.euribor + this.data.bankMargin,
  //       maxLoanAvailable: (userData.salary * 0.4 - userData.financialObligation) * (1 - Math.pow(1 + (((this.data.euribor + this.data.bankMargin) / 100) / 12), -(12 * userData.mortgagePeriod))) / (((this.data.euribor + this.data.bankMargin) / 100) / 12),
  //       monthlyPayment: (userData.priceOfProperty - userData.deposit) * ((((this.data.euribor + this.data.bankMargin) / 100) / 12) * Math.pow(1 + (((this.data.euribor + this.data.bankMargin) / 100) / 12), userData.mortgagePeriod * 12) / (Math.pow(1 + (((this.data.euribor + this.data.bankMargin) / 100) / 12), userData.mortgagePeriod * 12) - 1)),
  //       annualInterestRateTotal: ((this.data.bankFee + this.data.contractFee + this.data.contractFee + ((userData.priceOfProperty) * (this.data.euribor + this.data.bankMargin) * userData.mortgagePeriod)) / userData.priceOfProperty / (userData.mortgagePeriod * 365)) * 365,
  //     }).subscribe(
  //       (postedValues) => { console.log("posted calculations", postedValues); this.getCalculations(); this.userUiService.toggleLoading(); }
  //     )
  //   });
  // }


  //test
  postUserData(userData: UserData) {

    this.userUiService.toggleLoading();
    
    // this.salary = userData.salary
    // this.obligation = userData.financialObligation
    this.calculatorService.postUserDataGetsCalc(userData).pipe(catchError((error) => {
      console.error('Error posting input data: ', error);
      return EMPTY;
    })).subscribe((userData) => {
      //this.userUiService.changeAnnuityLinear(userData.annuityLinear)
      console.log("Posting userData " + Object.entries(userData))
      this.userUiService.toggleLoading();

      this.calculatorService.getCalculated().subscribe((calculatedValues)=>this.calculations = calculatedValues)

    });

    //needs testing
   // this.calculatorService.postUserDataGetsCalc(userData).subscribe((calculatedValues)=>this.calculations = calculatedValues; console.error("postUserDataGetsCalc",this.calculations))

  }

  getCalculations() {
    this.calculatorService.getCalculations().subscribe((calculatedValues) => { console.log("get calculations", this.calculations = calculatedValues); this.checkError(); });
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
