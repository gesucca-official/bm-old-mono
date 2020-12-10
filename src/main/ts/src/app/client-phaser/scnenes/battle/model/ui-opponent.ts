import {PhaserSettingsService} from "../../../phaser-settings.service";
import {GameService} from "../../../../service/game.service";
import {Opponent} from "../../../../model/player";
import {UI_Item} from "./ui-item";

export class UI_Opponent {

  private readonly container: Phaser.GameObjects.Container;
  private readonly items: Phaser.GameObjects.Container[] = [];

  private detailsShown = false;
  private origPosition: Map<string, [number, number]> = new Map<string, [number, number]>();
  private focusBackground: Phaser.GameObjects.Rectangle;

  getOpponent(): Phaser.GameObjects.Container {
    return this.container;
  }

  getItems(): Phaser.GameObjects.Container[] {
    return this.items;
  }

  private settingsService: PhaserSettingsService;
  private gameService: GameService;

  constructor(scene: Phaser.Scene, model: Opponent, index: number, totalOpponents: number) {
    this.settingsService = window['settingsService'];
    this.gameService = window['gameService'];

    const character = scene.add.image(0, 0, model.character.name)
      .setDisplaySize(this.getOppoWidth(totalOpponents), this.getOppoHeight(totalOpponents));

    const name = scene.add.text(
      -character.displayWidth * 0.45, -character.displayHeight * 0.55, [model.playerId])
      .setFontSize(this.settingsService.scaleForMin(36))
      .setFontFamily('Trebuchet MS')
      .setColor('#ffffff');

    this.container = scene.add.container(
      this.getOppoX(character.displayWidth, index, totalOpponents),
      this.getOppoY(character.displayHeight), [character, name]);

    this.container.setSize(character.displayWidth, character.displayHeight);
    this.container.setInteractive();

    const zone = UI_Opponent.setDropZone(scene, this.container, model);
    scene.input.enableDebug(this.container)

    for (let i = 0; i < model.character.items.length; i++) {
      const item = new UI_Item(scene, model.character.items[i], this.getOpponent(), i);
      this.items.push(item.getContainer());
    }

    this.setDetailsAnimation(scene, zone);
  }

  private setDetailsAnimation(scene: Phaser.Scene, zone: Phaser.GameObjects.Zone) {
    const detail = (target, id) => {
      return () => {
        if (!this.detailsShown) {
          target.setDepth(target.depth + 5);
          this.origPosition.set(id, [target.x, target.y]);
          scene.tweens.add({
            targets: target,
            ease: 'Sine.easeInOut',
            delay: 100,
            duration: 250,
            x: (target.x - this.origPosition.get('pg')[0]) * 1.5 + this.container.displayWidth
              + this.settingsService.scaleForMin(100),
            y: target.y + (this.settingsService.getScreenHeight() / 2 - target.displayHeight / 2)
              - this.settingsService.scaleForMin(100),
            scale: 1.5
          });
          // animations should starts for all the components
          setTimeout(() => this.detailsShown = true, 10);
        } else {
          scene.tweens.killAll();
          target.setDepth(target.depth - 5);
          scene.tweens.add({
            targets: target,
            ease: 'Sine.easeInOut',
            delay: 100,
            duration: 250,
            x: this.origPosition.get(id)[0],
            y: this.origPosition.get(id)[1],
            scale: 1
          });
          setTimeout(() => this.detailsShown = false, 10);
        }
      }
    };

    const focus = () => {
      if (!this.detailsShown) {
        this.focusBackground = scene.add.rectangle(0, 0,
          this.settingsService.getScreenWidth(),
          this.settingsService.getScreenHeight(), 0x9e9e9e, 0.75)
          .setOrigin(0, 0)
          .setInteractive(); // this prevents things underneath it to be clicked
      } else {
        this.focusBackground.destroy();
      }
    }

    this.container.on('pointerup', focus);
    this.container.on('pointerup', detail(this.container, 'pg'));
    this.container.on('pointerup', detail(zone, 'zone'));
    for (let i = 0; i < this.items.length; i++) {
      this.container.on('pointerup', detail(this.items[i], 'item' + i));
    }
  }

  private static setDropZone(scene: Phaser.Scene, container: Phaser.GameObjects.Container, model: Opponent) {
    return scene.add.zone(container.x, container.y, container.displayWidth, container.displayHeight)
      .setRectangleDropZone(container.displayWidth, container.displayHeight)
      .setDepth(-1)
      .setData({target: model.playerId});
  }

  private getOppoX(templateWidth: number, index: number, totalOpponents: number): number {
    return this.settingsService.getScreenWidth() -
      (this.settingsService.scaleForMin(25) + ((this.settingsService.getScreenWidth() / totalOpponents) * index)
        + (templateWidth / 2));
  }

  private getOppoY(templateHeight: number): number {
    return this.settingsService.scaleForMin(25) + (templateHeight / 2);
  }

  private getOppoWidth(totalOpponents: number): number {
    return (this.settingsService.getScreenWidth() / totalOpponents) * 0.85;
  }

  private getOppoHeight(totalOpponents: number): number {
    return Math.min(this.settingsService.getScreenHeight() / 2, this.getOppoWidth(totalOpponents) * (4 / 3));
  }

}
