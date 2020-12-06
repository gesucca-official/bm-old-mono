import {Component, ViewChild} from '@angular/core';
import Phaser from "phaser";
import {TestBattleSceneComponent} from "./client-phaser/scnenes/battle/test-battle-scene/test-battle-scene.component";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  phaserGame: Phaser.Game;
  config: Phaser.Types.Core.GameConfig;

  @ViewChild(TestBattleSceneComponent)
  testBattleSceneComponent: TestBattleSceneComponent;

  // I know this pretty much sucks
  // Phaser is picky about the way it is rendered (see comment in this component's template)

  isShowingDebugClient = false;
  isShowingGraphicClient = false;
  isShowingTestBattle = false;

  isShowingSomethingElse(): boolean {
    return this.isShowingGraphicClient || this.isShowingTestBattle || this.isShowingDebugClient;
  }

  showGraphicClient() {
    this.isShowingGraphicClient = true;
  }

  showTestBattle() {
    this.isShowingTestBattle = true;
  }

  showDebugClient() {
    this.isShowingDebugClient = true;
  }

}
