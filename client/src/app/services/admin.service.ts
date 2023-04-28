import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AdminData } from '../../Types/adminData';
import { environment } from "../environment";

@Injectable({
  providedIn: 'root',
})
export class AdminService {
  constructor(private http: HttpClient) {}

  postData(formData: AdminData): Observable<AdminData> {
    return this.http.post<AdminData>(
      environment.baseUrl + "admin/change-values",
      formData
    );
  }

  getData(): Observable<AdminData> {
    return this.http.get<AdminData>(
      environment.baseUrl + 'admin/get-initial-values'
    );
  }
}
