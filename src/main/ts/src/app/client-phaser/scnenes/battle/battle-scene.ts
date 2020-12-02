import {GameService} from "../../../service/game.service";
import {PhaserSettingsService} from "../../phaser-settings.service";

export class BattleScene extends Phaser.Scene {

  public static KEY = 'battleScene';
  private gameService: GameService;

  cardsName: Phaser.GameObjects.Text[] = [];

  constructor() {
    super({key: BattleScene.KEY});
    this.gameService = window['gameService'];
  }

  create() {
    this.gameService.playerState.cardsInHand.forEach(c =>
      this.cardsName.push(this.add.text(
        PhaserSettingsService.instance.getScreenWidth() / 2,
        PhaserSettingsService.instance.getScreenHeight() / 2,
        [c.name])
        .setOrigin(0.5, 0.5)
        .setFontSize(PhaserSettingsService.instance.scaleForMin(72) * window.devicePixelRatio)
        .setFontFamily('Trebuchet MS')
        .setColor('#00ffff'))
    );

  }

}
