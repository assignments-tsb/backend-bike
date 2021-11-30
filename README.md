# Bike Rental API
A microservice for managing bike rentals.

[![Unit Test](https://github.com/assignments-tsb/backend-bike/actions/workflows/unit_test.yml/badge.svg)](https://github.com/assignments-tsb/backend-bike/actions/workflows/unit_test.yml)

[![Unit Test (Native)](https://github.com/assignments-tsb/backend-bike/actions/workflows/unit_test_native.yml/badge.svg)](https://github.com/assignments-tsb/backend-bike/actions/workflows/unit_test_native.yml)

[![GraalVM CE CI](https://github.com/assignments-tsb/backend-bike/actions/workflows/graalvm.yml/badge.svg)](https://github.com/assignments-tsb/backend-bike/actions/workflows/graalvm.yml)

## Documentation (OpenAPI)
![Swagger Docs](docs/swagger_docs.png)

## Package Structure
- api: endpoints exposed to clients
- core: business logic (entity and use cases)
- driver: implementation of core logic dependencies
- framework: configs and framework integrations
![Packege Structure](docs/code_structure.png)

## Running the App Locally
- start the postgres database
```
docker-compose up -d
```
- start the app using gradlew (or use your IDE)
```
./gradlew run
```
![Local Run](docs/running_local.png)

## Database Migration Script
```
src/resources/db/liquibase-changelog.sql
```
![Liquibase](docs/liquibase.png)

## Unit Tests (Spock)
![Unit Test](docs/unit_tests.png)

## Integration Tests (Postman)
![Integration Tests](docs/postman.png)

