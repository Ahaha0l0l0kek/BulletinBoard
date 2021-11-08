## Requirements
- Java 15
- PostgreSQL 12+
- Docker

## Build project

Build project:
  ```shell
  $ ./mvnw clean install
  ```

If you want to build without generating typescript models and swagger documentation, just:
  ```shell
  $ ./mvnw clean install -DskipTests
  ```

## Run application

Copy jar file to /docker folder:

  ```shell
   $ cp /backend/target/backend-0.0.1-SNAPSHOT.jar /docker
  ```

Build and the container image using the docker command:
 ```shell
  $ ./docker build -t docker-spring-boot-postgres
  ```

Up the docker-compose:

 ```shell
  $ ./docker-compose build
  $ ./docker-compose up
  ```

## Work with the application

Go to address in browser: http://localhost:8080/swagger-ui/

or

Using Postman with address: http://localhost:8080/