import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import {FormControl, FormGroup} from "@angular/forms";
import {PaymentService} from "../services/payment.service";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss'],
})
export class MainComponent implements OnInit {

  public url : any;

  private myGroup: FormGroup;
  currencies = [];

  constructor(private router: Router,
              private paymentService: PaymentService) {
    this.currencies = this.getOrders();
  }

  ngOnInit() {
    this.myGroup = new FormGroup({
      'amount': new FormControl(''),
      'currency': new FormControl('')
    });
  }

  getOrders() {
    return [
      { id: 'EUR', name: 'EUR' },
      { id: 'SEK', name: 'SEK' },
      { id: 'DKK', name: 'DKK' },
      { id: 'NOK', name: 'NOK' }
    ];
  }

  submit(data) {
    console.log(data);
    this.paymentService.makeDeposit(data);
  }
}
