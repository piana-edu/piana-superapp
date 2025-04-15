import { CanActivateFn } from '@angular/router';
import {Inject} from '@angular/core';
import {SharedDataService} from '../services/shared-data/shared-data.service';

export const authGuard: CanActivateFn = (route, state) => {
  const sharedDataService: SharedDataService = Inject(SharedDataService);
  
  return true;
};
