import {Player} from "../../../../model/player";
import {UI_AbstractObject} from "./ui-abstract-object";
import {FOCUS_DETAILS, PLAYER_DETAILS} from "../animations/details";

export class UI_Player extends UI_AbstractObject {

  private readonly model: Player;
  private readonly character: Phaser.GameObjects.Image;
  private readonly zone: Phaser.GameObjects.Zone;

  constructor(scene: Phaser.Scene, model: Player) {
    super();
    this.model = model;

    this.character = scene.add.image(0, 0, model.character.name + '-back')
      .setDisplaySize(this.getWidth(), this.getHeight());

    this.container = scene.add.container(
      this.character.displayWidth / 2, this.getY(), [this.character])
    this.container.setName(this.getId());
    this.container.setSize(this.character.displayWidth, this.character.displayHeight);
    this.container.setInteractive();

    this.zone = scene.add.zone(this.container.x, this.container.y, this.container.displayWidth, this.container.displayHeight)
      .setRectangleDropZone(this.container.displayWidth, this.container.displayHeight)
      .setData({target: model.playerId})
      .setDepth(-1)
      .setName(this.getId() + '_dropZone');
    scene.input.enableDebug(this.container)

    this.container.on('pointerup', () => FOCUS_DETAILS(scene, this, this.settingsService));
    this.container.on('pointerup', () => PLAYER_DETAILS(scene, this, this.settingsService));
  }

  getAnimationTargets(): (Phaser.GameObjects.Container | Phaser.GameObjects.Zone)[] {
    const targets: any = [];
    targets.push(this.container);
    targets.push(this.zone);
    return targets;
  }

  getHeight(): number {
    return Math.min(this.settingsService.getScreenHeight() * 0.5, this.getWidth());
  }

  getWidth(): number {
    return Math.min(this.settingsService.getScreenWidth() * 0.35, this.settingsService.getScreenHeight() * 0.5);
  }

  getId(): string {
    return this.model.playerId;
  }

  getX(): number {
    return 0;
  }

  getY(): number {
    return Math.min(
      this.settingsService.getScreenHeight() - this.character.displayHeight / 2,
      (this.settingsService.getScreenHeight() * 0.75) + (this.character.displayHeight / 2)
    );
  }
}
