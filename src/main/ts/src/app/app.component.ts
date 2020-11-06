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
    }, (error) => {
      console.log('errorCallBack -> ' + error);
      setTimeout(() => {
        this.connect();
      }, 1000);
    });
  }

  joinGame(whichGame: string, endpoint: string) {
    this.joinClicked = true;
    const subscription = this.stompClient.subscribe(endpoint + '/game/' + whichGame + '/ready', (sdkEvent) => {
      subscription.unsubscribe();
      this.initGame(sdkEvent.body);
    });
    this.stompClient.send('/app/game/' + whichGame + '/join', {}, this.playerId);
  }

  playCard(cardName: string): void {
    const move = {
      playedCardName: cardName,
      playerId: this.playerId,
      targetId: this.targetOpponent,
      gameId: this.gameState.gameId
    };
    this.stompClient.send('/app/game/' + this.gameId + '/move', {}, JSON.stringify(move));
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

  logGameState(): void {
    console.log(this.gameState);
  }

  private initGame(gameId: string) {
    this.gameId = gameId;
    // alert about other players moves
    this.stompClient.subscribe('/topic/game/' + gameId + '/move', (sdkEvent) => {
      this.snackBar.open(sdkEvent.body, 'close', {duration: 5000})
    });
    // when there's an update about the game get your view
    this.stompClient.subscribe('/topic/game/' + gameId + '/update', (sdkEvent) => {
        this.stompClient.send('/app/game/' + gameId + '/' + this.playerId + '/view', {});
      }
    );
    // update game when there's new view
    this.stompClient.subscribe('/user/queue/game/' + gameId + '/' + this.playerId + '/view', (sdkEvent) =>
      this.onGameUpdate(sdkEvent.body)
    );
    // alert me when I select an illegal move
    this.stompClient.subscribe('/user/queue/player/action/illegalMove', (sdkEvent) =>
      this.snackBar.open(sdkEvent.body, 'close', {duration: 5000})
    );

    this.stompClient.send('/app/game/' + gameId + '/' + this.playerId + '/view', {});
  }

}
