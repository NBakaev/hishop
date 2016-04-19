# hiShop - e-commerce

[![Travis Widget]][Travis] [![Coverage Status Widget]][Coverage Status]

[Travis]: https://travis-ci.org/NBakaev/hishop
[Travis Widget]: https://travis-ci.org/NBakaev/hishop.svg?branch=master
[Coverage Status]: https://codecov.io/github/NBakaev/hishop?branch=master
[Coverage Status Widget]: https://img.shields.io/codecov/c/github/NBakaev/hishop/master.svg

## Build & run  
This project uses Java & Spring Boot.
To start server use `mvn package & java -jar target/tk.hishop.jar`.

### Notes for compiling
 - After compiling - test will be automatically run. You can skip tests on packaging step with `mvn package -DskipTests`
 - After executing `mvn package` - file `target/tk.hishop.jar` will be created. This is fat jar - fully executable jar file with embedded tomcat (provided by spring boot). So, you don't need anything dependencies, such as tomcat etc. You only need installed java to run program. 

## Start options
Web server startup port is described in `application.properties` or environment or VM options on startup

#### Program arguments

 - `--spring.output.ansi.enabled=ALWAYS` to enable color logging
 - `-Dhttp.proxyHost=127.0.0.1
 -Dhttp.proxyPort=9999
 -Dhttps.proxyHost=127.0.0.1
 -Dhttps.proxyPort=9999
 -Djava.net.useSystemProxies=true
` - just common argument to use local proxy for JVM (port 9999)

##### Profiles
 - `--spring.profiles.active=production` - by default; start with remote MongoDB database
 - `--spring.profiles.active=development` - start with embedded(running on local machine; fully clean on every startup) MongoDB database. Used primary for tests.

## Components:

### Backend:
 - Java 8
 - Spring 4 & Spring Boot 1.3
 - MongoDB 3 (NoSQL database)

#### Authentication & authorization
 Some REST endpoints requires `ROLE_ADMIN`. Users roles are in `userAccounts.role` filed in collection in db.
  
Default user with admin username `ya@nbakaev.ru`. Password: `admin`. To test, for example `GET /api/v1/users` which will response all users requres admin role. 

Password are stored as hash with `bcrypt` (better than md5/sha512)

Roles list
 - ROLE_USER - default role for every registered user
 - ROLE_ADMIN - admin account

### Frontend:
 - AngularJS - (`src/main/resources/static`)

#### Additional Files:
 - `Procfile` - Heroku (PaaS) configuration 
 - `docker/docker-compose.yml` - docker compose (docker orchestration)
 - `docker/` folder - images for Docker
 - `.travis.yml` CI server settings for notifications via Slack and test coverage reports
 - `demo/` folder with some demo data (images, excel etc)

## Notes:

 - `https://docs.google.com/document/d/1QhCjvqiGACP9OQohqe5BMcsHoedugAchMLozXtCoW64/edit?usp=sharing` - Бакаев Никита. Текст курсовой работы в Google Docs

### Основные адреса
В случае запуска на локальной машине вместо `http://hishop.herokuapp.com` нужно использовать `http://localhost:5555/`

 - `http://hishop.herokuapp.com` - test application UI here (index)
 - `http://hishop.herokuapp.com/api/v1/` - REST API Root
 - `http://hishop.herokuapp.com/swagger-ui.html` - Swagger UI - all REST endpoints documentation
 - `http://hishop.herokuapp.com/v2/api-docs` - Swagger endpoint. You can import all REST api endpoints with methods, name, params etc 
with any tool that support swagger format, for example, with `Postman`

### Deployments from travis details
 -  https://docs.travis-ci.com/user/deployment/bintray
 -  https://docs.travis-ci.com/user/encryption-keys/
 -  https://bintray.com/nbakaev/generic/hishop/view
 
## Code snippets

### Drop all mongodb indexes in shell
```js
db.getCollectionNames().forEach(function(collName) {
  db.runCommand({dropIndexes: collName, index: "*"});
});
```