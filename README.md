# twitter-search

This application is for searching tweets from twitter using Twitter API.

Technologies used: Groovy, Grails, MongoDB, Angular 7 (front-end), AWS (EC2, S3)

Here is the link for the web application:
[Twitter-Search](http://twitter-search-frontend.s3-website-ap-southeast-1.amazonaws.com/#/index)

There are initially 15 records with "test" as keyword. You can add more data by typing in keyword or hashtag in the input field and press search. Maximum of 15 items are displayed initially. If you want to view more tweets related to the keyword you just entered, click 'See More' and it will display 15 more records, and so on. The search results are recorded in the database. The records saved in the database will be loaded if there is no keyword entered. The application will load the tweets from the database first before loading tweets from the twitter api.

*If you have questions regarding the app, you can email me at clgarcia1@up.edu.ph.*
