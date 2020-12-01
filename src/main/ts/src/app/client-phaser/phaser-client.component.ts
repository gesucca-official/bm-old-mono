import {Component, OnInit} from '@angular/core';
// @ts-ignore
import Phaser from 'phaser';
import {TitleScene} from "./scnenes/title-scene";
import {GLOBALS} from "./globals";

@Component({
  selector: 'app-phaser-client',
  templateUrl: './phaser-client.component.html',
  styleUrls: ['./phaser-client.component.css']
})
export class PhaserClientComponent implements OnInit {

  phaserGame: Phaser.Game;
  config: Phaser.Types.Core.GameConfig = {
    type: Phaser.AUTO,
    scale: {
      mode: Phaser.Scale.FIT,
      parent: 'phaserContainer',
      autoCenter: Phaser.Scale.NO_CENTER,
      width: GLOBALS.WIDTH,
      height: GLOBALS.HEIGHT
    },
    scene: [TitleScene]
  }

  ngOnInit(): void {
    this.phaserGame = new Phaser.Game(this.config);
  }

}
