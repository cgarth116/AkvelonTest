# AkvelonTest
AkvelonTest

Web API for entering project data into the database (task tracker)
Implemented storage of tasks for the project.


Technologies used:
  - Java Spring Boot(JPA)
  - PostgreSQL
  - Uploaded to the GitHub
  - Using English to write comments and describe classes, fields, etc. d.
  
Non-functional used:
  - Three-level project architecture (data access level, logic level, representation)
  - All using third-party frameworks and packages – publicly available
  - There are a number of tests for logical level

A "Project" is an object that contains:
  1. ID
  2. Name
  3. Project start date
  4. Project completion date
  5. Status(enum: NotStarted, Active, Completed)
  6. Priority

Functionality:
  - Ability to create/view/edit/delete information about projects.
  - Ability to add and remove tasks from a project (one project can contain several tasks).


A "Task" is an instance that contains the fields:
  1. ID
  2. Task name
  3. Description of tasks
  4. Status(enum: ToDo / InProgress / Done)
  5. Priority
  6. projectId
  
Each task is part of only one project.

Functionality(As part of an existing project):
  - Ability to create/view/edit/delete information about tasks.
  - Ability to view all tasks in the project.
  
 
TO RUN THE APPLICATION:
0. Clone project from this repositories.
1. Create and add DataBase in Idea:
  - you need to set access to the Postgress database:
      application-properties must have:
      - username/password for access
      - url (add /akvelon?stringtype=unspecified)
      - add string spring.mvc.pathmatch.matching-strategy = ANT_PATH_MATCHER

      application-properties example:
      
        spring.datasource.driver-class-name=org.postgresql.Driver
        spring.datasource.username=username
        spring.datasource.password=password
        spring.datasource.url=jdbc:postgresql://localhost:5432/akvelon?stringtype=unspecified
        spring.jpa.show-sql=true
        spring.jpa.hibernate.ddl-auto=update

        spring.mvc.hiddenmethod.filter.enabled=true
        spring.mvc.pathmatch.matching-strategy = ANT_PATH_MATCHER
        
  - you have to create tables using script src/main/resources/database/initDB.sql
  - you can create primary table content using script src/main/resources/database/populateDB.sql
  
2. Make sure the build is successful run : 
    mvn clean install
    
3. Run project )

You can start project this way :
  mvn clean package spring-boot:repackage
  java -jar %Path for project%\target\AkvelonTest-0.0.1-SNAPSHOT.jar  
  
SUPPORTED COMMANDS:

  - GET
  	-	%host%/api/project/						- get info about all projects
    -	%host%/api/project/{id}					- get info about project with {id}
    -	%host%/api/project/{id}/task/			- get info about all task in project with {id}
    -	%host%/api/project/{id}/task/{taskId}	- get info about task with {taskId} for project {id}
      
  - POST
 	- %host%/api/project/- add project
 		- ncoming JSON sample:
 			- {
  "name": "project",
  "startDate": "2021-01-24T08:00:00.000+00:00",
  "finishDate": "2022-02-10T16:00:00.000+00:00",
  "status": "Active",
  "priority": 5
}	 
  	- %host%/api/project/{id}/task/	- add task, where {id} - id project 
  		- incoming JSON sample:
  			- {
          "description": "description",
          "name": "name",
          "priority": 52,
          "projectId": 1,
          "status": "Done"
        }
        
  - PUT
  	- %host%/api/project/{id}               - add/change project
  		- incoming JSON sample:
  			- {
          "name": "project",
          "startDate": "2021-01-24T08:00:00.000+00:00",
          "finishDate": "2022-02-10T16:00:00.000+00:00",
          "status": "Active",
          "priority": 5
        } 
  	- %host%/api/project/{id}/task/{taskId} - add/change task
  		-  incoming JSON sample:
  			- {
          "description": "description",
          "name": "name",
          "priority": 52,
          "projectId": 1,
          "status": "Done"
        }    
        
  - DELETE
  	- %host%/api/project/                   - delete all projects and all tasks in them
  	- %host%/api/project/{id}               - delete project with {id} and all tasks in it
  	- %host%/api/project/{id}/task/         - delete all task in project with {id}
  	- %host%/api/project/{id}/task/{taskId} - delete task {taskID} in project with {id}
