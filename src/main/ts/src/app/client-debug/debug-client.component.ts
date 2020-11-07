import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../service/websocket.service";
import Swal from 'sweetalert2';

@Component({
  selector: 'app-debug-client',
  templateUrl: './debug-client.component.html',
  styleUrls: ['./debug-client.component.css']
})
export class DebugClientComponent implements OnInit {

  joinClicked: boolean;

  playerId = '';
  gameId: string;
  gameState: any;
  playerState: any;
  opponents: any[];
  opponentsNames: string[];
  targetOpponent: string;
  cardsInHand: any[];

  constructor(protected websocketService: WebsocketService) {
  }

  connect(): void {
    this.websocketService.connect();
  }

  isConnected(): boolean {
    return this.websocketService.isConnected();
  }

  joinGame(whichGame: string) {
    this.joinClicked = true;
    this.websocketService.joinGame(this.playerId, whichGame, (sdkEvent => {
      this.gameId = sdkEvent.body;
      this.websocketService.subToGame(
        this.gameId,
        this.playerId,
        (sdkEvent) => alert(sdkEvent.body),
        (sdkEvent) => alert(sdkEvent.body),
        (sdkEvent) => this.onGameUpdate(sdkEvent.body)
      );
      this.websocketService.requestGameView(this.gameId, this.playerId);
    }));
  }

  playCard(cardName: string): void {
    this.websocketService.submitMove({
      playedCardName: cardName,
      playerId: this.playerId,
      targetId: this.targetOpponent,
      gameId: this.gameState.gameId
    });
  }

  onGameUpdate(game: any): void {
    this.gameState = JSON.parse(game);
    this.playerState = JSON.parse(JSON.stringify(this.gameState.players[this.playerId]));
    delete this.playerState.cardsInHand;
    delete this.playerState.deck;

    this.opponents = JSON.parse(JSON.stringify(this.gameState.players));
    delete this.opponents[this.playerId];
    this.opponentsNames = Object.keys(this.opponents);
    this.targetOpponent = this.opponentsNames[0]; // auto select first
    this.opponents = new Array(this.opponents); // ngFor is complaining otherwise

    this.cardsInHand = this.gameState.players[this.playerId].cardsInHand;

    if (this.gameState.lastResolvedMoves !== null) {
      let turnReport = '';
      this.gameState.lastResolvedMoves.forEach((move) =>
        turnReport = turnReport += ('<b>' + move.playerId + '->' + move.targetId
          + '</b><br>'
          + move.playedCardName + ' self:' + JSON.stringify(move.moveEffectToSelf) + ' target:' + JSON.stringify(move.moveEffectToTarget)
          + '<br><br>')
      );
      Swal.fire({
        title: turnReport === '' ? 'Game Begins' : 'Turn Resolution',
        html: turnReport,
        confirmButtonText: 'Ok'
      });
    }

    if (this.gameState.over) {
      Swal.fire('GAME IS OVER!!! WINNER: ' + this.gameState.winner);

      this.websocketService.unsubToGame(this.gameId);

      this.joinClicked = false;
      this.playerId = null;
      this.gameId = null;
      this.gameState = null;
      this.playerState = null;
      this.opponents = [];
      this.opponentsNames = [];
      this.targetOpponent = null;
      this.cardsInHand = [];
    }
  }

  logGameState(): void {
    console.log(this.gameState);
  }

  ngOnInit(): void {
  }

}
