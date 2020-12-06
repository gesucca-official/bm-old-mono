import {BrowserModule} from '@angular/platform-browser';
import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {CommonModule} from '@angular/common';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {DebugClientComponent} from "./client-debug/debug-client.component";
import {PhaserClientComponent} from "./client-phaser/phaser-client.component";
import {FormsModule} from "@angular/forms";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatButtonModule} from "@angular/material/button";
import {MatCardModule} from "@angular/material/card";
import {MatSelectModule} from "@angular/material/select";
import {MatInputModule} from "@angular/material/input";
import {MatMenuModule} from "@angular/material/menu";
import {MatDialogModule} from "@angular/material/dialog";
import {CodeDialogComponent} from './client-debug/code-dialog/code-dialog.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {CharacterCardComponent} from './client-debug/character-card/character-card.component';
import {FlexLayoutModule} from "@angular/flex-layout";
import {CardInHandComponent} from './client-debug/card-in-hand/card-in-hand.component';
import {MatExpansionModule} from "@angular/material/expansion";
import {ServerConnectionComponent} from './conn/server-connection/server-connection.component';
import {ChooseGameComponent} from './conn/choose-game/choose-game.component';
import {TestBattleSceneComponent} from './client-phaser/scnenes/battle/test-battle-scene/test-battle-scene.component';
import {HomeComponent} from './home/home.component';
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [
    AppComponent,
    DebugClientComponent,
    PhaserClientComponent,
    CodeDialogComponent,
    CharacterCardComponent,
    CardInHandComponent,
    ServerConnectionComponent,
    ChooseGameComponent,
    TestBattleSceneComponent,
    HomeComponent,
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    FormsModule,
    RouterModule.forRoot([
      {path: '', component: HomeComponent},
      {path: 'client/debug', component: DebugClientComponent},
      {path: 'client/graphic', component: PhaserClientComponent},
      {path: 'client/graphic/test/battle', component: TestBattleSceneComponent},
      {path: '**', redirectTo: '', component: HomeComponent}
    ]),
    FlexLayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatSelectModule,
    MatInputModule,
    MatMenuModule,
    MatDialogModule,
    MatSnackBarModule,
    MatExpansionModule
  ],
  providers: [
    MatSnackBarModule
  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
