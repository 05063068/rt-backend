# movie-api
Core Movie API

## Set up DB tunnels to RDS DB
    Account on jump server required (jump.aws.prod.flixster.com)
    
    Edit your ~/.ssh/config 
    
    Host jump
     User peterl 
     HostName jump.aws.prod.flixster.com
     LocalForward 10001 rds-shared-slave.cfzxgxfxhefu.us-west-2.rds.amazonaws.com:3306

## How to run with Maven

    $ mvn clean spring-boot:run

## How to test with curl

    $ curl -v http://localhost:8080/movie/9
    ...
