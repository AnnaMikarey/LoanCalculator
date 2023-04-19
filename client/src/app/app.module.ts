import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ReactiveFormsModule } from '@angular/forms';
import { UserParentComponent } from './components/user-parent/user-parent.component';
import { UserInputComponent } from './components/user-input/user-input.component';
import { UserOutputComponent } from './components/user-output/user-output.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [AppComponent, AdminDashboardComponent, UserParentComponent, UserInputComponent, UserOutputComponent],
  imports: [BrowserModule, ReactiveFormsModule, HttpClientModule,],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
