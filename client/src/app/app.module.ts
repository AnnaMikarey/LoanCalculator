import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
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

@NgModule({
  declarations: [AdminDashboardComponent, DisclaimerComponent, ModalComponent, UserParentComponent, UserInputComponent, UserOutputComponent, GraphAndChartComponent],
  imports: [BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatDialogModule,
     NgxEchartsModule.forRoot({
    /**
     * This will import all modules from echarts.
     * If you only need custom modules,
     * please refer to [Custom Build] section.
     */
    echarts: () => import('echarts'), // or import('./path-to-my-custom-echarts')
  })],

  providers: [],
  bootstrap: [AppComponent],
  exports: [
    DisclaimerComponent,
    AdminDashboardComponent
  ]
})

export class AppModule {}