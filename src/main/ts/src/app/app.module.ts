import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatSelectModule} from '@angular/material/select';
import {MatSnackBar} from '@angular/material/snack-bar';

@NgModule({
  declarations: [
    AppComponent
  ],
    imports: [
        CommonModule,
        BrowserModule,
        FormsModule,
        BrowserAnimationsModule,
        MatSelectModule
    ],
  providers: [MatSnackBar],
  bootstrap: [AppComponent]
})
export class AppModule {
}
