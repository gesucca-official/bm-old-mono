import {UI_CardInHand} from "../model/ui-card-in-hand";
import {PhaserSettingsService} from "../../../phaser-settings.service";

export function CARD_HIGHLIGHT(scene: Phaser.Scene, card: UI_CardInHand, settings: PhaserSettingsService): void {
  const container = card.getContainer();
  container.on('pointerover', () => {

    container.setDepth(container.depth + 10);
    (container.getByName('template') as Phaser.GameObjects.Image).setTint(0x44ff44);

    scene.tweens.add({
      targets: container,
      ease: 'Sine.easeInOut',
      delay: 100,
      duration: 250,
      y: container.y - settings.scaleForHeight(20)
    });
    scene.tweens.add({
      targets: container,
      ease: 'Sine.easeInOut',
      delay: 250,
      duration: 500,
      scale: 1.05,
      yoyo: true,
      repeat: -1
    });
  });
}

export function resetCardHighlight(scene: Phaser.Scene, card: UI_CardInHand): void {
  const container = card.getContainer();
  const template = (container.getByName('template') as Phaser.GameObjects.Image);
  container.on('pointerout', () => {
    template.clearTint();
    container.setDepth(container.depth - 10);

    scene.tweens.killAll();
    scene.tweens.add({
      targets: container,
      ease: 'Sine.easeInOut',
      delay: 100,
      duration: 250,
      x: card.getCardX(template.displayWidth, card.getIndex()),
      y: card.getCardY(template.displayHeight)
    });
    scene.tweens.add({
      targets: container,
      ease: 'Sine.easeInOut',
      delay: 100,
      duration: 250,
      scale: 1,
    });
  });
}
