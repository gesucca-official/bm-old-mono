import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsersService {

  private _usersConnectedToServer: string[] = [];

  get howManyUsersConnected(): number {
    return this._usersConnectedToServer.length;
  }

  get usersConnectedToServer(): string[] {
    return this._usersConnectedToServer;
  }

  set usersConnectedToServer(value: string[]) {
    this._usersConnectedToServer = value;
  }

}
