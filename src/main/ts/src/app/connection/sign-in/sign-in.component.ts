import {Component} from '@angular/core';
import {GameService} from "../../service/game.service";
import {WebsocketService} from "../../service/websocket.service";
import {HttpClient} from "@angular/common/http";
import Swal from "sweetalert2";

@Component({
  selector: 'app-server-connection',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.css']
})
export class SignInComponent {

  isLoading: boolean;

  playerId: string;
  password: string;

  constructor(public websocketService: WebsocketService,
              public gameService: GameService,
              private http: HttpClient) {
  }

  async logIn() {
    this.isLoading = true;
    const areCredentialsValid = await this.checkCredentials(this.playerId, this.password);
    if (!areCredentialsValid) {
      Swal.fire(
        'Log In Failed',
        'Please check your credentials',
        'error'
      ).then(() => this.isLoading = false);
    } else {
      this.websocketService.connect(this.playerId, this.password);
      this.isLoading = false;
    }
  }

  private async checkCredentials(username: string, password: string): Promise<boolean> {
    return this.http.post<boolean>('/sign-in/validate', {
      username: username,
      password: password
    }).toPromise()
  }
}
