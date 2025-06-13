## BulletinBoard

A simplified marketplace platform, demonstrating skills in Backend, ML and DevOps.


### Requirements
- Java 15
- Spring Boot
- Python 3.10
- PostgreSQL 12+
- Docker

### Build project

Build project:
  ```shell
  $ ./mvnw clean install
  ```

If you want to build without generating typescript models and swagger documentation, just:
  ```shell
  $ ./mvnw clean install -DskipTests
  ```

### Run application

Copy jar file to /docker folder:

  ```shell
   $ cp /backend/target/backend-0.0.1-SNAPSHOT.jar /docker
  ```

Build and the container images using the docker commands:
 ```shell
  $ ./docker build -t docker-spring-boot-postgres
  $ ./recommender_service build -t recommendation-service
  ```

Up the docker-compose:

 ```shell
  $ ./docker-compose build
  $ ./docker-compose up
  ```

## Work with the application

Go to address in browser: 

- http://localhost:8080/api/... — REST API Spring
- http://localhost:8000/recommendations/... — FastAPI сервис рекомендаций

or

Using Postman with address: http://localhost:8080/
