import {Component, OnInit} from '@angular/core';
import {NavService} from '../nav/nav.service';
import {Route, Router} from '@angular/router';

import { environment } from '../../environments/environment';
import { TweetService } from '../services/tweet.service';
import { Tweet } from '../models/tweet';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  controllers: Array<any>;
  serverUrl: string;

  tweets: Tweet[]
  keyword = ''
  sinceId = '0'

  searchLoading = false;
  loading = false;

  constructor(private navService: NavService, 
              private router: Router,
              private tweetService: TweetService) { }

  ngOnInit(): void {
    this.serverUrl = environment.serverUrl;
    this.tweetService.searchTweets(this.keyword, this.sinceId).subscribe((result: any) => {
      this.tweets = result;
    });
  }

  search() {
    this.searchLoading = true
    this.tweetService.searchTweets(this.keyword, this.sinceId).subscribe((result: any) => {
      this.tweets = result;
      this.searchLoading = false
    });
  }

  showMore() {
    this.loading = true;
    this.sinceId = this.tweets[this.tweets.length-1].idString
    this.tweetService.searchTweets(this.keyword, this.sinceId).subscribe((result: any) => {
      this.tweets = this.tweets.concat(result);
      this.loading = false;
    });
  }
}
