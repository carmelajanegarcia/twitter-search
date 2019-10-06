package twitter.search

class User {

    Long id
    String name
    String username
    String profileUrl

    static mapWith = "mongo"

    static mapping = {
        id generator: 'assigned'
    }

    static constraints = {
    }
}
