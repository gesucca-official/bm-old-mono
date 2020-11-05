import {Component} from '@angular/core';

import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import Swal from 'sweetalert2';
import {MatSnackBar} from '@angular/material/snack-bar';
import {environment} from "../environments/environment";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  stompClient: any;

  isConnected: boolean;

  joinClicked: boolean;

  playerId: string;
  gameId: string;

  gameState: any;

  playerState: any;
  opponents: any[];
  opponentsNames: string[];
  targetOpponent: string;

  cardsInHand: any[];

  gameOver: boolean;

  constructor(private snackBar: MatSnackBar) {
  }

  connect(): void {
    this.isConnected = false;

    const ws = new SockJS(environment.websocketServerEndpoint);
    this.stompClient = Stomp.over(ws);

    this.stompClient.connect({}, () => {
      this.isConnected = true;
      this.stompClient.subscribe('/topic/game/1v1/join', (sdkEvent) =>
        this.snackBar.open(sdkEvent.body, 'close', {duration: 5000})
      );
      this.stompClient.subscribe('/topic/game/update', (sdkEvent) => {
          if (!this.gameId) {
            this.gameId = sdkEvent.body;
          }
          const info = {
            playerId: this.playerId,
            gameId: this.gameId
          };
          this.stompClient.send('/app/game/view', {}, JSON.stringify(info));
        }
      );
      this.stompClient.subscribe('/topic/game/move', (sdkEvent) =>
        this.snackBar.open(sdkEvent.body, 'close', {duration: 5000})
      );
      this.stompClient.subscribe('/user/queue/game/move', (sdkEvent) =>
        this.snackBar.open(sdkEvent.body, 'close', {duration: 5000})
      );
      this.stompClient.subscribe('/user/queue/game/view', (sdkEvent) =>
        this.onGameUpdate(sdkEvent.body)
      );
    }, (error) => {
      console.log('errorCallBack -> ' + error);
      setTimeout(() => {
        this.connect();
      }, 1000);
    });
  }

  join1v1Game(): void {
    this.joinClicked = true;
    this.stompClient.send('/app/game/1v1/join', {}, this.playerId);
  }

  join4ffaComGame(): void {
    this.joinClicked = true;
    this.stompClient.send('/app/game/4ffaCom/join', {}, this.playerId);
  }

  join1vComGame(): void {
    this.joinClicked = true;
    this.stompClient.send('/app/game/1vCom/join', {}, this.playerId);
  }

  join4ffaGame(): void {
    this.joinClicked = true;
    this.stompClient.send('/app/game/4ffa/join', {}, this.playerId);
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
      this.snackBar.open('GAME IS OVER!!! WINNER: ' + this.gameState.winner, 'close', {verticalPosition: 'top'});
      this.gameOver = true;
    }
  }

  playCard(cardName: string): void {
    const move = {
      playedCardName: cardName,
      playerId: this.playerId,
      targetId: this.targetOpponent,
      gameId: this.gameState.gameId
    };
    this.stompClient.send('/app/game/move', {}, JSON.stringify(move));
  }

  logGameState(): void {
    console.log(this.gameState);
  }
}
