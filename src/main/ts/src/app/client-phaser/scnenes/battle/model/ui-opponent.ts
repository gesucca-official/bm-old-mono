import {PhaserSettingsService} from "../../../phaser-settings.service";
import {GameService} from "../../../../service/game.service";
import {Opponent} from "../../../../model/player";
import {UI_Item} from "./ui-item";

export class UI_Opponent {

  private readonly container: Phaser.GameObjects.Container;
  private readonly items: Phaser.GameObjects.Container[] = [];

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
      this.getOppoX(character.displayWidth, index, totalOpponents), this.getOppoY(character.displayHeight), [character, name]);

    this.container.setSize(character.displayWidth, character.displayHeight);

    this.container.setInteractive();
    UI_Opponent.setDropZone(scene, this.container, model);
    scene.input.enableDebug(this.container)

    for (let i = 0; i < model.character.items.length; i++) {
      const item = new UI_Item(scene, model.character.items[i], this.getOpponent(), i);
      this.items.push(item.getContainer());
    }
  }

  private static setDropZone(scene: Phaser.Scene, container: Phaser.GameObjects.Container, model: Opponent) {
    scene.add.zone(container.x, container.y, container.displayWidth, container.displayHeight)
      .setRectangleDropZone(container.displayWidth, container.displayHeight)
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
