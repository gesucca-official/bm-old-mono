import {UI_AbstractObject} from "../model/ui-abstract-object";
import {PhaserSettingsService} from "../../../phaser-settings.service";
import {UI_Item} from "../model/ui-item";

const DETAILS_SHOWN: Map<string, boolean> = new Map<string, boolean>();
const ORIGIN_POS: Map<string, [number, number]> = new Map<string, [number, number]>();
const ORIGIN_SCALE: Map<string, number> = new Map<string, number>();

let BLUR_BACKGROUND: Phaser.GameObjects.Rectangle;

export function PLAYER_DETAILS(scene: Phaser.Scene, player: UI_AbstractObject, settings: PhaserSettingsService) {
  if (!DETAILS_SHOWN.get(player.getId())) {
    player.getAnimationTargets().forEach(target => {
      target.setDepth(target.depth + 5);
      ORIGIN_POS.set(target.name, [target.x, target.y]);
      scene.tweens.add({
        targets: target,
        ease: 'Sine.easeInOut',
        delay: 100,
        duration: 250,
        x: (target.x - player.getX()) * 1.5 + player.getContainer().displayWidth + settings.scaleForMin(100),
        y: target.y + (settings.getScreenHeight() / 2 - target.displayHeight / 2) - settings.scaleForMin(100),
        scale: 1.5
      });
      DETAILS_SHOWN.set(player.getId(), true);
    })
  } else {
    player.getAnimationTargets().forEach(target => {
      scene.tweens.killAll();
      target.setDepth(target.depth - 5);
      scene.tweens.add({
        targets: target,
        ease: 'Sine.easeInOut',
        delay: 100,
        duration: 250,
        x: ORIGIN_POS.get(target.name) [0],
        y: ORIGIN_POS.get(target.name) [1],
        scale: 1
      });
    });
    DETAILS_SHOWN.set(player.getId(), false);
  }
}

// TODO maybe PLAYER_DETAILS can be generalized and used instead of this
// in any case, refactor this at earliest convenience
export function ITEM_DETAILS(scene: Phaser.Scene, item: UI_Item): void {
  if (!DETAILS_SHOWN.get(item.getId() + '_itemDetails')) {
    item.getAnimationTargets().forEach(target => {
      target.setDepth(target.depth + 1);
      // I have to change the name to avoid overriding the original position set in players details
      ORIGIN_POS.set(target.name + '_itemDetails', [target.x, target.y]);
      ORIGIN_SCALE.set(target.name + '_itemDetails', target.scale);
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
    DETAILS_SHOWN.set(item.getId() + '_itemDetails', true);
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
        x: ORIGIN_POS.get(target.name + '_itemDetails') [0],
        y: ORIGIN_POS.get(target.name + '_itemDetails') [1],
        scale: ORIGIN_SCALE.get(target.name + '_itemDetails')
      });
    });
    DETAILS_SHOWN.set(item.getId() + '_itemDetails', false);
    item.getPlayerSprite().setAlpha(1);
  }
}

export function FOCUS_DETAILS(scene: Phaser.Scene, player: UI_AbstractObject, settings: PhaserSettingsService) {
  if (!DETAILS_SHOWN.get(player.getId())) {
    BLUR_BACKGROUND = scene.add.rectangle(0, 0,
      settings.getScreenWidth(),
      settings.getScreenHeight(), 0x9e9e9e, 0.75)
      .setOrigin(0, 0)
      .setInteractive(); // this prevents things underneath it to be clicked
  } else {
    BLUR_BACKGROUND.destroy();
  }
}
