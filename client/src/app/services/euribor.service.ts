import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class EuriborService {
  private apiUrl = 'https://api.api-ninjas.com/v1/interestrate';
  private apiKey = '+2cf5ADANk6brx0mbtKeFA==8eN4TXWmWp3V1QrP';

  header = new HttpHeaders({
    'X-Api-Key': this.apiKey,
  });

  constructor(private http: HttpClient) {}

  getRates() {
    return this.http.get(this.apiUrl, { headers: this.header });
  }
}
