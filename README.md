#   Tenpo-calculator

Safe REST service for calculating mathematical operations

##  Technologies and standards

1.  Spring Boot 2
2.  Flyway
3.  Docker
3.  Postgres
4.  JWT
5.  springdoc-open-api and swagger

##  Endpoints

*   API Documentation
    -   http://localhost:9214/swagger-ui-custom.html

*   Sing-up
    -   http://localhost:9214/users
        -   POST message
        -   Request:
        
            ``{
                  "username": "newUserTesting",
                  "password": "dsds"
              }``

*   Login (Requires token)
    -   http://localhost:9214/login
        -   POST message
        -   Request:
        
            ``{
                  "username": "newUserTesting",
                  "password": "dsds"
              }``

*   Create operation
    -   http://localhost:9214/operations
        -   POST message
        -   Request:
        
            ``{
                  "type": "SUM",
                  "parameters": [2,3,6]
              }``


*   Get operations
    -   http://localhost:9214/operations
        -   GET message
    

##   Deploy with Docker

*   Deploy the database
    -   `docker run --publish 5433:5432 --name tenpo-db -e POSTGRES_PASSWORD=calculator -e POSTGRES_USER=tenpo -d postgres`

*   Clone the repository:
    -   `git clone https://github.com/mcadac/tenpo-calculator.git`

*   Build the project with maven:
    -   `mvn clean install`
    
*   Build the docker image
    -   `docker build -t tenpo-calculator-1.0.0 .`
    
*   Run the micro-service
    -   ` docker run -e "SPRING_PROFILES_ACTIVE=docker"  -p 9214:9214 -t tenpo-calculator-3`
        