import {GLOBALS} from "../globals";

export class TitleScene extends Phaser.Scene {

  title: Phaser.GameObjects.Text;

  constructor() {
    super({key: "title"});
  }

  preload() {
  }

  create() {
    this.title = this.add.text(GLOBALS.WIDTH / 2, GLOBALS.HEIGHT / 2, ['BOTTE MICIDIALI'])
      .setOrigin(0.5, 0.5)
      .setFontSize(72 * window.devicePixelRatio)
      .setFontFamily('Trebuchet MS')
      .setColor('#00ffff');
  }
}
