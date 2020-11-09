import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {RouterModule} from '@angular/router';
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
import {DragDropModule} from '@angular/cdk/drag-drop';
import {CardInHandComponent} from './client-debug/card-in-hand/card-in-hand.component';

@NgModule({
  declarations: [
    AppComponent,
    DebugClientComponent,
    PhaserClientComponent,
    CodeDialogComponent,
    CharacterCardComponent,
    CardInHandComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule.forRoot([
      {path: '', component: AppComponent},
      {path: 'client/debug', component: DebugClientComponent},
      {path: 'client/phaser', component: PhaserClientComponent},
      {path: '**', redirectTo: '', component: AppComponent}
    ]),
    FormsModule,
    DragDropModule,
    FlexLayoutModule,
    MatToolbarModule,
    MatButtonModule,
    MatCardModule,
    MatSelectModule,
    MatInputModule,
    MatMenuModule,
    MatDialogModule,
    MatSnackBarModule
  ],
  providers: [
    MatSnackBarModule
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
