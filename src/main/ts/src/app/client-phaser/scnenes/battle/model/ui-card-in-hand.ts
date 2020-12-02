import {Card} from "../../../../model/card";

export class UI_CardInHand {

  private readonly container: Phaser.GameObjects.Container;
  private settingsService: any;

  constructor(scene: Phaser.Scene, model: Card, index: number) {
    this.settingsService = window['settingsService'];

    const template =
      scene.add.image(0, 0, 'card')
        .setScale(this.settingsService.scaleForMin(1));

    const text =
      scene.add.text(this.settingsService.scaleForWidth(40), this.settingsService.scaleForHeight(35), [model.name])
        .setOrigin(0, 0)
        .setFontSize(this.settingsService.scaleForMin(36))
        .setFontFamily('Trebuchet MS')
        .setColor('#000000');

    this.container = scene.add.container(this.getCardX(index), this.getCardY(), [template, text]);
    this.container.setSize(template.displayWidth, template.displayHeight);
    this.container.setInteractive();
    scene.input.setDraggable(this.container)

    // scene.input.enableDebug(this.container)

    this.container.on('pointerover', function () {
      template.setTint(0x44ff44);
    });

    this.container.on('pointerout', function () {
      template.clearTint();
    });

  }

  getContainer(): Phaser.GameObjects.Container {
    return this.container;
  }

  private getCardX(index: number): number {
    return (this.settingsService.getScreenWidth() / 2.5) + (index * this.settingsService.scaleForMin(130))
  }

  private getCardY(): number {
    return this.settingsService.getScreenHeight() * 0.75
  }

}
