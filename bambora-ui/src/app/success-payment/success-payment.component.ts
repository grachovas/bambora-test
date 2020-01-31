import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {PaymentService} from "../services/payment.service";

@Component({
  selector: 'success-payment',
  templateUrl: './success-payment.component.html',
  styleUrls: ['./success-payment.component.scss'],
})
export class SuccessPaymentComponent {
  constructor() {
  }
}
