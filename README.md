# Gruppe5 - Skillmatcher

Welcome to Skillmatcher! Skillmatcher is an app that helps you find the perfect team members for your projects

# How it works
1. Post your project requirements to our platform
2. Our matching algorithm will find professionals with the skills and experience needed to bring your project to life
3. Connect with potential team members and build your dream team

# Features
* Easy to use interface for posting and finding projects
* Advanced matching algorithm to find the best team members
* Connect with professionals in your field

# Getting started
1. Download the app
2. Create a profile and create a project with your individual requirements or join an existing project based on your skill profile
3. Start connecting with potential team members via the chat 


# Documentation (SQL Schema, Wireframes, API, Steckbrief, Technologies)

## SQL Schema 
![SQL Schema](/docs/skillmatcher-datascheme.png)

The backend uses a MySQL Mariadb schema that connects project, user, and skill tables in a many-to-many relationship.

The schema manages project information, user information, and skill information. 

The join tables allow multiple users to have multiple skills, and multiple projects to have multiple users with different skills.

## API Testing & Documentation - 1 (Postman API Collection)
To help ensure that our API is functioning as expected, we have included a Postman API collection. 

This collection includes all of the endpoints that are required for our application, as well as example requests and responses. 

Please find the collection available for dowload [here.](https://gitlab.lrz.de/mobile-ifi/msp/22WS/gruppe5/-/blob/main/docs/SkillMatcher_API.postman_collection.json)

## API Testing & Documentation - 2 (Swagger UI)

The documentation of the Spring (Kotlin) REST API can be found here (Swagger-UI):

http://msp-ws2223-5.dev.mobile.ifi.lmu.de/swagger-ui.html

To test the API via the SwaggerUI, authentication via JWT is required for all routes except /register and /login.

To access all routes, follow the guide below to add your individual token:

#### 1. Register your individual user (in case not already done via the app) - use /auth/register
![Swagger Tutorial 1](/docs/swagger-docs/swagger2.png)

#### 2. Provide your credentials in order to log in and receive your JWT - use /auth/login
![Swagger Tutorial 2](/docs/swagger-docs/swagger1.png)

#### 3. Copy the obtained JWT token and paste it in the according field after pressing the "Authorize" button
![Swagger Tutorial 3](/docs/swagger-docs/swagger3.png)


## API Testing & Documentation - 3 (Chat)

The getStream.io API was utilized for chat. It's a free SDK for a month, valid until February 16th.

## Wireframes
We have created comprehensive wireframes in the planning phase of our application to help illustrate the user flow and key features.

![Wireframes 1](/docs/wireframes1.png)

![Wireframes 2](/docs/wireframes2.png)

## Group Overview (Steckbrief)
We have also included a Group Overview document which provides an overview of our project including the goals, approach, and features. 

You can view the Group Overview document by clicking [here.](https://gitlab.lrz.de/mobile-ifi/msp/22WS/gruppe5/-/blob/main/docs/Steckbrief_gruppe5.pdf)

## Technologies 
-	Android
    -   minSdk 29
    -   targetSdk 32 
-	Spring v5.3.20
-	Kotlin v1.6.21
-   Java v17 
-   MariaDB (MySQL) v10.5.15
- Compose 1.1
- getStream.io

