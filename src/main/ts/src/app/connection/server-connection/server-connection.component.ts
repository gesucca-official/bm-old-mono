import {Component} from '@angular/core';
import {GameService} from "../../service/game.service";
import {WebsocketService} from "../../service/websocket.service";

@Component({
  selector: 'app-server-connection',
  templateUrl: './server-connection.component.html',
  styleUrls: ['./server-connection.component.css']
})
export class ServerConnectionComponent {

  password: string;

  constructor(public websocketService: WebsocketService,
              public gameService: GameService) {
  }

  logGameService(): void {
    console.log(this.gameService);
  }

}
