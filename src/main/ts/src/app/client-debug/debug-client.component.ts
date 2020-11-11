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

  playCard(event: any): void {
    this.websocketService.submitMove({
      playedCardName: event.cardName,
      playerId: this.gameService.playerId,
      targetId: event.target,
      gameId: this.gameService.gameId,
      choices: event.choices
    });
  }

  onGameUpdate(game: any): void {
    this.gameService.gameState = JSON.parse(game);

    if (!this.gameService.gameState.over) {
      this.dialog.open(CodeDialogComponent, {
        width: 'fit-content',
        data: {
          title: this.getDialogTitle(),
          html: this.getDialogHtml(),
          jsonTextData: this.getDialogRawJsonTest()
        }
      });
    }

    if (this.gameService.gameState.over) {
      this.dialog.open(CodeDialogComponent, {
        width: 'fit-content',
        data: {
          title: 'WINNER: ' + this.gameService.gameState.winner,
          html: this.getDialogHtml(),
          data: this.gameService.gameState.lastResolvedMoves
        }
      });

      this.websocketService.unsubToGame(this.gameService.gameId);
      this.gameService.clearGame();
      this.joinClicked = false;
    }
  }

  logGameState(): void {
    console.log(this.gameService.gameState);
  }

  private getDialogTitle(): string {
    // if there are no last resolved moves, game is just begun
    return this.gameService.gameState.lastResolvedMoves.length == 0 ? 'Begin' : 'Turn Resolution';
  }

  private getDialogRawJsonTest(): string {
    return this.gameService.gameState.lastResolvedMoves.length == 0 ?
      this.getPlayersRawJsonText() : this.gameService.gameState.lastResolvedMoves
  }

  private getPlayersRawJsonText() {
    return {
      you: this.gameService.playerId,
      opponents: this.gameService.opponents.map(o => o.playerId)
    };
  }

  private getDialogHtml() {
    return this.gameService.gameState.lastResolvedMoves.length == 0 ?
      this.getBeginGameHtml()
      : this.getMovesHtml();
  }

  private getBeginGameHtml() {
    return '<p><b>Player</b>: ' + this.gameService.playerId + '</p><p><b>Opponent</b>(s): <ul><li>'
      + this.gameService.opponents.map(o => o.playerId).reduce((a, b) => a + '</li><li>' + b) + '</li></ul></p>';
  }

  private getMovesHtml() {
    return this.gameService.gameState.lastResolvedMoves.map(m => {
      return '<p><b>' + m.playerId + '</b> ---> <b>' + m.targetId + '</b></p>'
        + '<p><b>' + m.playedCardName + '</b></p>'
        + (!m.moveEffectToSelf ? '' : '<p>SELF: <ul><li>' + m.moveEffectToSelf.reduce((a, b) => a + '</li><li>' + b) + '</li></ul>')
        + '</p>'
        + (!m.moveEffectToTarget ? '' : '<p>TARGET: <ul><li>' + m.moveEffectToTarget.reduce((a, b) => a + '</li><li>' + b) + '</li></ul>')
        + '</p>';
    }).reduce((a, b) => a + '<br>' + b)
  }

  getTargets(card: any): string[] {
    const targets = [];
    if (card.canTarget.includes('SELF'))
      targets.push('SELF')
    if (card.canTarget.includes('OPPONENT'))
      this.gameService.opponents.map(o => o.playerId)
        .forEach(o => targets.push(o))
    return targets;
  }

  discardableCards() {
    return this.gameService.cardsInHand.filter(card => !card.characterBound).map(card => card.name);
  }
}
