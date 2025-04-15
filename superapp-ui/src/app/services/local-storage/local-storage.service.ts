import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStorageService {

  constructor() {
  }

  setItem(key: string, value: string, expireAfterSeconds: number = 24 * 60 * 60 /*default is 24 hours*/): void {
    let item = {
      data: value,
      expireTime: null
    }
    if (expireAfterSeconds && expireAfterSeconds > 0) {
      /*
          setting the expireTime currentTime + expiry Time
          provided when method was called.
      */
      item.expireTime = Date.now() + (expireAfterSeconds * 1000);
    }
    localStorage.setItem(key, JSON.stringify(item));
  }

  // Get a value from local storage
  getItem(key: string): string | null {
    // getting the data from localStorage using the key
    let result = JSON.parse(localStorage.getItem(key));

    if (result && typeof result === 'object' && !Array.isArray(result)) {
      /*
          if data expireTime is less than current time
          means item has expired,
          in this case removing the item using the key
          and return the null.
      */
      if (result.expireTime <= Date.now()) {
        localStorage.removeItem(key);
        return null;
      }
      // else return the data.
      return result.data;
    }
    //if there is no data provided the key, return null.
    return null;
  }

  // Set an object in local storage with
  setObject(key: string, value: object, expireAfterSeconds: number = 24 * 60 * 60 /*default is 24 hours*/): void {
    this.setItem(key, JSON.stringify(value), expireAfterSeconds);
  }

  // Get an object from local storage
  getObject(key: string): object | any | null {
    let item = this.getItem(key);
    if (item && typeof item === "object")
      return JSON.parse(item);
    return null;
  }

  // Remove a value from local storage
  removeItem(key: string): void {
    localStorage.removeItem(key);
  }

  // Clear all items from local storage
  clear(): void {
    localStorage.clear();
  }
}
