# hiShop - tk.hishopapp

This project uses Java & Spring Boot.
To start server use `mvn package & java -jar target/web-0.0.1-SNAPSHOT.jar`. 
This will start web server on port which is described in `application.properties` or environment or VM options on startup

## Components:

### Backend:
 - Java 8
 - Spring 4 & Spring Boot 1.3
 - MongoDB 3 (NoSQL database)

### Frontend:
 - AngularJS
 - All AngularJS (`src/main/resources/static`)

#### Additional Files:
 - `Procfile` - Heroku (PaaS) configuration 
 - `docker-compose.yml` - docker compose (docker orchestration)

### Notes:

 - `https://docs.google.com/document/d/1QhCjvqiGACP9OQohqe5BMcsHoedugAchMLozXtCoW64/edit?usp=sharing` - Бакаев Никита. Текст курсовой работы в Google Docs

### Основные адреса
В случае запуска на локальной машине вместо `http://hishop.herokuapp.com` нужно использовать `http://localhost:5555/`

 - `http://hishop.herokuapp.com` - test appication here (index)
 - `http://hishop.herokuapp.com/api/v1/` - REST API Root
 - `http://hishop.herokuapp.com/swagger-ui.html` - all REST endpoints documentation
