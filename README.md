# random-joke-api

Opensource Java API for fetching random jokes and filtering only the shortest and appropriate joke

#### Application information

Name: andom-joke-api
Maintainer: Sayf jawad ([sayf@multicode.nl](mailto:sayf@multicode.nl))

#### Requirements

This project uses:

```
Java 17
Maven
```

#### Build Application

```
mvn clean package
```

## run the integration tests

## Mutation testing

``` 
$ mvn clean install -Pintegration-test
```

#### Application usage

buid the application

``` 
$ mvn clean package
```

run the application

```
$ java -jar application/target/application-1.0-SNAPSHOT.jar
```

test by browser

```

http://localhost:8080/api/swagger-ui/index.html

```
