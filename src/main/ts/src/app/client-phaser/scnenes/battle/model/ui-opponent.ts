import {PhaserSettingsService} from "../../../phaser-settings.service";
import {GameService} from "../../../../service/game.service";
import {Player} from "../../../../model/player";

export class UI_Opponent {

  private readonly container: Phaser.GameObjects.Container;

  getContainer(): Phaser.GameObjects.Container {
    return this.container;
  }

  private settingsService: PhaserSettingsService;
  private gameService: GameService;

  constructor(scene: Phaser.Scene, model: Player, index: number, totalOpponents: number) {
    this.settingsService = window['settingsService'];
    this.gameService = window['gameService'];

    // TODO dynamically load this
    const character = scene.add.image(0, 0, model.character.name = 'Spazienzio de la Ucciso' ? 'spazienzio' : 'tossico')
      .setDisplaySize(this.getOppoW(), this.getOppoH());
    this.container = scene.add.container(
      this.getOppoX(character.displayWidth, index, totalOpponents), this.getOppoY(character.displayHeight),
      [character]);

    this.container.setSize(character.displayWidth, character.displayHeight);
    this.container.setInteractive()

    UI_Opponent.setDropZone(scene, this.container, model);

    scene.input.enableDebug(this.container)
  }

  private static setDropZone(scene: Phaser.Scene, container: Phaser.GameObjects.Container, model: Player) {
    scene.add.zone(container.x, container.y, container.displayWidth, container.displayHeight)
      .setRectangleDropZone(container.displayWidth, container.displayHeight)
      .setData(model.playerId);
  }

  private getOppoX(templateWidth: number, index: number, totalOpponents: number): number {
    return this.settingsService.scaleForWidth(75)
      + ((this.settingsService.getScreenWidth() / totalOpponents) * index) + (templateWidth / 2);
  }

  private getOppoY(templateHeight: number): number {
    return (this.settingsService.getScreenHeight() * 0.05) + (templateHeight / 2)
  }

  private getOppoW(): number {
    return this.settingsService.scaleForWidth(300)
  }

  private getOppoH(): number {
    return this.settingsService.scaleForHeight(300)
  }
}
