# appdirectchallenge

The 'appdirectchallenge' application is a simple prototype demonstrating the integration of various AppDirect APIs.
The application stack was generated with JHipster https://jhipster.github.io/

## Requirements

- nodejs (with npm)
- bower
- grunt
- maven

## Package

To generate a production .war file, run the following commands:

```bash
cd appdirectchallenge
npm install
bower install
mvn -Pprod package
```

The .war file contains an embedded tomcat. The application can be started as easily as ```java -jar target/appdirectchallenge-0.0.1-SNAPSHOT.war```

## Deployed to Heroku

This demo application is currently deployed on Heroku. Check it out @ https://appdirectchallenge.herokuapp.com

## Supported APIs

### Subscription API

#### Create Notification URL

```
https://appdirectchallenge.herokuapp.com/api/events/create?url={eventUrl}
```

#### Change Notification URL

```
https://appdirectchallenge.herokuapp.com/api/events/update?url={eventUrl}
```

#### Cancel Notification URL

```
https://appdirectchallenge.herokuapp.com/api/events/update?url={eventUrl}
```

### SSO: OpenID

```
https://appdirectchallenge.herokuapp.com/api/login
```
