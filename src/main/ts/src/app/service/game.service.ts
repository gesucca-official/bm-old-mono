import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private _gameId: string;
  private _playerId: string;
  private _gameState: any;

  clearGame(): void {
    this._playerId = null;
    this._gameId = null;
    this._gameState = null;
  }

  get playerState(): any {
    return JSON.parse(JSON.stringify(this.gameState.players[this.playerId]));
  }

  get opponents(): any[] {
    const players = JSON.parse(JSON.stringify(this.gameState.players));
    const opp = [];
    Object.keys(players).forEach(k => {
      if (k != this.playerId)
        opp.push(players[k])
    });
    return opp;
  }

  get cardsInHand(): any[] {
    if (this.gameState)
      return this.gameState.players[this.playerId].cardsInHand;
  }

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

}
