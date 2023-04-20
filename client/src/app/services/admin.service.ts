import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  constructor(private http: HttpClient) {}

  postData(formData: any): Observable<any> {
    return this.http.post<any>(
      'http://localhost:8080/admin/change-values',
      formData
    );
  }

  getData(): Observable<any> {
    return this.http.get<any>('http://localhost:8080/admin/initial-values');
  }
}
