package twitter.search

import grails.core.GrailsApplication
import grails.gorm.transactions.Transactional
import twitter4j.Query
import twitter4j.QueryResult
import twitter4j.Status
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.conf.ConfigurationBuilder

@Transactional
class TweetService {

    int MAX_RESULT = 15;

    GrailsApplication grailsApplication

    def search(String keyword, Long maxId, Long sinceId) {
        List<Tweet> tweets = new ArrayList<>()
        String regexKeyword = ".*" + keyword + ".*";

        // i used collection.find instead of findBy/criteria of grails because it has issues fetching tweets with (\n)

        if(sinceId != null) {
            // find tweet by keyword from username, name or text and greater than sinceId
            tweets = Tweet.collection.find([
                    $and: [
                            [$or:
                                     [
                                             [ "user.username": [
                                                     $regex  : regexKeyword,
                                                     $options: 'si'
                                             ]],
                                             [ "user.name"    : [
                                                     $regex  : regexKeyword,
                                                     $options: 'si'
                                             ]],
                                             [ "text": [
                                                     $regex  : regexKeyword,
                                                     $options: 'si'
                                             ]],
                                     ]],
                            [_id: [
                                    $gt: sinceId
                            ]]
                    ]
            ]).limit(MAX_RESULT).collect{it as Tweet}.sort { it.id }
        } else if(maxId != null) {
            // find tweet by keyword from username, name or text and less than maxId
            tweets = Tweet.collection.find([
                    $and: [
                            [$or:
                                     [
                                             [ "user.username": [
                                                     $regex  : regexKeyword,
                                                     $options: 'si'
                                             ]],
                                             [ "user.name"    : [
                                                     $regex  : regexKeyword,
                                                     $options: 'si'
                                             ]],
                                             [ "text": [
                                                     $regex  : regexKeyword,
                                                     $options: 'si'
                                             ]],
                                     ]],
                            [_id: [
                                    $lt: maxId
                            ]]
                    ]
            ]).limit(MAX_RESULT).collect{it as Tweet}.sort { it.id }
        } else {
            // find tweet by keyword from username, name or text
            tweets = Tweet.collection.find([
                $or:
                    [
                       [ "user.username": [
                                $regex  : regexKeyword,
                                $options: 'si'
                        ]],
                       [ "user.name"    : [
                                $regex  : regexKeyword,
                                $options: 'si'
                        ]],
                       [ "text": [
                               $regex  : regexKeyword,
                               $options: 'si'
                       ]],
                    ]
            ]).limit(MAX_RESULT).collect {it as Tweet}.sort { it.id }
        }

        // if result is less than the max result and the keyword is not empty, it will continue to search through the api
        if(tweets.size() < MAX_RESULT && !keyword.isEmpty()) {
            List<Tweet> apiTweets = searchByAPI(keyword, maxId, sinceId, MAX_RESULT - tweets.size());
            tweets.addAll(apiTweets)
        }
        tweets
    }

    def searchByAPI(String keyword, Long maxId, Long sinceId, int count) {

        // configure twitter access
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setTweetModeExtended(true)
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(grailsApplication.config.getProperty("twitter_api.CONSUMER_KEY"))
                .setOAuthConsumerSecret(grailsApplication.config.getProperty("twitter_api.CONSUMER_SECRET"))
                .setOAuthAccessToken(grailsApplication.config.getProperty("twitter_api.ACCESS_TOKEN"))
                .setOAuthAccessTokenSecret(grailsApplication.config.getProperty("twitter_api.ACCESS_TOKEN_SECRET"));
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        Query query = new Query(keyword);
        query.count(MAX_RESULT)
        if(maxId != null) {
            query.maxId(maxId - 1)
        } else if(sinceId != null) {
            query.sinceId(sinceId + 1)
        }
        QueryResult result = twitter.search(query)
        List<Tweet> tweets = new ArrayList<>()
        for (Status status : result.getTweets()) {

            // if tweet already exists in the database
            if(Tweet.findById(status.id) != null)
                continue

            // add tweet to database
            Tweet tweet = new Tweet()
            tweet.id = status.id
            tweet.idString = status.id?.toString()
            tweet.text = status.retweetedStatus?status.retweetedStatus.text:status.text
            tweet.createdAt = status.createdAt
            User user = User.findById(status.user?.id)
            if(user == null) {
                user = new User()
            }
            user.id = status.user?.id
            user.name = status.user?.name
            user.username = status.user?.screenName
            user.profileUrl = status.user?.profileImageURL
            tweet.user = user
            tweet.insert(failOnError: true)
            tweets.add(tweet)

            // if max number of tweet data is already reached, return tweets
            if(tweets.size() == count)
                break
        }
        tweets
    }

}
