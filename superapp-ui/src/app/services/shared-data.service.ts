import { Injectable } from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SharedDataService {
  _mobileToGetOtp: string = null;
  _mobileToGetOtp$: BehaviorSubject<string> = null;

  constructor() {
    this._mobileToGetOtp$ = new BehaviorSubject<string>('');
  }

  set mobileToGetOtp(_mobileToGetOtp: string ) {
    this._mobileToGetOtp = _mobileToGetOtp;
    this._mobileToGetOtp$.next(this._mobileToGetOtp)
  }

  get mobileToGetOtp(): string {
    return this._mobileToGetOtp;
  }

  get mobileToGetOtp$(): Observable<string> {
    return this._mobileToGetOtp$.asObservable();
  }


}
