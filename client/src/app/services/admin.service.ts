import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AdminData } from '../../Types/adminData';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  constructor(private http: HttpClient) {}

  postData(formData: AdminData): Observable<any> {
    return this.http.post<{}>('http://localhost:8080/admin/values', formData);
  }

  getData(): Observable<any> {
    return this.http.get<AdminData>('http://localhost:8080/admin/values');
  }
}
