import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AdminDashboardComponent } from './admin-dashboard/admin-dashboard.component';
import { ReactiveFormsModule } from '@angular/forms';
import { UserParentComponent } from './components/user-parent/user-parent.component';
import { UserInputComponent } from './components/user-input/user-input.component';
import { UserOutputComponent } from './components/user-output/user-output.component';
import { HttpClientModule } from '@angular/common/http';
import { NgxEchartsModule } from 'ngx-echarts';
import { GraphAndChartComponent } from './components/graph-and-chart/graph-and-chart.component';

@NgModule({
  declarations: [AppComponent, AdminDashboardComponent, UserParentComponent, UserInputComponent, UserOutputComponent, GraphAndChartComponent,],
  imports: [BrowserModule, ReactiveFormsModule, HttpClientModule, NgxEchartsModule.forRoot({
    /**
     * This will import all modules from echarts.
     * If you only need custom modules,
     * please refer to [Custom Build] section.
     */
    echarts: () => import('echarts'), // or import('./path-to-my-custom-echarts')
  })],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule { }
