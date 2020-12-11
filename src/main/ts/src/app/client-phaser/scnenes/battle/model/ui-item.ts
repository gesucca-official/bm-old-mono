import {PhaserSettingsService} from "../../../phaser-settings.service";
import {Card} from "../../../../model/card";

export class UI_Item {
  private readonly container: Phaser.GameObjects.Container;

  getContainer(): Phaser.GameObjects.Container {
    return this.container;
  }

  private settingsService: PhaserSettingsService;

  private detailsShown = false;
  private origX: number;
  private origY: number;
  private origScale: number;

  constructor(scene: Phaser.Scene, model: Card, player: Phaser.GameObjects.Container, index: number) {
    this.settingsService = window['settingsService'];

    const item = scene.add.image(0, 0, model.name + '-sprite')
      .setDisplaySize(this.getItemSize(player.displayWidth), this.getItemSize(player.displayWidth));

    this.container = scene.add.container(
      this.getItemX(player.x, player.displayWidth, index), this.getItemY(player.y, player.displayHeight), [item]);
    this.container.setSize(item.displayWidth, item.displayHeight);
    this.container.setInteractive();

    const zone = scene.add.zone(
      this.container.x, this.container.y, this.container.displayWidth, this.container.displayHeight)
      .setRectangleDropZone(this.container.displayWidth, this.container.displayHeight)
      .setData({target: model.name});
    this.container.setInteractive();
    scene.input.enableDebug(this.container);

    this.setDetailsAnimation(scene, zone, player);
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

  private setDetailsAnimation(scene: Phaser.Scene, zone: Phaser.GameObjects.Zone, player: Phaser.GameObjects.Container) {
    this.container.on('pointerup', () => {
      if (!this.detailsShown) {
        this.origX = this.container.x;
        this.origY = this.container.y;
        this.origScale = this.container.scale;
        zone.setDepth(zone.depth + 5);
        scene.tweens.add({
          targets: [this.container, zone],
          ease: 'Sine.easeInOut',
          delay: 100,
          duration: 250,
          x: player.x,
          y: player.y,
          scale: 3
        });
      } else {
        zone.setDepth(zone.depth - 5);
        scene.tweens.add({
          targets: [this.container, zone],
          ease: 'Sine.easeInOut',
          delay: 100,
          duration: 250,
          x: this.origX,
          y: this.origY,
          scale: this.origScale
        });
      }
      this.detailsShown = !this.detailsShown;
      player.setAlpha(this.detailsShown ? 0.5 : 1);
    });

  }
}
