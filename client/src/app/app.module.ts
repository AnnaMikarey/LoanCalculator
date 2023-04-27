import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';
import { ReactiveFormsModule } from '@angular/forms';

import { UserParentComponent } from './components/user-parent/user-parent.component';
import { UserInputComponent } from './components/user-input/user-input.component';
import { UserOutputComponent } from './components/user-output/user-output.component';
import { NgxEchartsModule } from 'ngx-echarts';
import { GraphAndChartComponent } from './components/graph-and-chart/graph-and-chart.component';

import { DisclaimerComponent } from './disclaimer/disclaimer.component';
import { ModalComponent } from './modal/modal.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AdminLayoutComponent } from './components/admin-layout/admin-layout.component';
import { AdminHeaderComponent } from './components/admin-header/admin-header.component';
import { MatSliderModule } from '@angular/material/slider';
@NgModule({
  declarations: [
    AdminDashboardComponent,
    DisclaimerComponent,
    ModalComponent,
    AdminLayoutComponent,
    AdminHeaderComponent,
    UserParentComponent,
    UserInputComponent,
    UserOutputComponent,
    GraphAndChartComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDialogModule,
    NgxEchartsModule.forRoot({
      echarts: () => import('echarts'),
    }),
    MatSliderModule
  ],

  providers: [],
  bootstrap: [AppComponent],
  exports: [DisclaimerComponent, AdminDashboardComponent],
})
export class AppModule { }
