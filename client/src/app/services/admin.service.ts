import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AdminData } from '../../Types/adminData';

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  constructor(private http: HttpClient) {}

  postData(formData: AdminData): Observable<AdminData> {
    return this.http.post<AdminData>(
      'http://16.16.120.205:8080/admin/change-values',
      formData
    );
  }

  getData(): Observable<AdminData> {
    return this.http.get<AdminData>(
      'http://16.16.120.205:8080/admin/get-initial-values'
    );
  }
}
