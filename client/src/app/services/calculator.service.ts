import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { InitialData } from '../Types/InitialData';
import { UserData } from '../Types/UserData';
import { CalculatedValues } from '../Types/CalculatedValues';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
}
@Injectable({
  providedIn: 'root'
})
export class CalculatorService {
  //todo api url
  private apiUrl = 'http://localhost:3000/';
  //private apiUrl = 'http://localhost:3000/client/';
  constructor(private httpClient: HttpClient) { }

  //todo get data from the server
  //getData():Observable<Data> {
  //return this.http.get<Data>(this.apiUrl);
  //return of(DATA);
  //}
  // calculateRequestedLoanAmount(priceOfProperty:string,initialDeposit:string){
  //   if(priceOfProperty!=""&&initialDeposit!=""){
  //     return Number(priceOfProperty)-Number(initialDeposit);
  //   }else{
  //     return "Enter data in to fields"
  //   }
  // }

  //every call for calculation is made to the server, now it is just mocked
  //gets all data that is in admin panel (probably needs to be limited to only needed data for the user)
  getData(): Observable<InitialData> {
    return this.httpClient.get<InitialData>(this.apiUrl + 'data');
  }
  postUserData(userData: UserData) {
    return this.httpClient.put<UserData>(this.apiUrl + 'userInputs', userData, httpOptions);
    //dont know if you can receive response in put or post request header for calculations
  }
  postCalculations(calculatedValues: CalculatedValues) {
    return this.httpClient.post<CalculatedValues>(this.apiUrl + 'calculatedValues', calculatedValues, httpOptions);
    //dont know if you can receive response in put or post request header for calculations
  }
  getCalculations(): Observable<CalculatedValues> {
    //first get response, that the server got the data, than call for the calculated data
    return this.httpClient.get<CalculatedValues>(this.apiUrl + 'calculatedValues');
  }

  //need to be tested
  getInitialData(): Observable<any[]> {
    return this.httpClient.get<any[]>(this.apiUrl + 'get-initial-values');
  }
  //https://stackoverflow.com/questions/54110377/angular-receive-and-return-observablet-response-in-http-post
  postUserDataGetsCalc(userData: UserData) {
    return this.httpClient.post<UserData>(this.apiUrl + 'calculate', userData, httpOptions);
  }
  //needs testing
  //   postUserDataGetsCalc(userData: UserData): Observable<CalculatedValues>{
  //     return this.httpClient.post<CalculatedValues>(this.apiUrl + 'calculate', userData, httpOptions)
  // }

  getCalculated(): Observable<CalculatedValues> {
    return this.httpClient.get<CalculatedValues>(this.apiUrl + 'calculated');
  }

}
