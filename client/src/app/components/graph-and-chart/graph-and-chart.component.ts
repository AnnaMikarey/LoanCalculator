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
  @Input() contractFee: number;
  @Input() registrationFee: number;
  @Input() monthlyBankFee: number;
  position: string = "bottom";
  loading: boolean = false;
  //data for linear and annuity
  linearOrAnnuity: string //= 'annuity';
  constructor(private userUiService: UserUiService) { }

  ngOnInit(): void {
    this.subscription = this.userUiService
      .onToggle()
      .subscribe(value =>
        this.loading = value
      )


    this.userUiService
      .onAnnuityLinearChange()
      .subscribe(value =>
        this.linearOrAnnuity = value)

  }


  ngOnChanges(changes: SimpleChanges): void {
    //todo provide real data
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
          show: true,
          // trigger: 'item',
          position: this.position,
          type: 'none',
          // label: {
          //   show: "none"
          // },
          textStyle: {
            color: "#000",
          },
          // confine: true
        },
        // legend: {
        //   top: '0%',
        //   left: 'center'
        // },
        series: [
          {
            type: 'pie',
            startAngle: 359,
            minAngle: 20,
            animation: true,
            animationTypeUpdate: 'transition',
            animationDuration: 1500,
            radius: ['90%', '80%'],
            avoidLabelOverlap: true,
            label: {
              show: false,
            },
            tooltip: {
              padding: 1,
              trigger: "item",
              alwaysShowContent: false,
              axisPointer: { label: { width: "20px" }, lineStyle: { width: "20px" } },
              show: true,
              position: 'bottom',
              textStyle: {
                fontSize: 11, color: "#000",
                overflow: 'break',
              },
              confine: 'true',
              extraCssText: 'width:auto; white-space:pre-wrap;',
            },
            data: [
              { "value": this.chartData?.requestedLoanAmount, "name": "Requested loan amount", itemStyle: { color: "#DBE4EE" } },
              { "value": this.contractFee, "name": "Contract fee", itemStyle: { color: "#81A4CD" } },

              // { "value": this.chartData?.monthlyPayment, "name": "Monthly payment" },
              { "value": this.chartData?.totalBankFee, "name": "Bank fee", itemStyle: { color: "#F17300" } },
              { "value": this.chartData?.totalInterestAmount, "name": "Total interest amount", itemStyle: { color: "#1585E4" } },
              { "value": this.registrationFee, "name": "Registration fee", itemStyle: { color: "#3E7CB1" } },

            ],
            // emphasis: {
            //   label: {
            //     show: false,
            //     fontSize: 40,
            //     fontWeight: 'bold'
            //   },
            //   labelLine: {
            //     show: false
            //   },
            //   itemStyle: {


            //     shadowBlur: 0,
            //     shadowOffsetX: 0,
            //     shadowColor: 'rgba(0, 0, 0, 0.5)'
            //   },

            // }
          }
        ]
      };
    }
  }

}



