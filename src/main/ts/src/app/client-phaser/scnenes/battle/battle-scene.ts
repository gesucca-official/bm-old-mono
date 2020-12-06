import {GameService} from "../../../service/game.service";
import {PhaserSettingsService} from "../../phaser-settings.service";
import {UI_CardInHand} from "./model/ui-card-in-hand";
import {UI_Opponent} from "./model/ui-opponent";

export class BattleScene extends Phaser.Scene {

  public static KEY = 'battleScene';
  private gameService: GameService;
  private settingsService: PhaserSettingsService;

  cards: Phaser.GameObjects.Container[] = [];
  opponents: Phaser.GameObjects.Container[] = [];

  constructor() {
    super({key: BattleScene.KEY});
    this.gameService = window['gameService'];
    this.settingsService = window['settingsService'];
  }

  preload() {
    this.load.image('card', 'assets/card.png');
    this.load.image('spazienzio', 'assets/spazienzio.png');
    this.load.image('tossico', 'assets/tossico.png');
  }

  create() {
    for (let i = 0; i < this.gameService.opponents.length; i++) {
      this.opponents.push(new UI_Opponent(this, this.gameService.opponents[i], i, this.gameService.opponents.length).getContainer())
    }
    for (let i = 0; i < this.gameService.playerState.cardsInHand.length; i++) {
      this.cards.push(new UI_CardInHand(this, this.gameService.playerState.cardsInHand[i], i).getContainer());
    }
    this.input.on('drag', (pointer, gameObject, dragX, dragY) => {
      gameObject.x = dragX;
      gameObject.y = dragY;
    });
    this.input.on('drop', (pointer, gameObject, dropZone) => {
      console.log(pointer);
      console.log(gameObject);
      console.log(dropZone);

      this.newTurn();
    })
  }

  newTurn() {
    alert('newTurn method')
    this.scene.restart();
  }

}
