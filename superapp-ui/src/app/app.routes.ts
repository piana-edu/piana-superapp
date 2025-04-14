import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: "", redirectTo: "/auth/request-otp", pathMatch: "full"
  },
  {
    path: 'auth/request-otp',
    loadComponent: () => import('./auth/request-otp/request-otp.component')
      .then((m) => m.RequestOtpComponent),
  },
  {
    path: 'auth/verify-otp',
    loadComponent: () => import('./auth/verify-otp/verify-otp.component')
      .then((m) => m.VerifyOtpComponent),
  }
];
