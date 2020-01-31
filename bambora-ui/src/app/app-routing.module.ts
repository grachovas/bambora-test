import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {MainComponent} from "./main/main.component";
import {XyzComponent} from "./xyz/xyz.component";
import {FailedPaymentComponent} from "./failed-payment/failed-payment.component";
import {SuccessPaymentComponent} from "./success-payment/success-payment.component";


const routes: Routes = [
  { path: '', component: MainComponent },
  { path: 'xyz', component: XyzComponent },
  { path: 'failed-payment', component: FailedPaymentComponent },
  { path: 'success-payment', component: SuccessPaymentComponent },
  { path: '**', component: MainComponent } // PageNotFoundComponent
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
