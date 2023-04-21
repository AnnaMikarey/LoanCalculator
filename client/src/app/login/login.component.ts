import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
})
export class LoginComponent {
  username: string = '';
  password: string = '';

  constructor(private router: Router) {}

  login(): void {
    const hardcodedUsername: string = 'admin';
    const hardcodedPassword: string = 'password';

    if (this.username === hardcodedUsername && this.password === hardcodedPassword) {
      this.router.navigate(['/admin']);
    } else {
      alert('Invalid login or password');
    }
  }
}