import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ReactiveFormsModule } from '@angular/forms';
import { DisclaimerComponent } from './disclaimer/disclaimer.component';
import { ModalComponent } from './modal/modal.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';


@NgModule({
  declarations: [AppComponent, AdminDashboardComponent, DisclaimerComponent, ModalComponent],
  imports: [BrowserModule, ReactiveFormsModule, BrowserAnimationsModule, MatButtonModule, MatDialogModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
