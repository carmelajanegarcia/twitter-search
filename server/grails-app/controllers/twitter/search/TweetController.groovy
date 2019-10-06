package twitter.search

import grails.rest.RestfulController

class TweetController extends RestfulController{
	static responseFormats = ['json', 'xml']

    def tweetService

    TweetController() {
        super(Tweet)
    }

    def index() {
        respond Tweet.list()
    }

    def search() {
        respond tweetService.search(params.keyword, params.long('maxId'), params.long('sinceId'))
    }
}
