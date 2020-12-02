import {PhaserSettingsService} from "../phaser-settings.service";

export class TitleScene extends Phaser.Scene {

  public static KEY = 'titleScene';

  title: Phaser.GameObjects.Text;
  browseText: Phaser.GameObjects.Text;

  constructor() {
    super({key: TitleScene.KEY});
  }

  preload() {
  }

  create() {
    this.title = this.add.text(
      PhaserSettingsService.instance.getScreenWidth() / 2,
      PhaserSettingsService.instance.getScreenHeight() / 2,
      ['BOTTE MICIDIALI'])
      .setOrigin(0.5, 0.5)
      .setFontSize(PhaserSettingsService.instance.scaleForMin(72) * window.devicePixelRatio)
      .setFontFamily('Trebuchet MS')
      .setColor('#00ffff');

    this.browseText = this.add.text(
      PhaserSettingsService.instance.getScreenWidth() * (12 / 16),
      PhaserSettingsService.instance.getScreenHeight() * (14 / 16),
      ['BROWSE CARDS'])
      .setFontSize(PhaserSettingsService.instance.scaleForMin(24) * window.devicePixelRatio)
      .setFontFamily('Trebuchet MS')
      .setColor('#00ffff')
      .on('pointerdown', () => alert('clicked'))
      .on('pointerover', () => this.browseText.setColor('#ff69b4'))
      .on('pointerout', () => this.browseText.setColor('#00ffff'))
      .setInteractive();
  }

}
