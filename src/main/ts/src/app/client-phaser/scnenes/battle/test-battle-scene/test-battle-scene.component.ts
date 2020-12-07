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

  config: Phaser.Types.Core.GameConfig;

  constructor(protected gameService: GameService, protected settingsService: PhaserSettingsService) {
  }

  ngOnInit(): void {
    window['gameService'] = this.gameService;
    window['settingsService'] = this.settingsService;
    this.gameService.gameState = JSON.parse(
      '{"gameId":"b7789c015df706e988b2ab6b177cdfb5ed1b77121d898c45e0b6880453175c8e","players":{"ComPlayer_7340":{"playerId":"ComPlayer_7340","character":{"name":"Tossico del Serraglio","itemsSize":3,"items":[{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Tennents Super","effect":"<p>Alcohol: +10. Spawn a Broken Beer Bottle Token near you.</p>","lastResort":false,"item":true,"characterBound":false,"basicAction":false},{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Peroni da 66, calda","effect":"<p>Alcohol: +5. Equip (single use): next Hit Damage you deal is increased and turned to Cut Damage.</p>","lastResort":false,"item":true,"characterBound":false,"basicAction":false}],"resources":{"HEALTH":100,"ALERTNESS":35,"TOXICITY":10},"statuses":[],"immunities":["TOXICITY"],"sprite":"characters/tossico.png"},"cardsInHand":6,"deck":9},"ComPlayer_7111":{"playerId":"ComPlayer_7111","character":{"name":"Spazienzio de la Ucciso","itemsSize":1,"items":[{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Negroni Trovato per Terra","effect":"<p>Violence: +20. Alcohol: +15; Toxicity: +5;</p>","lastResort":false,"item":true,"characterBound":false,"basicAction":false}],"resources":{"HEALTH":100,"ALERTNESS":20,"VIOLENCE":25},"statuses":[],"immunities":[],"sprite":"characters/spazienzio.png"},"cardsInHand":6,"deck":16},"gesucca":{"playerId":"gesucca","character":{"name":"Spazienzio de la Ucciso","itemsSize":1,"items":[{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Peroni da 66, calda","effect":"<p>Alcohol: +5. Equip (single use): next Hit Damage you deal is increased and turned to Cut Damage.</p>","lastResort":false,"item":true,"characterBound":false,"basicAction":false}],"resources":{"HEALTH":100,"ALERTNESS":20,"VIOLENCE":25},"statuses":[],"immunities":[],"sprite":"characters/spazienzio.png"},"cardsInHand":[{"cost":{},"priority":-1,"canTarget":["NEAR_ITEM"],"name":"ADOPERIAMOTI","effect":"<p>Basic Action <i>(you can always do this if its conditions are met)</i></p><p>Slow Move <i>(others act first when you are doing this)</i></p><p>Grab an Item near you and immediately use it.</p>","lastResort":false,"item":false,"characterBound":false,"basicAction":true},{"cost":{},"priority":2,"canTarget":["SELF"],"name":"LIEVE FASTIDIO","effect":"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>Quick Move <i>(you act first than others when doing this)</i></p><p>Violence: +5. This Turn, you take a bit less Hit Damage. </p>","lastResort":false,"item":false,"characterBound":true,"basicAction":false},{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"SVEGLIAMOCI MA TU NO","effect":"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>5 Hit Damages to Target, then Alertness: +5.</p>","lastResort":false,"item":false,"characterBound":true,"basicAction":false},{"cost":{"VIOLENCE":20},"priority":1,"canTarget":["OPPONENT"],"name":"ORMALE GUARIAMOTI","effect":"<p>30 Hit Damage, but Target is cured and sobered up.</p>","lastResort":false,"item":false,"characterBound":false,"basicAction":false},{"cost":{},"priority":0,"canTarget":["SELF"],"name":"PAZIENZA: FINITA","effect":"<p>Slow Move <i>(others act first when you are doing this)</i></p><p>Violence: +15, Alertness: +15. You take more Hit Damage next turn.</p>","lastResort":false,"item":false,"characterBound":false,"basicAction":false},{"cost":{},"priority":0,"canTarget":["SELF"],"name":"PAZIENZA: FINITA","effect":"<p>Slow Move <i>(others act first when you are doing this)</i></p><p>Violence: +15, Alertness: +15. You take more Hit Damage next turn.</p>","lastResort":false,"item":false,"characterBound":false,"basicAction":false}],"deck":16},"ComPlayer_1008":{"playerId":"ComPlayer_1008","character":{"name":"Spazienzio de la Ucciso","itemsSize":1,"items":[],"resources":{"HEALTH":100,"ALERTNESS":20,"VIOLENCE":25},"statuses":[],"immunities":[],"sprite":"characters/spazienzio.png"},"cardsInHand":6,"deck":17},"ComPlayer_826":{"playerId":"ComPlayer_826","character":{"name":"Tossico del Serraglio","itemsSize":3,"items":[],"resources":{"HEALTH":100,"ALERTNESS":35,"TOXICITY":10},"statuses":[],"immunities":["TOXICITY"],"sprite":"characters/tossico.png"},"cardsInHand":6,"deck":11}},"resolvedMoves":[],"timeBasedEffects":{},"over":false,"winner":"NONE"}'
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
  }

}
