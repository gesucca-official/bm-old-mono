import {GameService} from "../../../service/game.service";
import {PhaserSettingsService} from "../../phaser-settings.service";
import {UI_CardInHand} from "./model/ui-card-in-hand";
import {UI_Opponent} from "./model/ui-opponent";
import {ResolvedMoveAnimation} from "./animations/resolves-moves";
import {UI_Player} from "./model/ui-player";

export class BattleScene extends Phaser.Scene {

  public static KEY = 'battleScene';
  private readonly gameService: GameService;
  private readonly settingsService: PhaserSettingsService;

  player: Phaser.GameObjects.Container;
  opponents: Map<string, Phaser.GameObjects.Container> = new Map<string, Phaser.GameObjects.Container>();
  cards: Phaser.GameObjects.Container[] = [];

  constructor() {
    super({key: BattleScene.KEY});
    this.gameService = window['gameService'];
    this.settingsService = window['settingsService'];
  }

  preload() {
    // symbols
    this.load.image('health', 'assets/img/health.png');
    this.load.image('alertness', 'assets/img/alertness.png');
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
    this.settingsService.currentScene = this;

    for (let i = 0; i < this.gameService.opponents.length; i++) {
      const oppo = new UI_Opponent(this, this.gameService.opponents[i], i, this.gameService.opponents.length, 36);
      this.opponents.set(this.gameService.opponents[i].playerId, oppo.getContainer());
    }
    for (let i = 0; i < this.gameService.playerState.cardsInHand.length; i++) {
      this.cards.push(new UI_CardInHand(this, this.gameService.playerState.cardsInHand[i], i).getContainer());
    }
    this.player = new UI_Player(this, this.gameService.playerState, 55, '-back').getContainer();

    this.input.on('drag', (pointer, gameObject, dragX, dragY) => {
      gameObject.x = dragX;
      gameObject.y = dragY;
    });
    this.input.on('drop', (pointer, gameObject, dropZone) =>
      this.handleDropEvent(gameObject.data.list.card, dropZone.data.list.target)
    );

    if (this.gameService.gameState.resolvedMoves && this.gameService.gameState.resolvedMoves.length > 0)
      this.setupMoveAnimation(0);
    this.playTimeBasedAnimation();
  }

  getPlayerContainer(name: string): Phaser.GameObjects.Container {
    if (name === this.gameService.playerState.playerId)
      return this.player
    else return this.opponents.get(name);
  }

  private playTimeBasedAnimation() {
    if (!this.gameService.gameState.timeBasedEffects)
      return;
  }

  private setupMoveAnimation(index: number): void {
    const animation = new ResolvedMoveAnimation(
      this,
      this.settingsService,
      this.gameService.gameState.resolvedMoves[index],
      (name) => this.getPlayerContainer(name));
    animation.play();
    animation.getOkButton().on('pointerdown', () => {
      animation.resetAndDestroy();
      if (index + 1 < this.gameService.gameState.resolvedMoves.length)
        this.setupMoveAnimation(index + 1);
    });
  }

  private handleDropEvent(dropped: string, target: string): void {
    if (!confirm(target))
      return;
    const card = this.gameService.getCardObjFromName(dropped);
    if (card.characterBound) {
      // todo manage choices
    } else {
      this.gameService.submitMove({
        playedCardName: dropped,
        playerId: this.gameService.playerState.playerId,
        targetId: target,
        gameId: this.gameService.gameId,
        choices: null
      });
    }
  }

}
