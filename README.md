# Safety Campus Shuttle

## Overview
A Java-based application for optimizing night ride requests and dynamic scheduling. Built using the MVC design pattern, it manages shuttle rides and validates RESTful APIs with Postman.

## Tech Stack
- **Backend**: Java, Spring Boot, Hibernate
- **Database**: MySQL
- **Testing**: Postman

## Installation
#1. Clone the repo
   git clone https://github.com/yourusername/safety-campus-shuttle.git
   cd safety-campus-shuttle
   
#2.Install dependencies and build the project:
mvn install

#3.Configure MySQL connection in application.properties:
properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db

#4.Run the application:
mvn spring-boot:run

#5. API Endpoints
POST /ride/request: Request a shuttle ride
GET /shuttle/schedule: Get the dynamic shuttle schedule

