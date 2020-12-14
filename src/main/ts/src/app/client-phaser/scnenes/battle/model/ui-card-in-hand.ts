import {Card} from "../../../../model/card";
import {PhaserSettingsService} from "../../../phaser-settings.service";
import {GameService} from "../../../../service/game.service";
import {CARD_HIGHLIGHT, resetCardHighlight} from "../animations/card-highlight";

export class UI_CardInHand {

  private readonly container: Phaser.GameObjects.Container;

  getContainer(): Phaser.GameObjects.Container {
    return this.container;
  }

  private readonly index: number;

  getIndex(): number {
    return this.index;
  }

  private readonly settingsService: PhaserSettingsService;
  private readonly gameService: GameService;

  constructor(scene: Phaser.Scene, model: Card, index: number) {
    this.settingsService = window['settingsService'];
    this.gameService = window['gameService'];

    this.index = index;

    const template = this.renderTemplate(scene)
      .setName('template');
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

    // set animations
    CARD_HIGHLIGHT(scene, this, this.settingsService);
    resetCardHighlight(scene, this);
  }

  getCardX(templateWidth: number, index: number): number {
    return (this.settingsService.getScreenWidth() / 2.5) + (index * (this.settingsService.getScreenWidth() / 12)) + (templateWidth / 2);
  }

  getCardY(templateHeight: number): number {
    return (this.settingsService.getScreenHeight() * 0.75) + (templateHeight / 2)
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

  private getTextX(templateWidth: number): number {
    return -(templateWidth / 2) + this.settingsService.scaleForMin(40)
  }

  private getTextY(templateHeight: number): number {
    return -(templateHeight / 2) + this.settingsService.scaleForMin(35)
  }

  private renderImage(scene: Phaser.Scene, template: Phaser.GameObjects.Image, model: Card) {
    const card = model.image ? model.name : 'no-img';
    return scene.add.image(
      this.getTextX(template.displayWidth),
      this.getTextY(template.displayHeight) + this.settingsService.scaleForMin(50),
      card).setOrigin(0, 0)
      .setDisplaySize(this.settingsService.scaleForMin(420), this.settingsService.scaleForMin(300));
  }
}
