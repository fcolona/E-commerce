# Ecommerce

## Project Description
This project is an API of an online shop, whose main goals are: store products, manage user security, present a complete payment system

## Main Features
- Database Migrations
- Normalized Database
- Tests
- Integration with Credit Card Payment Gateway
- JWT Authentication and Authorization
- Entity Graphs
- JPA Projections
- Dedicated Sub-Resources Routes
- Cache
- Dedicated Routes for Uploading Images using a Multipart Form
- Swagger Documentation
- Whole Application Dockerized

## Main Technologies:
- Java
- Junit
- Mockito
- Spring Boot (Spring Security, Spring Data JPA, Bean Validation, Java Mail Sender...)
- Mysql
- Stripe
- Redis
- Swagger
- Docker
- Docker-Compose

## How to Set up
- Install [Docker](https://docs.docker.com/get-docker)
- Git clone the project  
  - ```git clone https://github.com/FelipeColona/Ecommerce```
- Assign values to the variables in the .env file
  - This step is optional, but keep in mind that not assigning values to the "Stripe" variables will disable the credit card payment and not assigning values to the "Mail" variables will disable email sending
- Open the terminal in the root folder of the application and run ```docker-compose up```
  - The first initialization may take a while, so be patient :)
  - You'll know it's over when "(JVM running for 0.000)" appear in your screen

## Try It Out!
- Enter (http://localhost:8080/swagger-ui/#/) on your favorite browser
- Scroll down until you see the "login-controller", then click on it  
- Click on "/perform-login"
- Click on the button in the top right corner, labeled "Try it out"
- Replace "string" with the email and the password, respectively 
  - Default admin credentials are:
    - admin@gmail.com
    - 321
  - Dummy user credentials are:
    - user@gmail.com 
    - 123
- Click on "Execute"
- Scroll down and copy the JWT token from the response body
- Scroll to the top of the page and click on the button labeled "Authorize"
- Click on the box and type "Bearer "
  - Yes, with the space
- Paste the token
- Then click on "Authorize"
- Now you can make a request to any route you wish