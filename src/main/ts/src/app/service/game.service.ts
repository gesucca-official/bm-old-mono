import {Injectable} from '@angular/core';
import {GameState} from "../model/game-state";
import {Player} from "../model/player";
import {Card} from "../model/card";

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private _gameId: string;
  private _playerId: string;
  private _gameState: GameState;

  clearGame(): void {
    this._gameId = null;
    this._gameState = null;
  }

  get playerState(): Player {
    return JSON.parse(JSON.stringify(this.gameState.players[this.playerId]));
  }

  get opponents(): Player[] {
    const players = JSON.parse(JSON.stringify(this.gameState.players));
    const opp = [];
    Object.keys(players).forEach(k => {
      if (k != this.playerId)
        opp.push(players[k])
    });
    return opp;
  }

  get cardsInHand(): Card[] {
    if (this.gameState)
      return this.gameState.players[this.playerId].cardsInHand;
  }

  get playerId(): string {
    return this._playerId;
  }

  set playerId(value: string) {
    this._playerId = value;
  }

  get gameState(): GameState {
    return this._gameState;
  }

  set gameState(value: GameState) {
    this._gameState = value;
  }

  get gameId(): string {
    return this._gameId;
  }

  set gameId(value: string) {
    this._gameId = value;
  }

}
