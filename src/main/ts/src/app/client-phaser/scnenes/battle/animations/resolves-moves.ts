import {PhaserSettingsService} from "../../../phaser-settings.service";
import {Move} from "../../../../model/move";

export class ResolvedMoveAnimation {

  private readonly scene: Phaser.Scene;
  private readonly settings: PhaserSettingsService;
  private readonly move: Move;
  private readonly getPlayer: (playerId: string) => Phaser.GameObjects.Container;

  private readonly button: Phaser.GameObjects.Container;
  private background: Phaser.GameObjects.Rectangle;

  constructor(scene: Phaser.Scene,
              settings: PhaserSettingsService,
              move: Move,
              getPlayer: (playerId: string) => Phaser.GameObjects.Container) {
    this.scene = scene;
    this.settings = settings;
    this.move = move;
    this.getPlayer = getPlayer;

    const rectangle = this.scene.add.rectangle(0, 0,
      this.settings.scaleForMin(200),
      this.settings.scaleForMin(100),
      0xa2ff33, 1);
    const text = this.scene.add.text(0, 0, ['OK'])
      .setFontSize(this.settings.scaleForMin(36))
      .setFontFamily('Trebuchet MS')
      .setColor('#000000');
    this.button = this.scene.add.container(
      this.settings.getScreenWidth() * (7 / 8),
      this.settings.getScreenHeight() * (3 / 4),
      [rectangle, text])
      .setSize(rectangle.displayWidth, rectangle.displayHeight)
      .setDepth(100)
      .setInteractive();
  }

  getOkButton(): Phaser.GameObjects.Container {
    return this.button;
  }

  play(): void {
    this.background = this.scene.add.rectangle(0, 0,
      this.settings.getScreenWidth(),
      this.settings.getScreenHeight(), 0x9e9e9e, 0.75)
      .setOrigin(0, 0)
      .setDepth(99)
      .setInteractive(); // this prevents things underneath it to be clicked

    this.getPlayer(this.move.playerId).setDepth(this.getPlayer(this.move.playerId).depth + 100);
  }

  resetAndDestroy(): void {
    this.background.destroy();
    this.button.destroy();

    this.getPlayer(this.move.playerId).setDepth(this.getPlayer(this.move.playerId).depth - 100);
  }

}
