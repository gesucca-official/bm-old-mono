import {Injectable} from '@angular/core';
import * as SockJS from 'sockjs-client';
import * as Stomp from 'stompjs';
import {environment} from "../../environments/environment";
import {Move} from "../model/move";

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {

  private stompClient: any; // dunno which type this is

  private connected: boolean;

  private subscriptions: Map<string, any[]>;

  constructor() {
    this.subscriptions = new Map<string, any>();
    this.connected = false;
  }

  public connect(): void {
    this.stompClient = Stomp.over(new SockJS(environment.websocketServerEndpoint));
    this.stompClient.connect({}, () => {
      this.connected = true;
    }, (error) => {
      console.log('Connection Failed! errorCallBack -> ' + error);
      console.log('Reattempting in 3 seconds...');
      setTimeout(() => {
        this.connect();
      }, 3000);
    });
  }

  public isConnected(): boolean {
    return this.connected;
  }

  public joinGame(playerId: string, gameType: string, callback: (sdkEvent) => void): void {
    // no need to save this subscription cause it will unsubscribe on completion
    const subscription = this.stompClient.subscribe(WebsocketService.inferJoinGameEndpoint(gameType), (sdkEvent) => {
      subscription.unsubscribe();
      callback(sdkEvent);
    });
    this.stompClient.send('/app/game/' + gameType + '/join', {}, playerId);
  }

  public subToGame(gameId: string,
                   playerId: string,
                   movesCallback: (sdkEvent) => void,
                   illegalMoveCallback: (sdkEvent) => void,
                   gameViewCallback: (sdkEvent) => void
  ): void {
    const gameSubs = new Array<any>();

    // when there's an update about the game get your updated view view
    gameSubs.push(
      this.stompClient.subscribe('/topic/game/' + gameId + '/update', () => {
          this.requestGameView(gameId, playerId)
        }
      ));
    // alert about other players moves
    gameSubs.push(
      this.stompClient.subscribe('/topic/game/' + gameId + '/move', movesCallback)
    );
    // alert player when he select an illegal move
    gameSubs.push(
      this.stompClient.subscribe('/user/queue/player/action/illegalMove', illegalMoveCallback)
    );
    // update game when receiving a new view
    gameSubs.push(
      this.stompClient.subscribe('/user/queue/game/' + gameId + '/' + playerId + '/view', gameViewCallback)
    );

    //save everything to unsubscribe later
    this.subscriptions.set(gameId, gameSubs);
  }

  public requestGameView(gameId: string, playerId: string) {
    this.stompClient.send('/app/game/' + gameId + '/' + playerId + '/view', {});
  }

  // don't know if coupling Move class with this service is good
  public submitMove(move: Move) {
    this.stompClient.send('/app/game/' + move.gameId + '/move', {}, JSON.stringify(move));
  }

  public unsubToGame(gameId: string, playerId: string) {
    this.stompClient.send('/app/game/' + gameId + '/' + playerId + '/leave', {});
    this.subscriptions.get(gameId).forEach(sub => sub.unsubscribe());
    this.subscriptions.delete(gameId);
  }

  private static inferJoinGameEndpoint(gameType: string): string {
    if (gameType.includes('Com'))
      return '/user/queue/game/' + gameType + '/ready';
    else return '/topic/game/' + gameType + '/ready';
  }
}
