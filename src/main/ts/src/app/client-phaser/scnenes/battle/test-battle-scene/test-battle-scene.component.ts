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
      '{"gameId":"f7eac8a40ca73ae8af8d28483393960db76cdaa71ae260b4b1dd74af9110f624","players":{"ComPlayer_9151":{"playerId":"ComPlayer_9151","character":{"name":"Tossico del Serraglio","itemsSize":3,"items":[{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Tennents Super","effect":"<p>Alcohol: +10. Spawn a Broken Beer Bottle Token near you.</p>","lastResort":false,"item":true,"characterBound":false,"basicAction":false},{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Peroni da 66, calda","effect":"<p>Alcohol: +5. Equip (single use): next Hit Damage you deal is increased and turned to Cut Damage.</p>","lastResort":false,"item":true,"characterBound":false,"basicAction":false}],"resources":{"HEALTH":100,"ALERTNESS":35,"TOXICITY":10},"statuses":[],"immunities":["TOXICITY"],"dead":false},"cardsInHand":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"deck":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"lastResortCard":{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"Last Resort: Banzai!!!","effect":"<p>10 Hit Damage to Target and 5 Hit Damage to you.</p>","lastResort":true,"item":false,"characterBound":false,"basicAction":false},"deckSize":9},"ComPlayer_3919":{"playerId":"ComPlayer_3919","character":{"name":"Spazienzio de la Ucciso","itemsSize":1,"items":[],"resources":{"HEALTH":100,"ALERTNESS":20,"VIOLENCE":25},"statuses":[],"immunities":[],"dead":false},"cardsInHand":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"deck":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"lastResortCard":{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"Last Resort: Banzai!!!","effect":"<p>10 Hit Damage to Target and 5 Hit Damage to you.</p>","lastResort":true,"item":false,"characterBound":false,"basicAction":false},"deckSize":17},"ComPlayer_2077":{"playerId":"ComPlayer_2077","character":{"name":"Spazienzio de la Ucciso","itemsSize":1,"items":[],"resources":{"HEALTH":100,"ALERTNESS":20,"VIOLENCE":25},"statuses":[],"immunities":[],"dead":false},"cardsInHand":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"deck":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"lastResortCard":{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"Last Resort: Banzai!!!","effect":"<p>10 Hit Damage to Target and 5 Hit Damage to you.</p>","lastResort":true,"item":false,"characterBound":false,"basicAction":false},"deckSize":17},"gesucca":{"playerId":"gesucca","character":{"name":"Tossico del Serraglio","itemsSize":3,"items":[],"resources":{"HEALTH":100,"ALERTNESS":35,"TOXICITY":10},"statuses":[],"immunities":["TOXICITY"],"dead":false},"cardsInHand":[{"cost":{},"priority":1,"canTarget":["NEAR_ITEM"],"name":"Desiderio Incontrollabile","effect":"<p>Basic Action <i>(you can always do this if its conditions are met)</i></p><p>Grab an Item near you and immediately use it. You lose half of your Alertness, rounded down.</p>","lastResort":false,"item":false,"characterBound":false,"basicAction":true},{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"Ago Marcio e Spezzato","effect":"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>5 Cut Damage to Target and +5 Toxicity to both you and Target.</p>","lastResort":false,"item":false,"characterBound":true,"basicAction":false},{"cost":{},"priority":2,"canTarget":["OPPONENT"],"name":"Ghigno co\' i\' Denti Marci","effect":"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>Quick Move <i>(you act first than others when doing this)</i></p><p>-5 Alertness to Target and +5 Alertness to you.</p>","lastResort":false,"item":false,"characterBound":true,"basicAction":false},{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Dose di Eroina da Cavalli","effect":"<p>Fill your Toxicity up to 30. If you already had 30 Toxicity or more, you lose that much Health.</p>","lastResort":false,"item":false,"characterBound":false,"basicAction":false},{"cost":{"ALERTNESS":10},"priority":0,"canTarget":["FAR_ITEM"],"name":"Steal","effect":"<p>Slow Move <i>(others act first when you are doing this)</i></p><p>Steal an Item from Target and place it near you, ready to be used.</p>","lastResort":false,"item":false,"characterBound":false,"basicAction":false},{"cost":{},"priority":1,"canTarget":["NEAR_ITEM"],"name":"Grab","effect":"<p>Grab an Item near you and immediately use it.</p>","lastResort":false,"item":false,"characterBound":false,"basicAction":false}],"deck":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"lastResortCard":{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"Last Resort: Banzai!!!","effect":"<p>10 Hit Damage to Target and 5 Hit Damage to you.</p>","lastResort":true,"item":false,"characterBound":false,"basicAction":false},"deckSize":11},"ComPlayer_9857":{"playerId":"ComPlayer_9857","character":{"name":"Spazienzio de la Ucciso","itemsSize":1,"items":[{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Negroni Trovato per Terra","effect":"<p>Violence: +20. Alcohol: +15; Toxicity: +5;</p>","lastResort":false,"item":true,"characterBound":false,"basicAction":false}],"resources":{"HEALTH":100,"ALERTNESS":20,"VIOLENCE":25},"statuses":[],"immunities":[],"dead":false},"cardsInHand":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"deck":[{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false},{"name":"Unknown Card","item":false,"basicAction":false}],"lastResortCard":{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"Last Resort: Banzai!!!","effect":"<p>10 Hit Damage to Target and 5 Hit Damage to you.</p>","lastResort":true,"item":false,"characterBound":false,"basicAction":false},"deckSize":16}},"resolvedMoves":[],"timeBasedEffects":{},"over":false,"winner":"NONE"}'
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
