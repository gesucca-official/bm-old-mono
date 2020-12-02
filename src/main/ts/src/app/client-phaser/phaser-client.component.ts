import {Component} from '@angular/core';
import Phaser from 'phaser';
import {PhaserSettingsService} from "./phaser-settings.service";
import {WebsocketService} from "../service/websocket.service";
import {GameService} from "../service/game.service";
import {BattleScene} from "./scnenes/battle/battle-scene";

@Component({
  selector: 'app-phaser-client',
  templateUrl: './phaser-client.component.html',
  styleUrls: ['./phaser-client.component.css']
})
export class PhaserClientComponent {

  battleScene: boolean;

  phaserGame: Phaser.Game;
  config: Phaser.Types.Core.GameConfig;

  constructor(
    protected websocketService: WebsocketService,
    protected gameService: GameService
  ) {
  }

  joinGame(whichGame: string) {
    window['gameService'] = this.gameService; // if this hack works its huge

    this.websocketService.joinGame(this.gameService.playerId, whichGame, (sdkEvent => {
      this.gameService.gameId = sdkEvent.body;
      this.websocketService.subToGame(
        this.gameService.gameId,
        this.gameService.playerId,
        // TODO obviously this
        (sdkEvent) => console.log(sdkEvent.body),
        (sdkEvent) => console.log(sdkEvent.body),
        (sdkEvent) => {
          this.gameService.gameState = JSON.parse(sdkEvent.body);
          this.createGame()
        }
      );
      this.websocketService.requestGameView(this.gameService.gameId, this.gameService.playerId);


    }));
  }

  private createGame() {
    this.battleScene = true;

    this.config = {
      type: Phaser.AUTO,
      scale: {
        mode: Phaser.Scale.FIT,
        parent: 'phaserContainer',
        autoCenter: Phaser.Scale.CENTER_BOTH,
        width: PhaserSettingsService.instance.getScreenWidth(),
        height: PhaserSettingsService.instance.getScreenHeight()
      },
      scene: [BattleScene]
    }
    this.phaserGame = new Phaser.Game(this.config);
  }

}
