import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../service/websocket.service";
import {MatDialog} from "@angular/material/dialog";
import {CodeDialogComponent} from "./code-dialog/code-dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {GameService} from "../service/game.service";

@Component({
  selector: 'app-debug-client',
  templateUrl: './debug-client.component.html',
  styleUrls: ['./debug-client.component.css']
})
export class DebugClientComponent implements OnInit {

  joinClicked: boolean;

  playerState: any;
  opponents: any[];
  opponentsNames: string[];
  targetOpponent: string;
  cardsInHand: any[];

  constructor(protected websocketService: WebsocketService,
              public  gameService: GameService,
              public dialog: MatDialog,
              public snackBar: MatSnackBar) {
  }

  connect(): void {
    this.websocketService.connect();
  }

  isConnected(): boolean {
    return this.websocketService.isConnected();
  }

  joinGame(whichGame: string) {
    this.joinClicked = true;
    this.websocketService.joinGame(this.gameService.playerId, whichGame, (sdkEvent => {
      this.gameService.gameId = sdkEvent.body;
      this.websocketService.subToGame(
        this.gameService.gameId,
        this.gameService.playerId,
        (sdkEvent) => this.snackBar.open(sdkEvent.body, 'ok', {duration: 2000}),
        (sdkEvent) => this.snackBar.open(sdkEvent.body, 'ok', {duration: 2000}),
        (sdkEvent) => this.onGameUpdate(sdkEvent.body)
      );
      this.websocketService.requestGameView(this.gameService.gameId, this.gameService.playerId);
    }));
  }

  playCard(cardName: string): void {
    this.websocketService.submitMove({
      playedCardName: cardName,
      playerId: this.gameService.playerId,
      targetId: this.targetOpponent,
      gameId: this.gameService.gameId
    });
  }

  onGameUpdate(game: any): void {
    this.gameService.gameState = JSON.parse(game);
    this.playerState = JSON.parse(JSON.stringify(this.gameService.gameState.players[this.gameService.playerId]));
    delete this.playerState.cardsInHand;
    delete this.playerState.deck;

    this.opponents = JSON.parse(JSON.stringify(this.gameService.gameState.players));
    delete this.opponents[this.gameService.playerId];
    this.opponentsNames = Object.keys(this.opponents);
    this.targetOpponent = this.opponentsNames[0]; // auto select first
    this.opponents = new Array(this.opponents); // ngFor is complaining otherwise

    this.cardsInHand = this.gameService.gameState.players[this.gameService.playerId].cardsInHand;

    if (this.gameService.gameState.lastResolvedMoves !== null) {
      this.dialog.open(CodeDialogComponent, {
          width: 'fit-content',
          data: {
            // if there are no last resolved moves, game is just begun
            title: this.gameService.gameState.lastResolvedMoves.length == 0 ? 'Begin' : 'Turn Resolution',
            data: this.gameService.gameState.lastResolvedMoves.length == 0 ? {
                you: this.gameService.playerId,
                opponents: this.opponentsNames
              }
              : this.gameService.gameState.lastResolvedMoves.map(m => {
                // clean null values and game id from moves?? not sure if it's a good idea, can hide important things
                delete m.gameId;
                Object.keys(m).forEach(k => {
                  if (m[k] === null)
                    delete m[k];
                })
                return m;
              })
          }
        }
      );
    }

    if (this.gameService.gameState.over) {
      this.dialog.open(CodeDialogComponent, {
        width: 'fit-content',
        data: {
          title: 'WINNER: ' + this.gameService.gameState.winner,
          data: this.gameService.gameState.lastResolvedMoves
        }
      });

      this.websocketService.unsubToGame(this.gameService.gameId);

      this.gameService.clearGame();

      this.joinClicked = false;
      this.playerState = null;
      this.opponents = [];
      this.opponentsNames = [];
      this.targetOpponent = null;
      this.cardsInHand = [];
    }
  }

  logGameState(): void {
    console.log(this.gameService.gameState);
  }

  ngOnInit(): void {
  }

}
