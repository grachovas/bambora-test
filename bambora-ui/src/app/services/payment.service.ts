import {Injectable} from '@angular/core';
import {IFormDataInterface, IUrlDataInterface} from "./form-data.interface";
import {Observable} from "rxjs";
import {HttpClient, HttpResponse} from "@angular/common/http";
import {SERVER_API_URL} from "../app.constants";

type EntityResponseType = HttpResponse<String>;

@Injectable()
export class PaymentService {
  isSaving: boolean;
  public resourceUrl = 'api/url';

  constructor(protected http: HttpClient) {}

  makeDeposit(data: IFormDataInterface ) {
    console.log(data.amount);
    console.log(data.currency);

    console.log('before post request');

    this.http.post<IUrlDataInterface>(this.resourceUrl, data, { observe: 'response' })
       .subscribe(
         result => {
           //this.onSaveSuccess(result)
           console.log('result');
           console.log(result);
           window.location.href = result.body.url.toString();
         },
           error => {
           //this.onSaveError(error)
             console.log('error');
             console.log(error);
           });
  }

  getUrl(data: IFormDataInterface ): Observable<EntityResponseType> {
    return this.http.post<String>(this.resourceUrl, data, { observe: 'response' });
  }

  protected onSaveSuccess(url) {
    console.log('onSaveSuccess');
    console.log('url' + url);


    this.isSaving = false;
    this.previousState();
    window.location.href = url;
  }

  protected onSaveError(error) {
    console.log('onSaveError');
    console.log(error);

    this.isSaving = false;
  }

  previousState() {
    console.log('succcses')
    window.history.back();
  }
}
