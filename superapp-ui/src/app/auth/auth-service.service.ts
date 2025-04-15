import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {SharedDataService} from '../services/shared-data/shared-data.service';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  constructor(
    private sharedData: SharedDataService,
    private router: Router,
    private httpClient: HttpClient) {}

  requestOTP(mobile: string, captcha: string) {
    this.httpClient.post("api/v1/auth/request-otp", {mobile: mobile, captcha: captcha},
      {
        observe: 'response',
      }).subscribe(
      (data) => {
        if (data.status === 200 || data.status === 202) {
          this.sharedData.mobileToGetOtp = mobile;
          this.router.navigate(['/auth/verify-otp']);
          console.log(`Got a successful status code: ${data.status}`);
        }
        console.log(`This contains body: ${data.body}`);
      },
      (err: HttpErrorResponse) => {
        if (err.status === 403) {
          console.error('403 status code caught');
        }
      },
    )
  }
}
