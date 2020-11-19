import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  private _queued: boolean;
  private _usersConnected: string[] = [];

  get queued(): boolean {
    return this._queued;
  }

  set queued(value: boolean) {
    this._queued = value;
  }

  get howManyUsersConnected(): number {
    return this._usersConnected.length;
  }

  get usersConnected(): string[] {
    return this._usersConnected;
  }

  set usersConnected(value: string[]) {
    this._usersConnected = value;
  }

}
