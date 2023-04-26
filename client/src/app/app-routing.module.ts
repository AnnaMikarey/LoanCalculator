import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { LoginComponent } from './login/login.component';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { BrowserModule } from '@angular/platform-browser';
import { AdminLayoutComponent } from './components/admin-layout/admin-layout.component';
import { UserParentComponent } from './components/user-parent/user-parent.component';

const appRoutes: Routes = [
  { path: '', component: UserParentComponent },
  { path: 'login', component: LoginComponent },
  { path: 'admin', component: AdminLayoutComponent },
];

@NgModule({
  imports: [BrowserModule, FormsModule, RouterModule.forRoot(appRoutes)],
  declarations: [AppComponent, LoginComponent],
  bootstrap: [AppComponent],
})
export class AppRoutingModule {}
