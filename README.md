# Skillmatcher Project Management Android App 💼📱

Skillmatcher is an app that helps you find the perfect team members for your projects

## How it works💡
1. Post your project requirements to our platform
2. Our matching algorithm will find professionals with the skills and experience needed to bring your project to life
3. Connect with potential team members and build your dream team

## Features 🌟
* Easy to use interface for posting and finding projects
* Advanced matching algorithm to find the best team members
* Connect with professionals in your field

## Getting started 🚀
1. Download the app
2. Create a profile and create a project with your individual requirements or join an existing project based on your skill profile
3. Start connecting with potential team members via the chat 


## Documentation 📚🖼🛠

### SQL Schema 
![SQL Schema](/docs/skillmatcher-datascheme.png)

The backend uses a MySQL MariaDB schema that connects project, user, and skill tables in a many-to-many relationship.

The schema manages project information, user information, and skill information. 

The join tables allow multiple users to have multiple skills, and multiple projects to have multiple users with different skills.

---
### API Testing & Documentation (Postman API Collection)
To help ensure that our API is functioning as expected, we have included a Postman API collection. 

This collection includes all of the endpoints that are required for our application, as well as example requests and responses. 

---
### Wireframes
We have created comprehensive wireframes in the planning phase of our application to help illustrate the user flow and key features.

![Wireframes 1](/docs/wireframes1.png)

![Wireframes 2](/docs/wireframes2.png)

---
### Technologies 
-	Android
    -   minSdk 29
    -   targetSdk 32 
-	Spring v5.3.20
-	Kotlin v1.6.21
-   Java v17 
-   MariaDB (MySQL) v10.5.15
- Compose 1.1
- getStream.io

