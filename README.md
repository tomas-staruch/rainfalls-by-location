Java application which gather (through web-scraper) precipitation measurements from meteorological stations, store them in DB and publish them through REST API.

The application consists of two parts: **back-end** Spring Boot multimodule application and **web-scraper** (not yet implemented).
The architecture of modules is one step away from microservices architecture. Modules don't depend on each other (however share common module) and communicate through REST API. 

<img src="https://github.com/tomas-staruch/rainfalls-by-location/blob/master/general_diagram.png" width="400" height="263">

Spring Boot multimodule application has the following structure:
```
.
├── pom.xml
├── application
│   ├── pom.xml
│   └── src
│       └── ...
├── common
│   ├── pom.xml
│   └── src
│       └── rainfalls/dto
│       └── rainfalls/security
├── gateway
│   ├── pom.xml
│   └── src
│       └── ...
├── task-scheduler-request-service
│   ├── pom.xml
│   └── src
│       └── ...
└── web-scraper-response-service
    ├── pom.xml
    └── src
        └── ...
```

## Back-end application
### Application module
The main module of the project which main responsiiblity is to agregate all modules to into Spring Boot application.
The module contains Application.java class in order to run Spring Boot application.

### Common module 
Common module provides DTOs for transferring data between modules and common classes for HMAC authorization.

### Gateway module 
Gateway provides RESTful API (JSON format) to query rainfalls service and cominicates with DB.

A client can use only GET HTTP method to communicate with **Gatway** in order to get the data:
```
GET list of all stations:
curl -i http://localhost:8080/rainfalls/stations/

GET information about particular station:
curl -i http://localhost:8080/rainfalls/stations/

GET all measurements of particular station:
curl -i http://localhost:8080/rainfalls/stations/1/precipitations

GET all measurements of particular station in given date (from 00:00:00 to 23:59:59):
curl -i http://localhost:8080/rainfalls/stations/1/precipitations?dateOfMeasurement=2016-09-01

```

### Scheduler module
Scheduler regularly requests **Gateway** and sends requests to **Web-scraper**.

### Web-scraper's responses processor module
The service processes the responses from **Web-scraper** and POST them to **Gateway**.
Every POST request contain HMAC authentication signature to ensure that it is comming from **Web-scraper**. 
