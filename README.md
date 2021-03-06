# buttongame

* This project is multiplayer button-clicking game. It has frontend made with React, backend made with Maven and Spring Boot using PostgreSQL database.
* Players click button that increments counter from range 1-500. Every 10th, 100th or 500th click gives players more credits.
* Player receives unique personal id from backend that is used to recognize player on later sessions. Id is saved to frontend cookies and if cookie is deletes, progres is lost.
* Players unique identifiers and scores are save to PostgreSQL database.

# Technologies

* React + npm
* Maven + Spring Boot
* PostgreSQL 12

### Frontend
Frontend can be found in "front" -folder.
To launch frontend, type :

```
cd ..\front
npm install
npm start
```

Note: In package.json proxy has been configured to "http://localhost:8080". If server is running in other location, this must be configured correspondingly-

### Backend
Backend is located in "back" -folder.
Launch backend by typing:

```
cd ..\back
mvn spring-boot:run
```
Notice : backend wont start if database is not configured in application.properties. See below.

### Database
Download and install PostgreSQL 12
Start database.
Configure backend to use database:
```
cd ..\back\src\main\resources
nano application.properties
```
Add database configuration to application.properties -file.
```
server.port=8080
spring.jpa.database=POSTGRESQL
spring.datasource.platform=postgres
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=password
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```
