# hiShop - e-commerce

[![Travis Widget]][Travis] [![Coverage Status Widget]][Coverage Status] [![Download Bintray Widget]][Download Bintray] [![Download Docker Widget]][Download Docker]

[Travis]: https://travis-ci.org/NBakaev/hishop
[Travis Widget]: https://travis-ci.org/NBakaev/hishop.svg?branch=master
[Coverage Status]: https://codecov.io/github/NBakaev/hishop?branch=master
[Download Docker]: https://hub.docker.com/r/nbakaev/hishop-api-backend/
[Coverage Status Widget]: https://img.shields.io/codecov/c/github/NBakaev/hishop/master.svg
[Download Bintray]: https://bintray.com/nbakaev/maven/hishop/_latestVersion
[Download Bintray Widget]: https://api.bintray.com/packages/nbakaev/maven/hishop/images/download.svg
[Download Docker Widget]: https://img.shields.io/docker/pulls/nbakaev/hishop-api-backend.svg

## Build & run  
This project uses Java & Spring Boot.
To start server use `mvn package & java -jar target/ru.nbakaev.hishop.jar`.

### Notes for compiling
 - After compiling - test will be automatically run. You can skip tests on packaging step with `mvn package -DskipTests`
 - After executing `mvn package` - file `target/ru.nbakaev.hishop.jar` will be created. This is fat jar - fully executable jar file with embedded tomcat (provided by spring boot). So, you don't need anything dependencies, such as tomcat etc. You only need installed java to run program. 
 - In CI environment variables with API keys are set up to use in tests. But if you want to run tests locally, you must set up Mandatory options.

## Start options
Web server startup port is described in `application.properties`. You can override it in environment or VM options on startup

### Mandatory options
`NOTE:` You must pass to application API keys of services to start application:

  - MongoDB (database)
  - mailgun credentials (send email messages with DKIM and spam filter)
   
For example
```bash
java -jar target/ru.nbakaev.hishop.jar \
    --spring_data_mongodb_uri="mongodb://H7sXKfNiJOoBPByF:X5hQ8Kx9KQZ9165.mongolab.com:39165/hishop" \
    --mailgun_api_key="key-b305ee369ffb69e8b559" \
    --mailgun_url="https://api.mailgun.net/v3/sandbox21473e1b5f33c2e779eb7a46.mailgun.org/messages" \
    --aws_accessKey=AKIAIIF56L35S7A \
    --aws_secretKey=Ez5JO4FjkRlyiki8c3DMTjzl \
    --spring.output.ansi.enabled=ALWAYS
```

Example above use command-line arguments to java program. You can use the same thing with environment variables:
```bash
#!/usr/bin/env bash
export spring_data_mongodb_uri="mongodb://H7sXKfNiJOoBPByF:X5hQ8Kx9KQZ9165.mongolab.com:39165/hishop"
export mailgun_api_key="key-b305ee369ffb69e8b559"
export mailgun_url="https://api.mailgun.net/v3/sandbox21473e1b5f33c2e779eb7a46.mailgun.org/messages"
export aws_accessKey="AKIAIIF56L35S7A"
export aws_secretKey="Ez5JO4FjkRlyiki8c3DMTjzl"
export spring.output.ansi.enabled=ALWAYS

java -jar target/ru.nbakaev.hishop.jar
````

Use any method that is more preferred to you or combine them.

### Docker

```bash
docker run -d -p 5555:5555 \
    -e "spring_data_mongodb_uri=mongodb://H7sXKfNiJOoBPByF:X5hQ8Kx9KQZ9165.mongolab.com:39165/hishop" \
    -e "mailgun_api_key=key-b305ee369ffb69e8b559" \
    -e "mailgun_url=https://api.mailgun.net/v3/sandbox21473e1b5f3344c2950ac2e779eb7a46.mailgun.org/messages" \
    -e "aws_accessKey=AKIAIIF56L35S7A" \
    -e "aws_secretKey=Ez5JO4FjkRlyiki8c3DMTjzl" \
    -e "spring.output.ansi.enabled=ALWAYS" \
    --name hishop1 nbakaev/hishop-api-backend:0.2.2-RELEASE_build.127763392
```

Docker notes:

 - you can not pass `--name hishop1`. This is unic container name. Instead container id will used provided. 
 - you can replace `nbakaev/hishop-api-backend:0.2.2-RELEASE_build.127763392` to another version. All [version are here on docker hub](https://hub.docker.com/r/nbakaev/hishop-api-backend/tags/)
 - `-p 5555:5555` by default docker create fully independent network environment with ports that are not hosts (see linux [cgroups](https://en.wikipedia.org/wiki/Cgroups)). 
`-p 5555:5555` option map container port 5555 to host than make available service outside.
 - you can see running containers with `docker ps`. See logs of each container with ` docker logs -f hishop1` (replace hishop1 with container id or name)

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
 - `deploy/publish.sh` CI script for deploy build to binary repository

## Notes:

 - [Бакаев Никита. Текст курсовой работы в Google Docs](https://docs.google.com/document/d/1QhCjvqiGACP9OQohqe5BMcsHoedugAchMLozXtCoW64/edit?usp=sharing)
 - [Бакаев Никита. Презентация курсовой работы в Google Docs](https://docs.google.com/presentation/d/1UFX-xWNX8DwdD1uIw4yO7Gc8Snupf74qfb5MSyjcgJU/edit?usp=sharing)

### Основные адреса
В случае запуска на локальной машине вместо `https://s2.nbakaev.ru` нужно использовать `http://localhost:5555/`

 - `https://s2.nbakaev.ru`
 - `https://s2.nbakaev.ru` - test application UI here (index)
 - `https://s2.nbakaev.ru/api/v1/` - REST API Root
 - `https://s2.nbakaev.ru/swagger-ui.html` - Swagger UI - all REST endpoints documentation
 - `https://s2.nbakaev.ru/v2/api-docs` - Swagger endpoint. You can import all REST api endpoints with methods, name, params etc 
with any tool that support swagger format, for example, with `Postman`

### Continuous Integration
For CI travis-ci.com for OSS is used.
Every commit in VCS triggers build on CI and on success - deploy jar file to binary repository.

Note: every build with unique maven version is deployed to `https://bintray.com/nbakaev/maven/hishop/view`. 
Every resulted maven build version contains version in `pom.xml` and CI build number (see `deploy/publish.sh`).

### Deployments from travis details
 -  https://docs.travis-ci.com/user/deployment/bintray
 -  https://docs.travis-ci.com/user/encryption-keys/
 - `travis encrypt key=value --add env.global`
 
## Code snippets

### Drop all mongodb indexes in shell
```js
db.getCollectionNames().forEach(function(collName) {
  db.runCommand({dropIndexes: collName, index: "*"});
});
```

```bash
git update-index --chmod=+x publish.sh
```

### License
Copyright © 2016 Nikita Bakaev. Licensed under the Apache License.