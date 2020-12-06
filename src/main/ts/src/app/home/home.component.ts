import {Component, EventEmitter, Output} from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  @Output() showTestBattle: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() showDebugClient: EventEmitter<boolean> = new EventEmitter<boolean>();
  @Output() showGraphicClient: EventEmitter<boolean> = new EventEmitter<boolean>();
}

