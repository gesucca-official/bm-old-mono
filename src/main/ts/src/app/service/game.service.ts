import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GameService {
  get playerId(): string {
    return this._playerId;
  }

  set playerId(value: string) {
    this._playerId = value;
  }

  get gameState(): any {
    return this._gameState;
  }

  set gameState(value: any) {
    this._gameState = value;
  }

  get gameId(): string {
    return this._gameId;
  }

  set gameId(value: string) {
    this._gameId = value;
  }

  private _gameId: string;
  private _playerId: string;
  private _gameState: any;

  constructor() {
  }

  clearGame(): void {
    this._playerId = null;
    this._gameId = null;
    this._gameState = null;
  }

}
