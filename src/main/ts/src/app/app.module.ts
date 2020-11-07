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

@NgModule({
  declarations: [
    AppComponent,
    DebugClientComponent,
    PhaserClientComponent
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
    MatToolbarModule,
    MatButtonModule
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {
}
