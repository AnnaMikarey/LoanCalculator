import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  constructor(private http: HttpClient) {}

  postData(formData: {}): Observable<any> {
    return this.http.post<{}>('http://localhost:8080/admin/values', formData);
  }

  getData(): Observable<any> {
    return this.http.get<{}>('http://localhost:8080/admin/values');
  }
}
