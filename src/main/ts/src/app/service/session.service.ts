import {Injectable} from '@angular/core';
import {QueuedUser, User} from "../model/user";
import {UserAccountData} from "../model/user-account-data";

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  private _queued: boolean;
  private _queuedFor: string;

  private _usersConnected: User[] = [];
  private _usersInCurrentQueue: QueuedUser[] = [];

  private _userAccountData: UserAccountData = {
    username: null,
    email: null,
    role: null,
    decks: [],
    collection: {
      characters: [],
      cards: []
    }
  };

  get queued(): boolean {
    return this._queued;
  }

  set queued(value: boolean) {
    this._queued = value;
  }

  get queuedFor(): string {
    return this._queuedFor;
  }

  set queuedFor(value: string) {
    this._queuedFor = value;
  }

  get usersInCurrentQueue(): QueuedUser[] {
    return this._usersInCurrentQueue;
  }

  set usersInCurrentQueue(value: QueuedUser[]) {
    this._usersInCurrentQueue = value;
  }

  get howManyUsersConnected(): number {
    return this._usersConnected.length;
  }

  get usersConnected(): User[] {
    return this._usersConnected;
  }

  set usersConnected(value: User[]) {
    this._usersConnected = value;
  }

  get userAccountData(): UserAccountData {
    return this._userAccountData;
  }

  set userAccountData(value: UserAccountData) {
    this._userAccountData = value;
  }

}
