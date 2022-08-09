# Time register application
Application for register working time 

## Prerequisite
- Java 18
- Gradle 7.4.1
- Docker 20.10.14 or later

## How to start
On local, export these values

```
export DATABASE_URL_CUSTOMIZE=$DATABASE_URL
export USER_NAME=$DB_USER_NAME
export USER_PASSWORD=$DB_USER_PASSWORD
export JWT_SECRET_KEY=$JWT_SECRET_KEY
```
After that run

```./gradlew bootRun```

## Swagger REST API documentation
Start application and access http://localhost:8080/swagger-ui/#/

