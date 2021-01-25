import {UI_AbstractObject} from "../model/ui-abstract-object";
import {PhaserSettingsService} from "../../../phaser-settings.service";
import {UI_Item} from "../model/ui-item";

export class DetailsAnimation {

  private static _INSTANCE: DetailsAnimation;

  public static getInstance(): DetailsAnimation {
    if (!this._INSTANCE)
      this._INSTANCE = new DetailsAnimation();
    return this._INSTANCE;
  }

  private readonly detailsShownFor: Map<string, boolean> = new Map<string, boolean>();
  private readonly originPosOf: Map<string, [number, number]> = new Map<string, [number, number]>();
  private readonly originScaleOf: Map<string, number> = new Map<string, number>();

  private blurredBackground: Phaser.GameObjects.Rectangle;

  private constructor() {
  }

  public focusDetails(scene: Phaser.Scene, player: UI_AbstractObject, settings: PhaserSettingsService) {
    if (!this.detailsShownFor.get(player.getId())) {
      this.blurredBackground = scene.add.rectangle(0, 0,
        settings.getScreenWidth(),
        settings.getScreenHeight(), 0x9e9e9e, 0.75)
        .setOrigin(0, 0)
        .setDepth(1)
        .setInteractive(); // this prevents things underneath it to be clicked
    } else {
      this.blurredBackground.destroy();
    }
  }

  public showPlayerDetails(scene: Phaser.Scene, obj: UI_AbstractObject, settings: PhaserSettingsService) {
    if (!this.detailsShownFor.get(obj.getId())) {
      obj.getAnimationTargets().forEach(target => {
        target.setDepth(target.depth + 5);
        this.originPosOf.set(target.name, [target.x, target.y]);
        scene.tweens.add({
          targets: target,
          ease: 'Sine.easeInOut',
          delay: 100,
          duration: 250,
          x: (target.x - obj.getX() + obj.getContainer().displayWidth / 2) * 1.5 + settings.scaleForWidth(35),
          y: (target.y - obj.getY()) * 1.5 + (settings.getScreenHeight() / 2),
          scale: 1.5
        });
        this.detailsShownFor.set(obj.getId(), true);
      })
    } else {
      obj.getAnimationTargets().forEach(target => {
        scene.tweens.killAll();
        target.setDepth(target.depth - 5);
        scene.tweens.add({
          targets: target,
          ease: 'Sine.easeInOut',
          delay: 100,
          duration: 250,
          x: this.originPosOf.get(target.name) [0],
          y: this.originPosOf.get(target.name) [1],
          scale: 1
        });
      });
      this.detailsShownFor.set(obj.getId(), false);
    }
  }

  public showItemDetails(scene: Phaser.Scene, item: UI_Item): void {
    if (!this.detailsShownFor.get(item.getId() + '_itemDetails')) {
      item.getAnimationTargets().forEach(target => {
        target.setDepth(target.depth + 1);
        // I have to change the name to avoid overriding the original position set in players details
        this.originPosOf.set(target.name + '_itemDetails', [target.x, target.y]);
        this.originScaleOf.set(target.name + '_itemDetails', target.scale);
        scene.tweens.add({
          targets: target,
          ease: 'Sine.easeInOut',
          delay: 100,
          duration: 250,
          x: item.getPlayerSprite().x,
          y: item.getPlayerSprite().y,
          scale: 3
        });
      });
      this.detailsShownFor.set(item.getId() + '_itemDetails', true);
      item.getPlayerSprite().setAlpha(0.5);
    } else {
      item.getAnimationTargets().forEach(target => {
        scene.tweens.killAll();
        target.setDepth(target.depth - 1);
        scene.tweens.add({
          targets: target,
          ease: 'Sine.easeInOut',
          delay: 100,
          duration: 250,
          x: this.originPosOf.get(target.name + '_itemDetails') [0],
          y: this.originPosOf.get(target.name + '_itemDetails') [1],
          scale: this.originScaleOf.get(target.name + '_itemDetails')
        });
      });
      this.detailsShownFor.set(item.getId() + '_itemDetails', false);
      item.getPlayerSprite().setAlpha(1);
    }
  }

}
