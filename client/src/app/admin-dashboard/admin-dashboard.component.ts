import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.css'],
})
export class AdminDashboardComponent implements OnInit {
  adminForm: FormGroup;

  ngOnInit() {
    this.adminForm = new FormGroup({
      euribor: new FormControl(3.6),
      bankMargin: new FormControl(2.5),
      priceMin: new FormControl(20000),
      priceMax: new FormControl(800000),
      priceDefault: new FormControl(250000),
      depositPercent: new FormControl(20),
      contractFee: new FormControl(350),
      bankFee: new FormControl(25),
      mortgageFee: new FormControl(250),
    });
  }

  saveChanges() {
    console.log(this.adminForm, '---', this.adminForm.value);
  }

  discardChanges() {
    console.log('Changes discarded');
  }
}
