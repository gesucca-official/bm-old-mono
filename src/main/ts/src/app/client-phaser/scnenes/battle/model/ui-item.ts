import {PhaserSettingsService} from "../../../phaser-settings.service";
import {Card} from "../../../../model/card";

export class UI_Item {
  private readonly container: Phaser.GameObjects.Container;

  getContainer(): Phaser.GameObjects.Container {
    return this.container;
  }

  private settingsService: PhaserSettingsService;

  constructor(scene: Phaser.Scene, model: Card, player: Phaser.GameObjects.Container, index: number) {
    this.settingsService = window['settingsService'];

    const item = scene.add.image(0, 0, 'item').setDisplaySize(this.getItemSize(player.displayWidth), this.getItemSize(player.displayWidth));

    this.container = scene.add.container(
      this.getItemX(player.x, player.displayWidth, index), this.getItemY(player.y, player.displayHeight), [item]);
    this.container.setSize(item.displayWidth, item.displayHeight);
    this.container.setInteractive();

    scene.add.zone(
      this.container.x, this.container.y, this.container.displayWidth, this.container.displayHeight)
      .setRectangleDropZone(this.container.displayWidth, this.container.displayHeight)
      .setData({target: model.name});
    this.container.setInteractive();
    scene.input.enableDebug(this.container);
  }

  private getItemSize(playerW: number): number {
    return (playerW / 3) - this.settingsService.scaleForMin(5);
  }

  private getItemX(playerX: number, playerW: number, i: number) {
    return (-playerW / 2) + (i * playerW / 3) + this.settingsService.scaleForMin(35)
      + playerX;
  }

  private getItemY(playerY: number, playerH: number): number {
    return (playerH / 2) - this.settingsService.scaleForMin(35) + playerY;
  }

}
