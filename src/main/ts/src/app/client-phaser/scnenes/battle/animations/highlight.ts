import {PhaserSettingsService} from "../../../phaser-settings.service";
import {UI_AbstractObject} from "../model/ui-abstract-object";

export function HIGHLIGHT(scene: Phaser.Scene,
                          settings: PhaserSettingsService,
                          target: UI_AbstractObject,
                          event?: string,
                          raise?: boolean): void {
  target.getContainer().on(event ? event : 'pointerover', () => {
    target.getContainer().setDepth(target.getContainer().depth + 10);
    target.getTintTarget().setTint(0x44ff44);
    if (raise)
      scene.tweens.add({
        targets: target.getAnimationTargets(),
        ease: 'Sine.easeInOut',
        delay: 100,
        duration: 250,
        y: target.getContainer().y - settings.scaleForHeight(20)
      });
    scene.tweens.add({
      targets: target.getAnimationTargets(),
      ease: 'Sine.easeInOut',
      delay: 250,
      duration: 500,
      scale: 1.05,
      yoyo: true,
      repeat: -1
    });
  });
}

export function RESET_HIGHLIGHT(scene: Phaser.Scene, target: UI_AbstractObject, event?: string, origScale?: number): void {
  target.getContainer().on(event ? event : 'pointerout', () => {
    target.getContainer().setDepth(target.getContainer().depth - 10);
    target.getTintTarget().clearTint();
    scene.tweens.killAll();
    scene.tweens.add({
      targets: target.getAnimationTargets(),
      ease: 'Sine.easeInOut',
      delay: 100,
      duration: 250,
      x: target.getX(),
      y: target.getY()
    });
    scene.tweens.add({
      targets: target.getAnimationTargets(),
      ease: 'Sine.easeInOut',
      delay: 100,
      duration: 250,
      scale: origScale ? origScale : 1,
    });
  });
}
