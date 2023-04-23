import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserUiService {

  loading: boolean = false;
  private subject = new Subject<boolean>();

  constructor() { }

  toggleLoading(): void {
    this.loading = !this.loading;
    this.subject.next(this.loading);
  }

  onToggle():Observable<boolean>{
    return this.subject.asObservable();
  }
}
