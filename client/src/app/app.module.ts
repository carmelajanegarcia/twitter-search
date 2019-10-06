import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {IndexComponent} from "./index/index.component";
import {NavComponent} from "./nav/nav.component";
import {NavService} from "./nav/nav.service";
import {NgbModule, NgbPaginationModule} from "@ng-bootstrap/ng-bootstrap";
import {HttpClientModule} from "@angular/common/http";
import {HashLocationStrategy, LocationStrategy} from "@angular/common";
import { TweetComponent } from './tweet/tweet.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AppComponent,
    IndexComponent,
    NavComponent,
    TweetComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    NgbModule,
    FormsModule,
    NgbPaginationModule
  ],
  providers: [{provide: LocationStrategy, useClass: HashLocationStrategy}, NavService],
  bootstrap: [AppComponent]
})
export class AppModule { }
