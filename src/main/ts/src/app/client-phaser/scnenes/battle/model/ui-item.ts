import {Card} from "../../../../model/card";
import {UI_AbstractObject} from "./ui-abstract-object";
import {ITEM_DETAILS} from "../animations/details";

export class UI_Item extends UI_AbstractObject {

  private readonly playerSprite: Phaser.GameObjects.Container;
  private readonly model: Card;
  private readonly index: number;

  private readonly zone: Phaser.GameObjects.Zone;

  constructor(scene: Phaser.Scene, model: Card, playerSprite: Phaser.GameObjects.Container, index: number) {
    super();
    this.playerSprite = playerSprite;
    this.model = model;
    this.index = index;

    const item = scene.add.image(0, 0, model.name + '-sprite')
      .setDisplaySize(this.getItemSize(), this.getItemSize());

    this.container = scene.add.container(this.getX(), this.getY(), [item]);
    this.container.setSize(item.displayWidth, item.displayHeight);
    this.container.setName(model.name);
    this.container.setInteractive();

    this.zone = scene.add.zone(
      this.container.x, this.container.y, this.container.displayWidth, this.container.displayHeight)
      .setRectangleDropZone(this.container.displayWidth, this.container.displayHeight)
      .setData({target: model.name})
      .setName(this.getId() + '_dropZone');
    this.container.setInteractive();
    scene.input.enableDebug(this.container);

    //this.setDetailsAnimation(scene, zone, playerSprite);
    this.container.on('pointerup', () => ITEM_DETAILS(scene, this));
  }

  getAnimationTargets(): (Phaser.GameObjects.Container | Phaser.GameObjects.Zone)[] {
    return [this.container, this.zone];
  }

  getId(): string {
    return this.model.name;
  }

  getHeight(): number {
    return this.getItemSize();
  }

  getWidth(): number {
    return this.getItemSize();
  }

  getX(): number {
    return (-this.playerSprite.displayWidth / 2) + (this.index * this.playerSprite.displayWidth / 3)
      + this.settingsService.scaleForMin(35)
      + this.playerSprite.x;
  }

  getY(): number {
    return (this.playerSprite.displayHeight / 2) - this.settingsService.scaleForMin(35) + this.playerSprite.y;
  }

  getPlayerSprite(): Phaser.GameObjects.Container {
    return this.playerSprite;
  }

  private getItemSize(): number {
    return (this.playerSprite.displayWidth / 3) - this.settingsService.scaleForMin(5);
  }
}
