import { Component, OnInit, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { UserUiService } from 'src/app/services/user-ui.service';
@Component({
  selector: 'app-graph-and-chart',
  templateUrl: './graph-and-chart.component.html',
  styleUrls: ['./graph-and-chart.component.css']
})
export class GraphAndChartComponent implements OnChanges, OnInit {
  subscription!: Subscription

  options: any;
  pageTitle: string = 'Deal Share by Contact';
  @Input() chartData: any;
  // loading$: Observable<boolean>;
  loading: boolean = false;
  //data for linear and annuity
  constructor(private userUiService: UserUiService) { }

  ngOnInit(): void {
    this.subscription = this.userUiService
    .onToggle()
    .subscribe(value =>
      this.loading = value)
  }

  ngOnChanges(changes: SimpleChanges): void {

    this.options = {
      tooltip: {
        trigger: 'item'
      },
      legend: {
        top: '0%',
        left: 'center'
      },
      series: [
        {
          type: 'pie',
          radius: ['90%', '80%'],
          avoidLabelOverlap: false,
          label: {
            show: false,
            position: 'center'
          },
          data: [
            { "value": this.chartData?.requestedLoanAmount, "name": "requestedLoanAmount" },
            { "value": this.chartData?.requestedLoanAmount, "name": "interest" },//only mock
            { "value": this.chartData?.euroborAndRate, "name": "euroborAndRate" },
            { "value": this.chartData?.bankFee, "name": "bankFee" },
            { "value": this.chartData?.mortgageRegistration, "name": "mortgageRegistration" },
          ],
          emphasis: {
            label: {
              show: true,
              fontSize: 40,
              fontWeight: 'bold'
            },
            labelLine: {
              show: false
            },
            itemStyle: {

              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    };
  }


}