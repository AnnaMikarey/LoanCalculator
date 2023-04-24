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


 error:boolean;
 errorM:string;


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


  fun(n:number):string{
   if(n<0){
     return "Time is up"
   }
   let secs=~~(n/1000)
   let minutes=~~(secs/60)
   secs=secs-minutes*60
   let hours=~~(minutes/60)
   minutes=minutes-hours*60
   let days=~~(hours/24)
   hours=hours-days*24


   return `time left: ${days} days ${hours}:${minutes}:${secs}`
 }


  time = new Observable<string>((observer: Observer<string>) => {
   setInterval(() => observer.next(this.fun(new Date(2024,7,12).getTime()-(new Date()).getTime())), 1000);
 });


 ngOnInit(): void {


   //this.subscription = this.calculatorService.getData().subscribe((d) => { this.data = d; });
   //need to be tested
   //this.subscription = this.calculatorService.getInitialData().subscribe((d) => { this.data = d[0];  this.calculations = d[1];});
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




 postUserData(userData: UserData) {


   this.userUiService.toggleLoading();


   // this.salary = userData.salary
   // this.obligation = userData.financialObligation
   // this.calculatorService.postUserDataGetsCalc(userData).pipe(catchError((error) => {
   //   console.error('Error posting input data: ', error);
   //   return EMPTY;
   // })).subscribe((userData) => {
   //   //this.userUiService.changeAnnuityLinear(userData.annuityLinear)
   //   console.log("Posting userData " + Object.entries(userData))
   //   this.userUiService.toggleLoading();


   //this.calculatorService.getCalculated().subscribe((calculatedValues)=>this.calculations = calculatedValues)
   //this.calculatorService.postUserDataGetsCalc(userData).subscribe((calculatedValues)=>this.calculations = calculatedValues; console.error("postUserDataGetsCalc",this.calculations))


   //});


   //needs testing
   this.calculatorService.postUserDataGetsCalc(userData).subscribe((calculatedValues) => { this.calculations = calculatedValues; console.error("postUserDataGetsCalc", this.calculations); this.userUiService.toggleLoading();this.error=false },(error)=>{console.log("error",error);this.error=true;this.errorM=error.error.message})


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



