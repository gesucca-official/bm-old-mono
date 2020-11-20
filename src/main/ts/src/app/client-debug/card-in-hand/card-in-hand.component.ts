import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Card} from "../../model/card";
import {Move} from "../../model/move";
import {GameService} from "../../service/game.service";

@Component({
  selector: 'app-card-in-hand',
  templateUrl: './card-in-hand.component.html',
  styleUrls: ['./card-in-hand.component.css']
})
export class CardInHandComponent {

  @Input() cardData: Card;
  @Input() targets: string[];
  @Input() chosenTarget: string;

  @Input() discardableCards: string[];
  cardToDiscard: string;

  @Output()
  onPlayThis: EventEmitter<Move> = new EventEmitter<Move>();

  constructor(protected gameService: GameService) {
  }

  logState() {
    console.log(this.cardData);
  }

  playThis() {
    this.onPlayThis.emit({
      playedCardName: this.cardData.name,
      playerId: this.gameService.playerId,
      targetId: this.chosenTarget,
      gameId: this.gameService.gameId,
      choices: this.cardData.characterBound ? {'DISCARD_ONE': this.cardToDiscard} : null
    })
  }
}
