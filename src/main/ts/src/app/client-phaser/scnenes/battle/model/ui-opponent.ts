import {Opponent} from "../../../../model/player";
import {UI_Item} from "./ui-item";
import {UI_AbstractObject} from "./ui-abstract-object";
import {FOCUS_DETAILS, PLAYER_DETAILS} from "../animations/details";

export class UI_Opponent extends UI_AbstractObject {

  private readonly items: Phaser.GameObjects.Container[] = [];

  getItems(): Phaser.GameObjects.Container[] {
    return this.items;
  }

  private readonly model: Opponent;
  private readonly character: Phaser.GameObjects.Image;
  private readonly name: Phaser.GameObjects.Text;
  private readonly zone: Phaser.GameObjects.Zone;

  private readonly index: number;
  private readonly opponentsQty: number;

  constructor(scene: Phaser.Scene, model: Opponent, index: number, opponentsQty: number) {
    super();
    this.model = model;
    this.index = index;
    this.opponentsQty = opponentsQty;

    this.character = scene.add.image(0, 0, model.character.name)
      .setDisplaySize(this.getWidth(), this.getHeight());

    this.name = scene.add.text(
      -this.character.displayWidth * 0.45, -this.character.displayHeight * 0.55, [model.playerId])
      .setFontSize(this.settingsService.scaleForMin(36))
      .setFontFamily('Trebuchet MS')
      .setColor('#ffffff');

    this.container = scene.add.container(this.getX(), this.getY(), [this.character, this.name]);
    this.container.setName(this.getId());
    this.container.setSize(this.character.displayWidth, this.character.displayHeight);
    this.container.setInteractive();

    this.zone = scene.add.zone(this.container.x, this.container.y, this.container.displayWidth, this.container.displayHeight)
      .setRectangleDropZone(this.container.displayWidth, this.container.displayHeight)
      .setDepth(-1)
      .setData({target: model.playerId})
      .setName(this.getId() + '_dropZone');
    scene.input.enableDebug(this.container);

    for (let i = 0; i < model.character.items.length; i++) {
      const item = new UI_Item(scene, model.character.items[i], this.getContainer(), i);
      this.items.push(item.getContainer().setName(this.getId() + '_item' + i));
    }
    this.container.on('pointerup', () => FOCUS_DETAILS(scene, this, this.settingsService));
    this.container.on('pointerup', () => PLAYER_DETAILS(scene, this, this.settingsService));
  }

  getHeight(): number {
    return Math.min(this.settingsService.getScreenHeight() / 2, this.getWidth() * (4 / 3));
  }

  getWidth(): number {
    return (this.settingsService.getScreenWidth() / this.opponentsQty) * 0.85;
  }

  getX(): number {
    return this.settingsService.getScreenWidth() -
      (this.settingsService.scaleForMin(25) + ((this.settingsService.getScreenWidth() / this.opponentsQty) * this.index)
        + (this.character.displayWidth / 2));
  }

  getY(): number {
    return this.settingsService.scaleForMin(25) + (this.character.displayHeight / 2);
  }

  getId(): string {
    return this.model.playerId;
  }

  getAnimationTargets(): (Phaser.GameObjects.Container | Phaser.GameObjects.Zone)[] {
    const targets: any = [];
    this.items.forEach(i => targets.push(i));
    targets.push(this.container);
    targets.push(this.zone);
    return targets;
  }

}
