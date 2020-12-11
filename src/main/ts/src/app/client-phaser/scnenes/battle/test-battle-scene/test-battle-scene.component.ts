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
      '{"gameId":"e367db9abb5fce51681b989ad8f4161b4df4e83cc9f76531964b6d7ce4a8ceb3","players":{"ComPlayer_6757":{"playerId":"ComPlayer_6757","character":{"name":"Tossico del Serraglio","itemsSize":3,"items":[{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Peroni da 66, calda","effect":"<p>Alcohol: +5. Equip (single use): next Hit Damage you deal is increased and turned to Cut Damage.</p>","image":null,"sprite":"item-sprite.png","item":true,"lastResort":false,"basicAction":false,"characterBound":false}],"resources":{"HEALTH":100,"ALERTNESS":35,"TOXICITY":10},"statuses":[],"immunities":["TOXICITY"],"sprite":"tossico.png"},"cardsInHand":6,"deck":10},"gesucca":{"playerId":"gesucca","character":{"name":"Tossico del Serraglio","itemsSize":3,"items":[{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Tennents Super","effect":"<p>Alcohol: +10. Spawn a Broken Beer Bottle Token near you.</p>","image":null,"sprite":"item-sprite.png","item":true,"lastResort":false,"basicAction":false,"characterBound":false},{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Peroni da 66, calda","effect":"<p>Alcohol: +5. Equip (single use): next Hit Damage you deal is increased and turned to Cut Damage.</p>","image":null,"sprite":"item-sprite.png","item":true,"lastResort":false,"basicAction":false,"characterBound":false},{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Tennents Super","effect":"<p>Alcohol: +10. Spawn a Broken Beer Bottle Token near you.</p>","image":null,"sprite":"item-sprite.png","item":true,"lastResort":false,"basicAction":false,"characterBound":false}],"resources":{"HEALTH":100,"ALERTNESS":35,"TOXICITY":10},"statuses":[],"immunities":["TOXICITY"],"sprite":"tossico.png"},"cardsInHand":[{"cost":{},"priority":1,"canTarget":["NEAR_ITEM"],"name":"Desiderio Incontrollabile","effect":"<p>Basic Action <i>(you can always do this if its conditions are met)</i></p><p>Grab an Item near you and immediately use it. You lose half of your Alertness, rounded down.</p>","image":null,"sprite":null,"item":false,"lastResort":false,"basicAction":true,"characterBound":false},{"cost":{},"priority":2,"canTarget":["OPPONENT"],"name":"Ghigno co\' i\' Denti Marci","effect":"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>Quick Move <i>(you act first than others when doing this)</i></p><p>-5 Alertness to Target and +5 Alertness to you.</p>","image":null,"sprite":null,"item":false,"lastResort":false,"basicAction":false,"characterBound":true},{"cost":{},"priority":1,"canTarget":["OPPONENT"],"name":"Ago Marcio e Spezzato","effect":"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>5 Cut Damage to Target and +5 Toxicity to both you and Target.</p>","image":null,"sprite":null,"item":false,"lastResort":false,"basicAction":false,"characterBound":true},{"cost":{"ALERTNESS":10},"priority":0,"canTarget":["FAR_ITEM"],"name":"Steal","effect":"<p>Slow Move <i>(others act first when you are doing this)</i></p><p>Steal an Item from Target and place it near you, ready to be used.</p>","image":null,"sprite":null,"item":false,"lastResort":false,"basicAction":false,"characterBound":false},{"cost":{},"priority":1,"canTarget":["NEAR_ITEM"],"name":"Grab","effect":"<p>Grab an Item near you and immediately use it.</p>","image":null,"sprite":null,"item":false,"lastResort":false,"basicAction":false,"characterBound":false},{"cost":{},"priority":1,"canTarget":["NEAR_ITEM"],"name":"Grab","effect":"<p>Grab an Item near you and immediately use it.</p>","image":null,"sprite":null,"item":false,"lastResort":false,"basicAction":false,"characterBound":false}],"deck":8},"ComPlayer_6929":{"playerId":"ComPlayer_6929","character":{"name":"Tossico del Serraglio","itemsSize":3,"items":[{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Tennents Super","effect":"<p>Alcohol: +10. Spawn a Broken Beer Bottle Token near you.</p>","image":null,"sprite":"item-sprite.png","item":true,"lastResort":false,"basicAction":false,"characterBound":false}],"resources":{"HEALTH":100,"ALERTNESS":35,"TOXICITY":10},"statuses":[],"immunities":["TOXICITY"],"sprite":"tossico.png"},"cardsInHand":6,"deck":10},"ComPlayer_8675":{"playerId":"ComPlayer_8675","character":{"name":"Spazienzio de la Ucciso","itemsSize":1,"items":[],"resources":{"HEALTH":100,"ALERTNESS":20,"VIOLENCE":25},"statuses":[],"immunities":[],"sprite":"spazienzio.png"},"cardsInHand":6,"deck":17},"ComPlayer_4747":{"playerId":"ComPlayer_4747","character":{"name":"Spazienzio de la Ucciso","itemsSize":1,"items":[{"cost":{},"priority":1,"canTarget":["SELF"],"name":"Peroni da 66, calda","effect":"<p>Alcohol: +5. Equip (single use): next Hit Damage you deal is increased and turned to Cut Damage.</p>","image":null,"sprite":"item-sprite.png","item":true,"lastResort":false,"basicAction":false,"characterBound":false}],"resources":{"HEALTH":100,"ALERTNESS":20,"VIOLENCE":25},"statuses":[],"immunities":[],"sprite":"spazienzio.png"},"cardsInHand":6,"deck":16}},"resolvedMoves":[],"timeBasedEffects":{},"over":false,"winner":"NONE"}'
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
