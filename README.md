# twitter-search

This application is for searching tweets from twitter using Twitter API.

Technologies used: Groovy, Grails, MongoDB, Angular 7 (front-end), AWS (EC2, S3)

Here is the link for the web application:
[Twitter-Search](http://twitter-search-frontend.s3-website-ap-southeast-1.amazonaws.com/#/index)

There are initially 15 records with "test" as keyword. You can add more data by typing in keyword or hashtag in the input field and press search. Maximum of 15 items are displayed initially. If you want to view more tweets related to the keyword you just entered, click 'See More' and it will display 15 more records, and so on. The search results are recorded in the database. The records saved in the database will be loaded if there is no keyword entered. The application will load the tweets from the database first before loading tweets from the twitter api.

## How to run the app locally:
### Prerequisites
#### Initialize Database

```js
use twitter-search

db.createUser({
  user: 'twitter-api',
  pwd: 'password123',
  roles: [
    { role: 'readWrite', db: 'twitter-search' }
  ]
})

db.createCollection('tweets')
```

Note: Make sure MongoDb is running in Port 27017 as the project is configured to connect to this port

### 1. Clone the repo

```bash
git clone https://github.com/carmelajanegarcia/twitter-search.git
```

### 2. Run the app
```bash
cd twitter-search

./gradlew bootRun --parallel
```

### 3. Open the app at http://localhost:4200/
Enter keyword and press search to see results.


*If you have questions regarding the app, you can email me at clgarcia1@up.edu.ph.*
