# appdirectchallenge
==========================

## Requirements

nodejs (with npm)
bower
grunt
maven

## Package

To generate a production .war file, run the following commands:

```bash
cd appdirectchallenge
npm install
bower install
mvn -Pprod package
```

## Deploy to heroku

To deploy to heroku, run the following commands:

```bash
grunt deployHeroku
cd deploy/heroku
git push
```
