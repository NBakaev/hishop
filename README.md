# hiShop

This project uses Java & Spring Boot.
To start server use `mvn package & java -jar target/web-0.0.1-SNAPSHOT.jar`. 
This will start web server on port which is described in `application.properties`

Components:
 - Java 8
 - Spring 4 & Spring Boot 1.3
 - MongoDB 3 (NoSQL database)

Files:

 - `Procfile` - Heroku (PaaS) configuration 

Notes:

Вместо `http://hishop.herokuapp.com` можно использовать `http://localhost:5555/` разрабатывая на локальной машине

 - `http://hishop.herokuapp.com` - test appication here (index)
 - `http://hishop.herokuapp.com/api/v1/` - REST API Root
 - `http://hishop.herokuapp.com/swagger-ui.html` - all REST endpoints
