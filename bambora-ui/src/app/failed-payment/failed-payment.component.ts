import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {PaymentService} from "../services/payment.service";

@Component({
  selector: 'failed-payment',
  templateUrl: './failed-payment.component.html',
  styleUrls: ['./failed-payment.component.scss'],
})
export class FailedPaymentComponent {


  constructor() {
  }
}
