import {Component, EventEmitter, Output} from '@angular/core';
import {WebsocketService} from "../../service/websocket.service";
import {GameService} from "../../service/game.service";
import {SessionService} from "../../service/session.service";

@Component({
  selector: 'app-choose-game',
  templateUrl: './choose-game.component.html',
  styleUrls: ['./choose-game.component.css']
})
export class ChooseGameComponent {

  @Output() joinGameRequest: EventEmitter<String> = new EventEmitter<String>();

  constructor(public websocketService: WebsocketService,
              public gameService: GameService,
              public sessionService: SessionService) {
  }

  joinGame(game: string) {
    this.joinGameRequest.emit(game);
  }

  logConnectedUsers() {
    console.log(this.sessionService.usersConnected);
  }
}
