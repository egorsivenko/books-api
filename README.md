# Books REST API
Simple REST API built with Spring Web and Spring Data JPA using PostgreSQL as a main database and H2 for testing purposes.

## How to run the project
In order for the application to start up, PostgreSQL container must be running. So first of all, execute this command:
```
$ docker compose up -d
```

After that the application can be run using the following command:
```
$ ./mvnw spring-boot:run
```

In addition, use this command to run all project tests (in-memory H2 database in PostgreSQL mode will be launched and utilized):
```
$ ./mvnw clean verify
```
