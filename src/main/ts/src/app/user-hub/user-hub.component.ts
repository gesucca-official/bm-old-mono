import {Component, OnInit} from '@angular/core';
import {WebsocketService} from "../service/websocket.service";

@Component({
  selector: 'app-user-hub',
  templateUrl: './user-hub.component.html',
  styleUrls: ['./user-hub.component.css']
})
export class UserHubComponent implements OnInit {

  constructor(public websocketService: WebsocketService) {
  }

  ngOnInit(): void {
  }

}
