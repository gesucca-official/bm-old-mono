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
  opponents: Map<string, Phaser.GameObjects.Container> = new Map<string, Phaser.GameObjects.Container>();
  items: Phaser.GameObjects.Container[] = [];

  constructor() {
    super({key: BattleScene.KEY});
    this.gameService = window['gameService'];
    this.settingsService = window['settingsService'];
  }

  preload() {
    // card template
    this.load.image('card', 'assets/img/card-template.png');
    // card image fallback
    this.load.image('no-img', 'assets/img/cards/no-img.png');
    // player back
    this.load.image(this.gameService.playerState.character.name + '-back',
      'assets/img/characters/' + this.gameService.playerState.character.sprite.replace('.png', '') + '-back.png');
    // opponents front
    this.gameService.opponents.forEach(o => {
      this.load.image(o.character.name, 'assets/img/characters/' + o.character.sprite);
      o.character.items.forEach(i => this.load.image(i.name + '-sprite', 'assets/img/cards/' + i.sprite))
    });
    // cards images
    this.gameService.playerState.cardsInHand.forEach(c => {
      if (c.image)
        this.load.image(c.name, 'assets/img/cards/' + c.image);
    });
  }

  create() {
    for (let i = 0; i < this.gameService.opponents.length; i++) {
      const oppo = new UI_Opponent(this, this.gameService.opponents[i], i, this.gameService.opponents.length);
      this.opponents.set(this.gameService.opponents[i].playerId, oppo.getContainer());
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

    this.playResolvedMovesAnimation();
    this.playTimeBasedAnimation();
  }

  private playResolvedMovesAnimation(): void {
    if (!this.gameService.gameState.resolvedMoves)
      return;
    // TODO
  }

  private playTimeBasedAnimation() {
    if (!this.gameService.gameState.timeBasedEffects)
      return;
  }

  private getPlayerContainer(name: string): Phaser.GameObjects.Container {
    if (name === this.gameService.playerState.playerId)
      return this.player
    else return this.opponents.get(name);
  }
}
