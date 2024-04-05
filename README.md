# random-joke-api

Opensource Java API for fetching random jokes and filtering only an appropriate example


#### Application information

Name: andom-joke-api
Maintainer: Sayf jawad ([sayf@multicode.nl](mailto:sayf@multicode.nl))

#### Requirements

This project uses:

```
Java 17
Spring Boot 3.x
Maven
```

#### Build Application

```
mvn clean package
```

#### Use dockerhub.com

Create environment variables containing login/password to be able to register your container
images to dockerhub.com

``` 
DOCKER_HUB_USER={your dockerhub username}
DOCKER_HUB_PASS={your dockerhub password}
``` 

#### Use sonarqube
Create environment variables containing url/login/password to be able to connect your project to a
SonarQube/SonarCloud instance. 
``` 
SONAR_URL=http://sonar.host.com
SONAR_LOGIN=login
SONAR_PASSWORD=password
``` 


After building the project run:
```
$ mvn sonar:sonar
```


#### Integration testing using kubernetes
Running automated tests to ensure functional expectations are met and prevent regression

## requisites
Install Docker-Desktop and enable kuberenetes support in settings

## run the integration tests
Make sure docker-desktop is running, build the necessary docker image using 'jib'
``` 
$ cd application
$ mvn jib:dockerBuild
```
go to the integration-test folder
```

$ cd integration-test

```
run the integration test.sh script with install parameter then again with port parameter
```

$ cd integration-test
$ ./test.sh install
$ ./test.sh port

```
your kuberenetes pod should be running. 
Now you can run cucumber tests!

``` 
$ mvn clean install -Pintegration-test
```

#### Application usage

Generate BSN or BankAccount:
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
test by command line
```

# BSN

$ curl -X 'GET' 'http://localhost:8080/api/bsn/generate' -H 'accept: */*'

```

