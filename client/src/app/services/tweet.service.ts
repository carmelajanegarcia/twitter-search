import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TweetService {

  constructor(private http: HttpClient) { }

  searchTweets(keyword: string, sinceId: string) {
    return this.http.get(environment.serverUrl + "/tweets/search", {
      params: {
        keyword: keyword,
        sinceId: sinceId
      }
    })
  }

}
