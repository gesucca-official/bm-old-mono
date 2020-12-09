import {GameService} from "../../../service/game.service";
import {PhaserSettingsService} from "../../phaser-settings.service";
import {UI_CardInHand} from "./model/ui-card-in-hand";
import {UI_Opponent} from "./model/ui-opponent";
import {UI_Player} from "./model/ui-player";

export class BattleScene extends Phaser.Scene {

  public static KEY = 'battleScene';
  private gameService: GameService;
  private settingsService: PhaserSettingsService;

  player: Phaser.GameObjects.Container;
  cards: Phaser.GameObjects.Container[] = [];
  opponents: Phaser.GameObjects.Container[] = [];
  items: Phaser.GameObjects.Container[] = [];

  constructor() {
    super({key: BattleScene.KEY});
    this.gameService = window['gameService'];
    this.settingsService = window['settingsService'];
  }

  preload() {
    this.load.image('card', 'assets/img/card-template.png');
    this.load.image('item', 'assets/img/item.png');
    this.gameService.opponents.forEach(
      o => {
        this.load.image(o.character.name, 'assets/img/' + o.character.sprite);
        this.load.image(o.character.name + ' BACK',
          'assets/img/' + o.character.sprite.replace('.png', '') + '-back.png');
      });
  }

  create() {
    for (let i = 0; i < this.gameService.opponents.length; i++) {
      const oppo = new UI_Opponent(this, this.gameService.opponents[i], i, this.gameService.opponents.length);
      this.opponents.push(oppo.getOpponent())
      oppo.getItems().forEach(i => this.items.push(i));
    }
    for (let i = 0; i < this.gameService.playerState.cardsInHand.length; i++) {
      this.cards.push(new UI_CardInHand(this, this.gameService.playerState.cardsInHand[i], i).getContainer());
    }
    this.player = new UI_Player(this, this.gameService.playerState).getContainer();

    this.input.on('drag', (pointer, gameObject, dragX, dragY) => {
      gameObject.x = dragX;
      gameObject.y = dragY;
    });
    this.input.on('drop', (pointer, gameObject, dropZone) => {
      alert(gameObject.data.list.card + ' dropped on ' + dropZone.data.list.target)
    })
  }

}
