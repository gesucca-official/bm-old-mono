import {Component, Input, OnInit} from '@angular/core';
import {Card} from "../../../model/card";

@Component({
  selector: 'app-collection-card',
  templateUrl: './collection-card.component.html',
  styleUrls: ['./collection-card.component.css']
})
export class CollectionCardComponent implements OnInit {

  @Input() card: Card;

  constructor() {
  }

  ngOnInit(): void {
  }

}
