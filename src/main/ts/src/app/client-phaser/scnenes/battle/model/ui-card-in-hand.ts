import {Card} from "../../../../model/card";
import {PhaserSettingsService} from "../../../phaser-settings.service";
import {GameService} from "../../../../service/game.service";

export class UI_CardInHand {

  private readonly container: Phaser.GameObjects.Container;

  private settingsService: PhaserSettingsService;
  private gameService: GameService;

  constructor(scene: Phaser.Scene, model: Card, index: number) {
    this.settingsService = window['settingsService'];
    this.gameService = window['gameService'];

    const template = this.renderTemplate(scene);
    const text = this.renderName(scene, template, model);
    const image = this.renderImage(scene, template, model);

    this.container = scene.add.container(
      this.getCardX(template.displayWidth, index),
      this.getCardY(template.displayHeight),
      [template, text, image]);

    this.container.setSize(template.displayWidth, template.displayHeight);
    this.container.setDepth(index + 1);
    this.container.setData({card: model.name})
    this.container.setInteractive();
    scene.input.setDraggable(this.container)

    if (!this.gameService.isPlayable(model, this.gameService.playerState.character))
      template.setTint(0xd3d3d3);

    this.setHighlightEffect(template, scene);
    this.resetHighlightEffects(template, scene, index);
  }

  getContainer(): Phaser.GameObjects.Container {
    return this.container;
  }

  private setHighlightEffect(template: Phaser.GameObjects.Image, scene: Phaser.Scene) {
    this.container.on('pointerover', () => {

      this.container.setDepth(this.container.depth + 10);
      template.setTint(0x44ff44);

      scene.tweens.add({
        targets: this.container,
        ease: 'Sine.easeInOut',
        delay: 100,
        duration: 250,
        y: this.container.y - this.settingsService.scaleForHeight(20)
      });
      scene.tweens.add({
        targets: this.container,
        ease: 'Sine.easeInOut',
        delay: 250,
        duration: 500,
        scale: 1.05,
        yoyo: true,
        repeat: -1
      });
    });
  }

  private resetHighlightEffects(template: Phaser.GameObjects.Image, scene: Phaser.Scene, index: number) {
    this.container.on('pointerout', () => {
      template.clearTint();
      this.container.setDepth(this.container.depth - 10);

      scene.tweens.killAll();
      scene.tweens.add({
        targets: this.container,
        ease: 'Sine.easeInOut',
        delay: 100,
        duration: 250,
        x: this.getCardX(template.displayWidth, index),
        y: this.getCardY(template.displayHeight)
      });
      scene.tweens.add({
        targets: this.container,
        ease: 'Sine.easeInOut',
        delay: 100,
        duration: 250,
        scale: 1,
      });
    });
  }

  private renderName(scene: Phaser.Scene, template: Phaser.GameObjects.Image, model: Card) {
    return scene.add.text(this.getTextX(template.displayWidth), this.getTextY(template.displayHeight), [model.name])
      .setFontSize(this.settingsService.scaleForMin(36))
      .setFontFamily('Trebuchet MS')
      .setColor('#000000');
  }

  private renderTemplate(scene: Phaser.Scene) {
    return scene.add.image(0, 0, 'card')
      .setDisplaySize(this.getCardWidth(), this.getCardHeight());
  }

  private getCardWidth(): number {
    return this.settingsService.scaleForMin(500);
  }

  private getCardHeight(): number {
    return this.settingsService.scaleForMin(700);
  }

  private getCardX(templateWidth: number, index: number): number {
    return (this.settingsService.getScreenWidth() / 2.5) + (index * (this.settingsService.getScreenWidth() / 12)) + (templateWidth / 2);
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

  private renderImage(scene: Phaser.Scene, template: Phaser.GameObjects.Image, model: Card) {
    // TODO position this once template is somewhat definitive
    const card = model.image ? model.name : 'no-img';
    return scene.add.image(0, 0, card)
      .setDisplaySize(50, 50);
  }
}
