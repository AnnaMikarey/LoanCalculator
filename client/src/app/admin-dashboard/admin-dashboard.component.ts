import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
})
export class AdminDashboardComponent implements OnInit {
  adminForm: FormGroup;

  saveDate!: string;
  saveTime!: string;
  saveDateCurrent!: string;
  saveTimeCurrent!: string;

  validatorsNum = [Validators.required, Validators.pattern(/^\d+\.?\d*$/)];
  validatorsPercent = [
    ...this.validatorsNum,
    Validators.min(0),
    Validators.max(100),
  ];

  ngOnInit() {
    this.adminForm = new FormGroup({
      euribor: new FormControl(3.6, this.validatorsPercent),
      bankMargin: new FormControl(2.5, this.validatorsPercent),
      priceMin: new FormControl(20000, this.validatorsNum),
      priceMax: new FormControl(800000, this.validatorsNum),
      priceDefault: new FormControl(250000, this.validatorsNum),
      depositPercent: new FormControl(20, this.validatorsPercent),
      contractFee: new FormControl(350, this.validatorsNum),
      bankFee: new FormControl(25, this.validatorsNum),
      mortgageFee: new FormControl(250, this.validatorsNum),
    });

    this.saveDate = String(localStorage.getItem('saveDate')).slice(0, 10);
    this.saveTime = String(localStorage.getItem('saveDate')).slice(11, 16);
    this.saveDateCurrent = this.saveDate;
    this.saveTimeCurrent = this.saveTime;
  }

  saveChanges() {
    console.log(this.adminForm, '---', this.adminForm.value);
    localStorage.setItem('adminFormData', JSON.stringify(this.adminForm.value));
    this.saveDateCurrent = this.saveDate;
    this.saveTimeCurrent = this.saveTime;
    localStorage.setItem(
      'saveDate',
      new Date(
        Date.now() - new Date().getTimezoneOffset() * 60 * 1000
      ).toISOString()
    );
  }

  discardChanges() {
    console.log('Changes discarded');
    this.ngOnInit();
  }

  handleMinMaxPrices() {
    const priceMin = this.adminForm.get('priceMin').value;
    const priceMax = this.adminForm.get('priceMax').value;

    if (priceMin > priceMax) {
      this.adminForm.patchValue({
        priceMin: priceMax,
        priceMax: priceMin,
      });
    }
  }
}
