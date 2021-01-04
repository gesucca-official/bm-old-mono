import {Component, ViewChild} from '@angular/core';
import {WebsocketService} from "../service/websocket.service";
import {GameService} from "../service/game.service";
import {DebugClientComponent} from "../client-debug/debug-client.component";
import {PhaserClientComponent} from "../client-phaser/phaser-client.component";

@Component({
  selector: 'app-user-hub',
  templateUrl: './user-hub.component.html',
  styleUrls: ['./user-hub.component.css']
})
export class UserHubComponent {

  @ViewChild(DebugClientComponent) debugClient: DebugClientComponent;
  @ViewChild(PhaserClientComponent) phaserClient: PhaserClientComponent;

  constructor(public websocketService: WebsocketService, public gameService: GameService) {
  }

  bounceJoinGameEvent($event: string) {
    if (this.gameService.graphicClient)
      this.phaserClient.joinGame($event);
    else this.debugClient.joinGame($event);
  }
}
