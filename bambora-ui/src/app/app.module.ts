import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MainComponent} from "./main/main.component";
import { XyzComponent } from './xyz/xyz.component';
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {ReactiveFormsModule} from "@angular/forms";
import {PaymentService} from "./services/payment.service";
import {HttpClientModule} from "@angular/common/http";
import {FailedPaymentComponent} from "./failed-payment/failed-payment.component";
import {SuccessPaymentComponent} from "./success-payment/success-payment.component";

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    XyzComponent,
    FailedPaymentComponent,
    SuccessPaymentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatFormFieldModule,
    MatSelectModule,
    MatInputModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [PaymentService],
  bootstrap: [
    AppComponent,
    MainComponent,
    FailedPaymentComponent,
    SuccessPaymentComponent]
})
export class AppModule { }
