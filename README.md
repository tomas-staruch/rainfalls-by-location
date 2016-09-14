The application consists of two parts: **back-end** Spring Boot multimodule application and **web-scraper** (not yet implemented).

<img src="https://github.com/tomas-staruch/rainfalls-by-location/blob/master/general_diagram.png" width="400" height="263">

Spring Boot multimodule application has the following structure:
```
.
├── pom.xml
├── application
│   ├── pom.xml
│   └── src
│       └── ...
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

### Gateway module 
Gateway provides RESTful API (JSON format) to query rainfalls service and cominicates with DB.

A client can use only GET HTTP method to communicate with **Gatway** in order to get the data:
```
GET list of all stations:
curl -i http://localhost:8080/rainfalls/stations/

GET information about particular station:
curl -i http://localhost:8080/rainfalls/stations/

GET all measurements of particular station:
curl -i http://localhost:8080/rainfalls/stations/1/measurements
```

### Scheduler module
Scheduler regularly requests **Gateway** and sends requests to **Web-scraper**.

### Web-scraper's responses processor module
The service process the responses from **Web-scraper** and POST them to **Gateway**.
