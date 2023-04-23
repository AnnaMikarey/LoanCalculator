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
  linearOrAnnuity: string //= 'annuity';
  constructor(private userUiService: UserUiService) { }

  ngOnInit(): void {
    this.subscription = this.userUiService
      .onToggle()
      .subscribe(value =>
        this.loading = value)

    this.userUiService
      .onAnnuityLinearChange()
      .subscribe(value =>
        this.linearOrAnnuity = value)


  }

  ngOnChanges(changes: SimpleChanges): void {
    if (this.linearOrAnnuity == "linear") {
      const xAxisData = [];
      const data1 = [];
      const data2 = [];

      for (let i = 0; i < 100; i++) {
        xAxisData.push('category' + i);
        data1.push((Math.sin(i / 5) * (i / 5 - 10) + i / 6) * 5);
        data2.push((Math.cos(i / 5) * (i / 5 - 10) + i / 6) * 5);
      }
      this.options = {
        legend: {
          data: ['bar', 'bar2'],
          align: 'left',
        },
        tooltip: {},
        xAxis: {
          data: xAxisData,
          silent: false,
          splitLine: {
            show: false,
          },
        },
        yAxis: {},
        series: [
          {
            name: 'bar',
            type: 'bar',
            data: data1,
            animationDelay: (idx) => idx * 10,
          },
          {
            name: 'bar2',
            type: 'bar',
            data: data2,
            animationDelay: (idx) => idx * 10 + 100,
          },
        ],
        animationEasing: 'elasticOut',
        animationDelayUpdate: (idx) => idx * 5,
      };
    } else {


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
            data: [//only mock
              { "value": this.chartData?.requestedLoanAmount, "name": "requestedLoanAmount" },
              { "value": this.chartData?.requestedLoanAmount, "name": "interest" },
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


}