import {Card} from "../../../../model/card";
import {HIGHLIGHT, RESET_HIGHLIGHT} from "../animations/highlight";
import {UI_AbstractObject} from "./ui-abstract-object";

export class UI_CardInHand extends UI_AbstractObject {

  private readonly model: Card;
  private readonly index: number;

  private readonly template: Phaser.GameObjects.Image;

  constructor(scene: Phaser.Scene, model: Card, index: number) {
    super();
    this.model = model;
    this.index = index;

    this.template = this.renderTemplate(scene)
      .setName('template');
    const text = this.renderName(scene, this.template, model);
    const image = this.renderImage(scene, this.template, model);

    this.container = scene.add.container(
      this.getX(), this.getY(),
      [this.template, text, image]);

    this.container.setSize(this.template.displayWidth, this.template.displayHeight);
    this.container.setDepth(index + 3);
    this.container.setData({card: model.name})
    this.container.setInteractive();
    scene.input.setDraggable(this.container)

    if (!this.gameService.isPlayable(model, this.gameService.playerState.character))
      this.template.setTint(0xd3d3d3);

    // set animations
    HIGHLIGHT(scene, this.settingsService, this, 'pointerover', true);
    RESET_HIGHLIGHT(scene, this);
  }

  getIndex(): number {
    return this.index;
  }

  getId(): string {
    return this.model.name;
  }

  getAnimationTargets(): (Phaser.GameObjects.Container | Phaser.GameObjects.Zone)[] {
    return [this.container];
  }

  getHeight(): number {
    return this.settingsService.scaleForMin(700);
  }

  getWidth(): number {
    return this.settingsService.scaleForMin(500);
  }

  getX(): number {
    return (this.settingsService.getScreenWidth() / 2.5)
      + (this.index * (this.settingsService.getScreenWidth() / 12))
      + (this.template.displayWidth / 2);
  }

  getY(): number {
    return (this.settingsService.getScreenHeight() * 0.75) + (this.template.displayHeight / 2)
  }

  getTintTarget(): Phaser.GameObjects.Image {
    return this.template;
  }

  private renderName(scene: Phaser.Scene, template: Phaser.GameObjects.Image, model: Card) {
    return scene.add.text(this.getTextX(template.displayWidth), this.getTextY(template.displayHeight), [model.name])
      .setFontSize(this.settingsService.scaleForMin(36))
      .setFontFamily('Electrolize')
      .setColor('#000000');
  }

  private renderTemplate(scene: Phaser.Scene) {
    return scene.add.image(0, 0, 'card')
      .setDisplaySize(this.getWidth(), this.getHeight());
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
