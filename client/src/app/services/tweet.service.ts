import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TweetService {

  constructor(private http: HttpClient) { }

  searchTweets(keyword: string, sinceId: string) {
    return this.http.get("http://localhost:8080/tweets/search", {
      params: {
        keyword: keyword,
        sinceId: sinceId
      }
    })
  }

}
