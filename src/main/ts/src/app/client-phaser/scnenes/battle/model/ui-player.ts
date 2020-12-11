import {PhaserSettingsService} from "../../../phaser-settings.service";
import {GameService} from "../../../../service/game.service";
import {Player} from "../../../../model/player";

export class UI_Player {
  private readonly container: Phaser.GameObjects.Container;

  getContainer(): Phaser.GameObjects.Container {
    return this.container;
  }

  private settingsService: PhaserSettingsService;
  private gameService: GameService;

  constructor(scene: Phaser.Scene, model: Player) {
    this.settingsService = window['settingsService'];
    this.gameService = window['gameService'];

    const character = scene.add.image(0, 0, model.character.name + '-back')
      .setDisplaySize(this.getPlayerWidth(), this.getPlayerHeight());

    this.container = scene.add.container(
      character.displayWidth / 2, this.getPlayerY(character.displayHeight), [character]);

    this.container.setSize(character.displayWidth, character.displayHeight);
    this.container.setInteractive();

    UI_Player.setDropZone(scene, this.container, model);
    scene.input.enableDebug(this.container)
  }

  private static setDropZone(scene: Phaser.Scene, container: Phaser.GameObjects.Container, model: Player) {
    scene.add.zone(container.x, container.y, container.displayWidth, container.displayHeight)
      .setRectangleDropZone(container.displayWidth, container.displayHeight)
      .setData({target: model.playerId});
  }

  private getPlayerY(templateHeight: number): number {
    return Math.min(
      this.settingsService.getScreenHeight() - templateHeight / 2,
      (this.settingsService.getScreenHeight() * 0.75) + (templateHeight / 2)
    );
  }

  private getPlayerWidth(): number {
    return Math.min(this.settingsService.getScreenWidth() * 0.35, this.settingsService.getScreenHeight() * 0.5);
  }

  private getPlayerHeight(): number {
    return Math.min(this.settingsService.getScreenHeight() * 0.5, this.getPlayerWidth());
  }
}
