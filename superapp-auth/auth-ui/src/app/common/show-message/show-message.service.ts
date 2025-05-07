import {inject, Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ShowMessageComponent} from './show-message.component';

@Injectable({
  providedIn: 'root'
})
export class ShowMessageService {
  private _snackBar = inject(MatSnackBar);

  constructor() { }

  showMessage(message: string, action: string, durationInSeconds = 5) {
    this._snackBar.openFromComponent(ShowMessageComponent, {
      duration: durationInSeconds * 1000,
      data: {
        message: message,
        action: action,
        durationInSeconds: durationInSeconds
      },
    });
  }

  showSuccess(message: string, durationInSeconds = 5) {
    this._snackBar.openFromComponent(ShowMessageComponent, {
      duration: durationInSeconds * 1000,
      data: {
        message: message,
        action: 'success'
      },
    });
  }

  showInfo(message: string, durationInSeconds = 5) {
    this._snackBar.openFromComponent(ShowMessageComponent, {
      duration: durationInSeconds * 1000,
      data: {
        message: message,
        action: 'info'
      },
    });
  }

  showError(message: string, durationInSeconds = 5) {
    this._snackBar.openFromComponent(ShowMessageComponent, {
      duration: durationInSeconds * 1000,
      data: {
        message: message,
        action: 'error'
      },
    });
  }
}
