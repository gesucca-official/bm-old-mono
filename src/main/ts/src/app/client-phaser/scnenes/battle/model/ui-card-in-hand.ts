import {Card} from "../../../../model/card";
import {PhaserSettingsService} from "../../../phaser-settings.service";
import {GameService} from "../../../../service/game.service";

export class UI_CardInHand {

  private readonly container: Phaser.GameObjects.Container;
  private containerAnimation: Phaser.Tweens.Tween;

  private settingsService: PhaserSettingsService;
  private gameService: GameService;

  constructor(scene: Phaser.Scene, model: Card, index: number) {
    this.settingsService = window['settingsService'];
    this.gameService = window['gameService'];

    const template =
      scene.add.image(0, 0, 'card')
        .setScale(this.settingsService.scaleForMin(1));

    const text =
      scene.add.text(this.getTextX(template.displayWidth), this.getTextY(template.displayHeight), [model.name])
        .setFontSize(this.settingsService.scaleForMin(36))
        .setFontFamily('Trebuchet MS')
        .setColor('#000000');

    this.container = scene.add.container(
      this.getCardX(template.displayWidth, index),
      this.getCardY(template.displayHeight),
      [template, text]);
    this.container.setSize(template.displayWidth, template.displayHeight);
    this.container.setDepth(index);
    this.container.setInteractive();
    scene.input.setDraggable(this.container)

    if (!this.gameService.isPlayable(model, this.gameService.playerState.character))
      template.setTint(0xd3d3d3);

    this.container.on('pointerover', () => {
      template.setTint(0x44ff44);
      this.container.setDepth(this.container.depth + 10);

      this.containerAnimation = scene.tweens.add({
        targets: this.container,
        ease: 'Sine.easeInOut',
        delay: 250,
        duration: 500,
        scale: 1.25,
        yoyo: true,
        repeat: -1
      });
    });

    this.container.on('pointerout', () => {
      template.clearTint();
      this.container.setDepth(this.container.depth - 10);
      this.containerAnimation.stop();
      this.containerAnimation = scene.tweens.add({
        targets: this.container,
        ease: 'Sine.easeInOut',
        delay: 250,
        duration: 500,
        scale: 1,
      });
    });

  }

  getContainer(): Phaser.GameObjects.Container {
    return this.container;
  }

  private getCardX(templateWidth: number, index: number): number {
    return (this.settingsService.getScreenWidth() / 2.5) + (index * this.settingsService.scaleForMin(130)) + (templateWidth / 2);
  }

  private getCardY(templateHeight: number): number {
    return (this.settingsService.getScreenHeight() * 0.75) + (templateHeight / 2)
  }

  private getTextX(templateWidth: number): number {
    return -(templateWidth / 2) + this.settingsService.scaleForMin(40)
  }

  private getTextY(templateHeight: number): number {
    return -(templateHeight / 2) + this.settingsService.scaleForMin(35)
  }

}
