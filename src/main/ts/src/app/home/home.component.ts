import {Component, OnInit} from '@angular/core';
import {SponsorComponent} from "./sponsor/sponsor.component";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  constructor(public snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.snackBar.openFromComponent(SponsorComponent, {duration: 3000});
  }

}

