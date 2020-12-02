import {GameService} from "../../../service/game.service";
import {PhaserSettingsService} from "../../phaser-settings.service";
import {UI_CardInHand} from "./model/ui-card-in-hand";

export class BattleScene extends Phaser.Scene {

  public static KEY = 'battleScene';
  private gameService: GameService;
  private settingsService: PhaserSettingsService;

  cards: Phaser.GameObjects.Container[] = [];

  constructor() {
    super({key: BattleScene.KEY});
    this.gameService = window['gameService'];
    this.settingsService = window['settingsService'];
  }

  preload() {
    this.load.image('card', 'assets/card.png');
  }

  create() {
    for (let i = 0; i < this.gameService.playerState.cardsInHand.length; i++) {
      this.cards.push(new UI_CardInHand(this, this.gameService.playerState.cardsInHand[i], i).getContainer());
    }
    this.input.on('drag', function (pointer, gameObject, dragX, dragY) {
      gameObject.x = dragX;
      gameObject.y = dragY;
    });
  }

}
