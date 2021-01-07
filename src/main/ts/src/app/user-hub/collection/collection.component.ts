import {Component, OnInit} from '@angular/core';
import {SessionService} from "../../service/session.service";

@Component({
  selector: 'app-collection',
  templateUrl: './collection.component.html',
  styleUrls: ['./collection.component.css']
})
export class CollectionComponent implements OnInit {

  constructor(public sessionService: SessionService) {
  }

  ngOnInit(): void {
  }

}
