import {AfterViewInit, Component, EventEmitter, Output, ViewChild} from '@angular/core';
import {WebsocketService} from "../../service/websocket.service";
import {GameService} from "../../service/game.service";
import {SessionService} from "../../service/session.service";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {User} from "../../model/user";
import {MatSort} from "@angular/material/sort";

@Component({
    selector: 'app-choose-game',
    templateUrl: './choose-game.component.html',
    styleUrls: ['./choose-game.component.css']
})
export class ChooseGameComponent implements AfterViewInit {

    @Output() joinGameRequest: EventEmitter<String> = new EventEmitter<String>();

    constructor(public websocketService: WebsocketService,
                public gameService: GameService,
                public sessionService: SessionService) {
    }

    @ViewChild(MatPaginator) paginator: MatPaginator;
    @ViewChild(MatSort) sort: MatSort;

    dataSource = new MatTableDataSource<User>(this.sessionService.usersConnected);

    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
    }

    ngDoCheck() {
        if (this.dataSource.data !== this.sessionService.usersConnected)
            this.dataSource = new MatTableDataSource<User>(this.sessionService.usersConnected);
    }

    joinGame(game: string) {
        this.joinGameRequest.emit(game); // TODO why am I emitting this event and not directly calling the service
    }

    addComPlayerToFfaGame() {
        this.websocketService.addComToGame();
    }

    forceStartFfaGame() {
        this.websocketService.forceStartFfaGame();
    }

}
