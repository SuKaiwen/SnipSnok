# SnipSnok: ELEC5619 Group Project

SnipSnok is a social network that connects content creators (i.e. artists, musicians, graphic designers, etc) with fans and the general public in a new, unique and fun way. This service combines the purchasing features of e-commerce websites such as Patreon and Etsy with the fast-paced nature of social networks such as Twitter and TikTok. In building a social network around the idea of e-commerce and trade, we envisage that SnipSnok will allow for content creators to not just sell their work and make money but instead also better promote and share their work more widely with a range of different audiences and fans. This may, in turn, allow for fans to find out about content creators that they might not have been interested about; a win-win for both sides.

### Contributors

The effort and amount of all contributions to this repository were roughly equal by all contributors, who are listed below, ordered alphabetically by last name. 

Edwin Chau 

Daniel O’Dea

Chris Pidd

Sophia Polito 

Niravit Theng 

Kevin Su

## How to run

First start the backend, then start the frontend. 

### Backend

```
cd snipsnok
mvn install
java -jar target/snipsnok-1.3.6.jar
```

### Frontend

```
cd frontend
npm install
npm start
```

## How to contribute

For each change, make a new branch with the format `{your-name}/{feature-name}`

Commits that break existing features should be discussed with the team members who created or primarily worked on those features. 

For each change, a pull request should be created to merge that feature branch with master. 

We should all be running all tests and writing new tests for all changes. The process of that is described below.

## How to test

```
cd snipsnok
mvn test
```

If any tests fail, contact the owner of the test or the owner of the functionality that the test is tied to. 

## More information

For more information, check out the project wiki in GitHub. 
