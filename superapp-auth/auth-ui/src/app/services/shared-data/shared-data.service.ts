import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {LocalStorageService} from '../local-storage/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  _mobileToGetOtp: MobileToGetOTP = null;
  _mobileToGetOtp$: BehaviorSubject<MobileToGetOTP> = null;

  constructor(private localStorage: LocalStorageService) {
    let mobileToGetOTP : MobileToGetOTP = localStorage.getObject("mobileToGetOTP");
    console.log("mobileToGetOTP", mobileToGetOTP);
    this._mobileToGetOtp = mobileToGetOTP ? mobileToGetOTP : null;
    this._mobileToGetOtp$ = new BehaviorSubject<MobileToGetOTP>(this._mobileToGetOtp);
  }

  set mobileToGetOtp(_mobileToGetOtp: string) {
    this._mobileToGetOtp = {
      mobile: _mobileToGetOtp,
      expireAt: new Date().getTime() / 1000 + 120
    };

    this.localStorage.setObject("mobileToGetOTP", this._mobileToGetOtp, 120);
    this._mobileToGetOtp$.next(this._mobileToGetOtp)
  }

  get mobileToGetOtp(): MobileToGetOTP {
    return this._mobileToGetOtp;
  }

  get mobileToGetOtp$(): Observable<MobileToGetOTP> {
    return this._mobileToGetOtp$.asObservable();
  }
}

interface MobileToGetOTP {
  mobile: string,
  expireAt: number
}
