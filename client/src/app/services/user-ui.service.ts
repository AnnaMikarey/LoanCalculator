import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { UserData } from '../Types/UserData';

@Injectable({
  providedIn: 'root'
})
export class UserUiService {

  loading: boolean = false;
  private subject = new Subject<boolean>();
  private subjectLinearOrAnnuity = new Subject<string>();
  constructor() { }

  toggleLoading(): void {
    this.loading = !this.loading;
    this.subject.next(this.loading);
  }

  onToggle(): Observable<boolean> {
    return this.subject.asObservable();
  }

  changeAnnuityLinear(value: string): void {
    this.subjectLinearOrAnnuity.next(value);
  }

  onAnnuityLinearChange(): Observable<string> {
    return this.subjectLinearOrAnnuity.asObservable();
  }
}
