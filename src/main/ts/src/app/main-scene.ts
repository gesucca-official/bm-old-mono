export class MainScene extends Phaser.Scene {
  private square: Phaser.GameObjects.Rectangle & { body: Phaser.Physics.Arcade.Body };

  constructor() {
    super({key: 'main'});
  }

  public create() {
    this.square = this.add.rectangle(window.innerWidth / 4, window.innerHeight / 4, window.innerWidth / 8, window.innerHeight / 8, 0xFFFFFF) as any;
    this.physics.add.existing(this.square);
  }

  preload() {
    console.log('preload method');
  }

  update() {
    console.log('update method');
  }
}
