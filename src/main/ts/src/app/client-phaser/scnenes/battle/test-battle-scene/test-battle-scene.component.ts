import {Component, OnInit} from '@angular/core';
import Phaser from "phaser";
import {PhaserSettingsService} from "../../../phaser-settings.service";
import {BattleScene} from "../battle-scene";
import {GameService} from "../../../../service/game.service";

@Component({
  selector: 'app-test-battle-scene',
  templateUrl: './test-battle-scene.component.html',
  styleUrls: ['./test-battle-scene.component.css']
})
export class TestBattleSceneComponent implements OnInit {

  phaserGame: Phaser.Game;
  config: Phaser.Types.Core.GameConfig;

  constructor(protected gameService: GameService, protected settingsService: PhaserSettingsService) {
  }

  ngOnInit(): void {
    window['gameService'] = this.gameService; // if this hack works its huge
    window['settingsService'] = this.settingsService;
    this.gameService.gameState = JSON.parse(
      '{"gameId":"53522d64f51aea1fb71b84fd35cd8d0924e1c78635a2f7b54a43fb6136c48a02","players":{"ComPlayer_7392":{"playerId":"ComPlayer_7392","character":{"name":"Spazienzio de la Ucciso","itemsSize":1,"items":[{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Negroni Trovato per Terra","effect":"<p>Violence: +20. Alcohol: +15; Toxicity: +5;</p>","basicAction":false,"lastResort":false,"item":true,"characterBound":false}],"resources":{"HEALTH":100,"ALERTNESS":20,"VIOLENCE":25},"statuses":[],"immunities":[],"dead":false},"cardsInHand":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"deck":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"lastResortCard":{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"Last Resort: Banzai!!!","effect":"<p>10 Hit Damage to Target and 5 Hit Damage to you.</p>","basicAction":false,"lastResort":true,"item":false,"characterBound":false},"deckSize":16},"gesucca":{"playerId":"gesucca","character":{"name":"Spazienzio de la Ucciso","itemsSize":1,"items":[],"resources":{"HEALTH":100,"ALERTNESS":20,"VIOLENCE":25},"statuses":[],"immunities":[],"dead":false},"cardsInHand":[{"cost":{},"priority":-1,"canTarget":["NEAR_ITEM"],"name":"ADOPERIAMOTI","effect":"<p>Basic Action <i>(you can always do this if its conditions are met)</i></p><p>Slow Move <i>(others act first when you are doing this)</i></p><p>Grab an Item near you and immediately use it.</p>","basicAction":true,"lastResort":false,"item":false,"characterBound":false},{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"SVEGLIAMOCI MA TU NO","effect":"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>5 Hit Damages to Target, then Alertness: +5.</p>","basicAction":false,"lastResort":false,"item":false,"characterBound":true},{"cost":{},"priority":2,"canTarget":["SELF"],"name":"LIEVE FASTIDIO","effect":"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>Quick Move <i>(you act first than others when doing this)</i></p><p>Violence: +5. This Turn, you take a bit less Hit Damage. </p>","basicAction":false,"lastResort":false,"item":false,"characterBound":true},{"cost":{"VIOLENCE":10},"priority":1,"canTarget":["OPPONENT"],"name":"GABBIANO DUNQUE VOLIAMOTI","effect":"<p>15 Hit Damage to Target. Ignore Good Statuses while inflicting this Damage.</p>","basicAction":false,"lastResort":false,"item":false,"characterBound":false},{"cost":{"VIOLENCE":20},"priority":1,"canTarget":["OPPONENT"],"name":"SPEDIAMOTI DUNQUE IN O-AHIO","effect":"<p>Hit Damage to Target equals to 20 plus 1 for each 2 Violence points you have left.</p>","basicAction":false,"lastResort":false,"item":false,"characterBound":false},{"cost":{"VIOLENCE":10},"priority":1,"canTarget":["FAR_ITEM"],"name":"BELLO IL TUO GIOCATTOLO SI","effect":"<p>Destroy Target Item.</p>","basicAction":false,"lastResort":false,"item":false,"characterBound":false}],"deck":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"lastResortCard":{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"Last Resort: Banzai!!!","effect":"<p>10 Hit Damage to Target and 5 Hit Damage to you.</p>","basicAction":false,"lastResort":true,"item":false,"characterBound":false},"deckSize":17}},"resolvedMoves":[],"timeBasedEffects":{},"over":false,"winner":"NONE"}'
    );
    this.gameService.playerId = 'gesucca';

    this.config = {
      type: Phaser.AUTO,
      scale: {
        mode: Phaser.Scale.FIT,
        parent: 'phaserContainer',
        autoCenter: Phaser.Scale.CENTER_BOTH,
        width: this.settingsService.getScreenWidth(),
        height: this.settingsService.getScreenHeight(),
      },
      scene: [BattleScene]
    }
    this.phaserGame = new Phaser.Game(this.config);
  }

}
