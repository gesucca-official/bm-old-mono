import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-card-in-hand',
  templateUrl: './card-in-hand.component.html',
  styleUrls: ['./card-in-hand.component.css']
})
export class CardInHandComponent {

  @Input() cardData: any;
  @Input() targets: string[];
  @Input() chosenTarget: string;

  @Output()
  onPlayThis: EventEmitter<any> = new EventEmitter<any>();

  logState() {
    console.log(this.cardData);
  }

  playThis() {
    this.onPlayThis.emit({
      cardName: this.cardData.name,
      target: this.chosenTarget
    })
  }
}
