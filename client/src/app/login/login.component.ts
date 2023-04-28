import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  isError: boolean;

  constructor(private router: Router) {}

  login(): void {
    const hardcodedUsername: string = 'admin987';
    const hardcodedPassword: string = 'password985';
    let isError: boolean = false;


    if (this.username === hardcodedUsername && this.password === hardcodedPassword) {
      this.router.navigate(['/admin']);
    } else {
      this.isError = true;    
    }
  }
}
