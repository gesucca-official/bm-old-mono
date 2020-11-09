import {Component, Input, OnInit} from '@angular/core';

@Component({
  selector: 'app-character-card',
  templateUrl: './character-card.component.html',
  styleUrls: ['./character-card.component.css']
})
export class CharacterCardComponent implements OnInit {

  @Input() playerState: any;
  @Input() isPlayer: boolean;

  constructor() {
  }

  ngOnInit(): void {
  }

  logState() {
    console.log(this.playerState);
  }

  get health(): number {
    return this.playerState.character.resources['HEALTH'];
  }

  get alertness(): number {
    return this.playerState.character.resources['ALERTNESS'];
  }

  get otherResources(): any {
    const res = JSON.parse(JSON.stringify(this.playerState.character.resources));
    delete res['HEALTH']
    delete res['ALERTNESS']
    return res;
  }

}
