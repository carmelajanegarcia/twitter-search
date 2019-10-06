package twitter.search

class Tweet {

    Long id
    String idString
    String text
    Date createdAt
    User user

    static embedded = ['user']

    static mapWith = "mongo"

    static mapping = {
        id generator: 'assigned'
    }

    static constraints = {
    }
}
