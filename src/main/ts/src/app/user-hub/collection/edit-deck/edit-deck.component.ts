import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Deck} from "../../../model/deck";
import {SessionService} from "../../../service/session.service";
import {Collection} from "../../../model/user-account-data";
import {Card} from "../../../model/card";

@Component({
  selector: 'app-edit-deck',
  templateUrl: './edit-deck.component.html',
  styleUrls: ['./edit-deck.component.css']
})
export class EditDeckComponent implements OnInit {

  collection: Collection = JSON.parse("{\"characters\":[{\"bindingName\":\"junkie.character.ToxicJunkie\",\"itemsSize\":3,\"items\":[],\"resources\":{\"HEALTH\":100,\"ALERTNESS\":35,\"TOXICITY\":10},\"statuses\":[],\"immunities\":[\"TOXICITY\"],\"timers\":[],\"dead\":false},{\"bindingName\":\"bruiser.character.BigBadBruiser\",\"itemsSize\":1,\"items\":[],\"resources\":{\"HEALTH\":100,\"ALERTNESS\":20,\"VIOLENCE\":25},\"statuses\":[],\"immunities\":[],\"timers\":[],\"dead\":false}],\"cards\":[{\"priority\":0,\"cost\":{\"TOXICITY\":25},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"Ce l'hai due spiccioli per il biglietto?\",\"effect\":\"<p>Slow Move <i>(others act first when you are doing this)</i></p><p>-10 Alertness to Target. You sober up a bit and beg Target for a random Item.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.BeggingMoney\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":2,\"cost\":{\"TOXICITY\":10},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"Riflessi Aumentati dalla Speed\",\"effect\":\"<p>Quick Move <i>(you act first than others when doing this)</i></p><p>+5 Alertness to You. Counter every next Move that Targets You.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.AmphetamineReflexes\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":0,\"cost\":{},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"PAZIENZA: FINITA\",\"effect\":\"<p>Slow Move <i>(others act first when you are doing this)</i></p><p>Violence: +15, Alertness: +15. You take more Hit Damage next turn.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.Glare\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"NEAR_ITEM\"],\"boundToCharacter\":null,\"name\":\"Tutto è Droga\",\"effect\":\"<p>Toxicity: +20, but it consumes an Item near You.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.EverythingIsDrug\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"NEAR_ITEM\"],\"boundToCharacter\":null,\"name\":\"Desiderio Incontrollabile\",\"effect\":\"<p>Basic Action <i>(you can always do this if its conditions are met)</i></p><p>Grab an Item near you and immediately use it. You lose half of your Alertness, rounded down.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"com.basic.ToxicGrab\",\"basicAction\":true,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"NEAR_ITEM\"],\"boundToCharacter\":null,\"name\":\"Grab\",\"effect\":\"<p>Grab an Item near you and immediately use it.</p>\",\"image\":\"grab.png\",\"sprite\":null,\"bindingName\":\"com.Grab\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":2,\"cost\":{},\"canTarget\":[\"SELF\"],\"boundToCharacter\":\"bruiser.character.BigBadBruiser\",\"name\":\"LIEVE FASTIDIO\",\"effect\":\"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>Quick Move <i>(you act first than others when doing this)</i></p><p>Violence: +5. This Turn, you take a bit less Hit Damage. </p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.character.LittleRage\",\"basicAction\":false,\"characterBound\":true,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"Negroni Trovato per Terra\",\"effect\":\"<p>Violence: +20. Alcohol: +15; Toxicity: +5;</p>\",\"image\":\"negroni.png\",\"sprite\":\"item-sprite.png\",\"bindingName\":\"com.items.CocktailOnTheGround\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":true},{\"priority\":1,\"cost\":{},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"Tennents Super\",\"effect\":\"<p>Alcohol: +10. Spawn a Broken Beer Bottle Token near you.</p>\",\"image\":\"no-img.png\",\"sprite\":\"item-sprite.png\",\"bindingName\":\"com.items.AnotherRottenBeer\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":true},{\"priority\":2,\"cost\":{\"VIOLENCE\":5},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"FAI BASTA\",\"effect\":\"<p>Quick Move <i>(you act first than others when doing this)</i></p><p>Counter Target's own Move, but gives him +10 Alertness.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.YouStop\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":0,\"cost\":{\"ALERTNESS\":10},\"canTarget\":[\"FAR_ITEM\"],\"boundToCharacter\":null,\"name\":\"Steal\",\"effect\":\"<p>Slow Move <i>(others act first when you are doing this)</i></p><p>Steal an Item from Target and place it near you, ready to be used.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"com.Steal\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{\"VIOLENCE\":10},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"PERMESSO SCUSA ORA SMETTI \",\"effect\":\"<p>10 Hit Damage to Target. Target loses all his Equipped Statuses.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.BadEducation\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":\"bruiser.character.BigBadBruiser\",\"name\":\"SVEGLIAMOCI MA TU NO\",\"effect\":\"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>5 Hit Damages to Target, then Alertness: +5.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.character.LittleSmack\",\"basicAction\":false,\"characterBound\":true,\"lastResort\":false,\"item\":false},{\"priority\":0,\"cost\":{\"VIOLENCE\":25},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"GARGANTUESCO IMPATTO SUL TU\",\"effect\":\"<p>Slow Move <i>(others act first when you are doing this)</i></p><p>40 Hit Damage to Target.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.SuperImpact\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":-1,\"cost\":{},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"Scatto Repentino\",\"effect\":\"<p>Slow Move <i>(others act first when you are doing this)</i></p><p>Switch Place with Target, getting his Items near you.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"com.ShuffleFeet\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":2,\"cost\":{\"ALERTNESS\":10},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"Presa Male di Brutto\",\"effect\":\"<p>Quick Move <i>(you act first than others when doing this)</i></p><p>Double your Toxicity, but You lose immunity to it for 3 Turns.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.BadTrip\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"Dose di Eroina da Cavalli\",\"effect\":\"<p>Fill your Toxicity up to 30. If you already had 30 Toxicity or more, you lose that much Health.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.LethalHeroinDose\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{\"TOXICITY\":10},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"C'è del sangue nella mia droga\",\"effect\":\"<p>Transfer all your Toxicity to Target (after paying this card's cost). He loses immunity to Toxicity this turn.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.ToxicBloodDrain\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{\"VIOLENCE\":10},\"canTarget\":[\"FAR_ITEM\"],\"boundToCharacter\":null,\"name\":\"BELLO IL TUO GIOCATTOLO SI\",\"effect\":\"<p>Destroy Target Item.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.BreakYourToy\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"Mescolone Micidiale\",\"effect\":\"<p>Alcohol: +20, Toxicity: +20, You gain Immunity from both. In 5 Turn, You take 20 Poison Damage and lose all immunities and statuses.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"com.items.MarvelousCocktail\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":true},{\"priority\":1,\"cost\":{\"TOXICITY\":10},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"Tossicone Scatarrante\",\"effect\":\"<p>-5 Alertness and +5 Toxicity to Target.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.Cough\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":2,\"cost\":{},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":\"junkie.character.ToxicJunkie\",\"name\":\"Ghigno co' i' Denti Marci\",\"effect\":\"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>Quick Move <i>(you act first than others when doing this)</i></p><p>-5 Alertness to Target and +5 Alertness to you.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.character.RottenSmile\",\"basicAction\":false,\"characterBound\":true,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{\"VIOLENCE\":20},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"ORMALE GUARIAMOTI\",\"effect\":\"<p>30 Hit Damage, but Target is cured and sobered up.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.HealingBlow\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{\"VIOLENCE\":20},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"SPEDIAMOTI DUNQUE IN O-AHIO\",\"effect\":\"<p>Hit Damage to Target equals to 20 plus 1 for each 2 Violence points you have left.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.ExtremelyViolentBlow\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"Metabolismo Corrotto\",\"effect\":\"<p>You lose all your Toxicity and gain Health equals to half of it.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.PervertedMetabolism\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{\"VIOLENCE\":10},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"GABBIANO DUNQUE VOLIAMOTI\",\"effect\":\"<p>15 Hit Damage to Target. Ignore Good Statuses while inflicting this Damage.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.SeagullFly\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":-1,\"cost\":{},\"canTarget\":[\"NEAR_ITEM\"],\"boundToCharacter\":null,\"name\":\"ADOPERIAMOTI\",\"effect\":\"<p>Basic Action <i>(you can always do this if its conditions are met)</i></p><p>Slow Move <i>(others act first when you are doing this)</i></p><p>Grab an Item near you and immediately use it.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"com.basic.BruiserGrab\",\"basicAction\":true,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{\"VIOLENCE\":15},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"AMMALIAMOTI DI BOTTE\",\"effect\":\"<p>10 Hit Damage and +10 Toxicity to Target.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.SickeningBlow\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":2,\"cost\":{\"ALERTNESS\":10,\"VIOLENCE\":10},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"COSA FAI NON É UNA DOMANDA\",\"effect\":\"<p>Quick Move <i>(you act first than others when doing this)</i></p><p>Counter Target's own Move and inflict to him the damage it should have done.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.WhatchaDoin\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":\"junkie.character.ToxicJunkie\",\"name\":\"Ago Marcio e Spezzato\",\"effect\":\"<p>Fallback Move <i>(discard another card instead of this)</i></p><p>5 Cut Damage to Target and +5 Toxicity to both you and Target.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.character.PatheticBlade\",\"basicAction\":false,\"characterBound\":true,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{\"VIOLENCE\":15},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"STORDIAMOTI HO DETTO\",\"effect\":\"<p>10 Hit Damage and -10 Alertness to Target.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.StunningBlow\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"Peroni da 66, calda\",\"effect\":\"<p>Alcohol: +5. Equip (single use): next Hit Damage you deal is increased and turned to Cut Damage.</p>\",\"image\":\"no-img.png\",\"sprite\":\"item-sprite.png\",\"bindingName\":\"com.items.RottenBeer\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":true},{\"priority\":1,\"cost\":{\"ALCOHOL\":10},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"Estasi da Coca Etilene\",\"effect\":\"<p>30 Hit Damage to Target.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.GoingBerserk\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":1,\"cost\":{},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"Last Resort: Banzai!!!\",\"effect\":\"<p>10 Hit Damage to Target and 5 Hit Damage to you.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"com.last.Struggle\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":true,\"item\":false},{\"priority\":1,\"cost\":{\"TOXICITY\":5},\"canTarget\":[\"OPPONENT\"],\"boundToCharacter\":null,\"name\":\"Rabbia Psicotica da Meth\",\"effect\":\"<p>15 Hit Damage to Target and 5 Hit Damage to you.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"junkie.MethDrivenRage\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":0,\"cost\":{},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"NON GIOCARE COL COLTELLO NO\",\"effect\":\"<p>Slow Move <i>(others act first when you are doing this)</i></p><p>+10 Violence. For a bit, halve all Cut Damage dealt to you.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.MadeOfStone\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false},{\"priority\":2,\"cost\":{\"VIOLENCE\":20},\"canTarget\":[\"SELF\"],\"boundToCharacter\":null,\"name\":\"GENTILE IL TUO CAREZZO\",\"effect\":\"<p>Quick Move <i>(you act first than others when doing this)</i></p><p>This turn, Hit Damage dealt to you is halved.</p>\",\"image\":\"no-img.png\",\"sprite\":null,\"bindingName\":\"bruiser.CantFeelAnything\",\"basicAction\":false,\"characterBound\":false,\"lastResort\":false,\"item\":false}]}");

  showDeckToggle = false;
  collectionSearchTerm: string;
  collectionCardsShown: Card[];

  @Input() deck: Deck;
  @Output() saveDeck: EventEmitter<Deck> = new EventEmitter<Deck>();
  @Output() exit: EventEmitter<void> = new EventEmitter<void>();

  constructor(public sessionService: SessionService) {
  }

  filterBasicActionCards(): Card[] {
    const filtered = [];
    this.collection.cards.forEach(c => {
      if (c.basicAction)
        filtered.push(c)
    });
    return filtered;
  }

  filterLastResortCards(): Card[] {
    const filtered = [];
    this.collection.cards.forEach(c => {
      if (c.lastResort)
        filtered.push(c)
    });
    return filtered;
  }

  filterCharacterBoundCards(): Card[] {
    const filtered = [];
    this.collection.cards.forEach(c => {
      if (c.characterBound && this.deck.character && c.boundToCharacter === this.deck.character.bindingName)
        filtered.push(c)
    });
    return filtered;
  }

  searchCollectionCards() {
    this.collectionCardsShown = [];
    this.collection.cards.forEach(c => {
      if (JSON.stringify(c).toUpperCase().includes(this.collectionSearchTerm.toUpperCase()))
        this.collectionCardsShown.push(c)
    });
  }

  changeCardShownByToggle() {
    this.collectionCardsShown = [];
    if (this.showDeckToggle)
      this.collectionCardsShown = this.deck.regularCards;
    else this.collection.cards.forEach(c => {
      if (!c.characterBound && !c.lastResort && !c.basicAction)
        this.collectionCardsShown.push(c);
    })
  }

  ngOnInit(): void {
    this.changeCardShownByToggle();
  }

  addOrRemoveFromDeck(card: Card) {
    if (this.showDeckToggle) {
      // delete one from deck
      const index = this.deck.regularCards.findIndex(obj => obj.name === card.name)
      if (index > -1) {
        this.deck.regularCards.splice(index, 1);
      }
    } else this.deck.regularCards.push(card);
  }

  saveDeckAction() {
    this.saveDeck.emit(this.deck);
  }

  exitAction() {
    this.exit.emit();
  }
}
