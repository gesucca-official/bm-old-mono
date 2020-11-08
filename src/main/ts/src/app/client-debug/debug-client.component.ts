import {Component} from '@angular/core';
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
export class DebugClientComponent {

  joinClicked: boolean;
  selectedTargetOpponent: string;

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
        (sdkEvent) => this.snackBar.open(sdkEvent.body, 'ok', {duration: 3000}),
        (sdkEvent) => this.snackBar.open(sdkEvent.body, 'ok', {duration: 1500}),
        (sdkEvent) => this.onGameUpdate(sdkEvent.body)
      );
      this.websocketService.requestGameView(this.gameService.gameId, this.gameService.playerId);
    }));
  }

  playCard(cardName: string): void {
    this.websocketService.submitMove({
      playedCardName: cardName,
      playerId: this.gameService.playerId,
      targetId: this.selectedTargetOpponent,
      gameId: this.gameService.gameId
    });
  }

  onGameUpdate(game: any): void {
    this.gameService.gameState = JSON.parse(game);
    this.selectedTargetOpponent = this.gameService.opponents[0].playerId; // auto select first

    if (!this.gameService.gameState.over) {
      this.dialog.open(CodeDialogComponent, {
        width: 'fit-content',
        data: {
          title: CodeDialogComponent.getDialogTitle(this.gameService.gameState),
          jsonTextData: CodeDialogComponent.getDialogRawJsonTest(this.gameService)
        }
      });
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
      this.selectedTargetOpponent = null;
    }
  }

  logGameState(): void {
    console.log(this.gameService.gameState);
  }

}
