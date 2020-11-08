import {Component, Inject} from '@angular/core';
import {CodeDialogData} from "./code-dialog-data";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";

@Component({
  selector: 'app-code-dialog',
  templateUrl: './code-dialog.component.html',
  styleUrls: ['./code-dialog.component.css']
})
export class CodeDialogComponent {

  constructor(
    public dialogRef: MatDialogRef<CodeDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public dialogData: CodeDialogData) {
  }

  public static getDialogTitle(gameState: any): string {
    // if there are no last resolved moves, game is just begun
    return gameState.lastResolvedMoves.length == 0 ? 'Begin' : 'Turn Resolution';
  }

  // TODO this does not belong here, pass the service to the dialog and let it sort itself
  public static getDialogRawJsonTest(gameService: any): string {
    return gameService.gameState.lastResolvedMoves.length == 0 ? {
        you: gameService.playerId,
        opponents: gameService.opponents.map(o => o.playerId)
      }
      : gameService.gameState.lastResolvedMoves.map(m => {
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
