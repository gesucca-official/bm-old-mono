import {UI_AbstractObject} from "../model/ui-abstract-object";
import {PhaserSettingsService} from "../../../phaser-settings.service";

const DETAILS_SHOWN: Map<string, boolean> = new Map<string, boolean>();
const ORIGIN_POS: Map<string, [number, number]> = new Map<string, [number, number]>();

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
