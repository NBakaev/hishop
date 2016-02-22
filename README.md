# hiShop - tk.hishopapp

[![Build Status](https://travis-ci.org/NBakaev/hishop.png?branch=master)](https://travis-ci.org/NBakaev/hishop)   
[![Coverage Status](https://img.shields.io/codecov/c/github/NBakaev/hishop/master.svg)](https://codecov.io/github/NBakaev/hishop?branch=master)
  
This project uses Java & Spring Boot.
To start server use `mvn package & java -jar target/web-0.1.0-SNAPSHOT.jar`. 
This will start web server on port which is described in `application.properties` or environment or VM options on startup

## Start options

#### Program arguments

 - `--spring.profiles.active=development` - start with embedded(running on local machine; fully clean on every startup) MongoDB database
 - `--spring.profiles.active=production` - by default or leave `spring.profiles.active` blank - start with remote MongoDB database
 - `-Dhttp.proxyHost=127.0.0.1
 -Dhttp.proxyPort=9999
 -Dhttps.proxyHost=127.0.0.1
 -Dhttps.proxyPort=9999
 -Djava.net.useSystemProxies=true
` - just common argument to use local proxy for JVM (port 9999)

## Components:

### Backend:
 - Java 8
 - Spring 4 & Spring Boot 1.3
 - MongoDB 3 (NoSQL database)

### Frontend:
 - AngularJS - (`src/main/resources/static`)

#### Additional Files:
 - `Procfile` - Heroku (PaaS) configuration 
 - `docker/docker-compose.yml` - docker compose (docker orchestration)
 - `docker` folder - images for Docker

## Notes:

 - `https://docs.google.com/document/d/1QhCjvqiGACP9OQohqe5BMcsHoedugAchMLozXtCoW64/edit?usp=sharing` - Бакаев Никита. Текст курсовой работы в Google Docs

### Основные адреса
В случае запуска на локальной машине вместо `http://hishop.herokuapp.com` или dns алиас `http://www.hishopapp.tk`(не работает) нужно использовать `http://localhost:5555/`

 - `http://hishop.herokuapp.com` - test appication here (index)
 - `http://hishop.herokuapp.com/api/v1/` - REST API Root
 - `http://hishop.herokuapp.com/swagger-ui.html` - all REST endpoints documentation
