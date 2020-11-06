import {Component, OnInit} from '@angular/core';
import Phaser from 'phaser';
import {MainScene} from "../main-scene";

@Component({
  selector: 'app-phaser-game-component',
  templateUrl: './phaser-game-component.component.html',
  styleUrls: ['./phaser-game-component.component.css']
})
export class PhaserGameComponentComponent implements OnInit {

  phaserGame: Phaser.Game;
  config: Phaser.Types.Core.GameConfig;

  constructor() {
    this.config = {
      type: Phaser.AUTO,
      scale: {
        width: window.innerWidth,
        height: window.innerHeight,
      },
      scene: [MainScene],
      parent: 'gameContainer',
      physics: {
        default: 'arcade',
        arcade: {
          gravity: {y: 100}
        }
      }
    };
  }

  ngOnInit() {
    this.phaserGame = new Phaser.Game(this.config);
  }

}
