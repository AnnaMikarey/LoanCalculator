import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { InitialData } from '../Types/InitialData';
import { UserData } from '../Types/UserData';
import { CalculatedValues } from '../Types/CalculatedValues';
import { environment } from '../environment';
const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};
@Injectable({
  providedIn: 'root',
})
export class CalculatorService {
  // private apiUrl = 'http://16.16.120.205:8080/client/';
  private apiUrl = 'http://localhost:3000/';
  constructor(private httpClient: HttpClient) {}

  getInitialData(): Observable<any[]> {
    return this.httpClient.get<any[]>(
      environment.baseUrl + 'client/' + 'get-initial-values'
    );
  }

  postUserDataGetsCalc(userData: UserData): Observable<CalculatedValues> {
    return this.httpClient.post<CalculatedValues>(
      environment.baseUrl + 'client/' + 'calculate',
      userData,
      httpOptions
    );
  }
}
