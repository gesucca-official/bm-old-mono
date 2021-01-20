import {Component, EventEmitter, Output} from '@angular/core';
import Phaser from 'phaser';
import {PhaserSettingsService} from "./phaser-settings.service";
import {WebsocketService} from "../service/websocket.service";
import {GameService} from "../service/game.service";
import {BattleScene} from "./scnenes/battle/battle-scene";
import {Deck} from "../model/deck";

@Component({
  selector: 'app-phaser-client',
  templateUrl: './phaser-client.component.html',
  styleUrls: ['./phaser-client.component.css']
})
export class PhaserClientComponent {

  @Output() currentlyGaming: EventEmitter<boolean> = new EventEmitter<boolean>();

  config: Phaser.Types.Core.GameConfig;

  constructor(
    protected websocketService: WebsocketService,
    protected gameService: GameService,
    protected settingsService: PhaserSettingsService
  ) {
  }

  joinGame(whichGame: { game: string, deck?: Deck }) {
    window['gameService'] = this.gameService;
    window['settingsService'] = this.settingsService;

    this.websocketService.joinGame(this.gameService.playerId, whichGame.game, (sdkEvent => {
      this.gameService.gameId = sdkEvent.body;
      this.websocketService.subToGame(
        this.gameService.gameId,
        this.gameService.playerId,
        // TODO obviously this
        (sdkEvent) => console.log(sdkEvent.body),
        (sdkEvent) => console.log(sdkEvent.body),
        (sdkEvent) => {
          this.gameService.gameState = JSON.parse(sdkEvent.body);
          this.initGameConfig() // cannot initialize game config here!!!
        }
      );
      this.websocketService.requestGameView(this.gameService.gameId, this.gameService.playerId);


    }), whichGame.deck);
  }

  private initGameConfig() {
    this.currentlyGaming.emit(true);

    this.config = {
      type: Phaser.AUTO,
      scale: {
        mode: Phaser.Scale.FIT,
        parent: 'phaserClient',
        autoCenter: Phaser.Scale.NONE,
        width: this.settingsService.getScreenWidth(),
        height: this.settingsService.getScreenHeight()
      },
      scene: [BattleScene]
    }
  }

}
