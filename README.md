# Zendesk Intern 2022 Coding Challenge
## Zendesk Ticket Viewer

#### The Application is divided into two logical parts:
- Java Based Backend
- React Based Frontend

### Backend
The backend is a SpringBoot application, to run it you need to have:
- Maven
- JDK v1.8 or higher

Before proceeding to run the application, navigate to the application.properties file in the \src\main\resources directory and enter the username, token and subdomain accordingly.

- To install and run after entering the correct configuration info in application.properties file
```sh
mvn spring-boot:run
```
The above command runs the application at the port 8080
The following URL are available:
> /all

Fetches all records
> /ticket/{id}

Fetch the ticket with the given id
> /tickets

Fetches list of tickets (paginated) with the following query params
> "nextURI" : "", // url for the next request in order(recieved from backend response)
  "prevURI" :"", //  url for the previous request in order(recieved from backend response)
  "next" : true/false depending upon whether its a request for next/previous page
  "pageSize": 25,
  "hasNext": true  .// recieved from backend represents whether there are more results in queue

To check code coverage :
- First build the code using the command
 ```sh
mvn clean install
```
-Navigate to the folder 
> \target\site\jacoco

and open index.html to see the code coverage criteria generated by jacoco plugin



### Frontend
The frontend Ticket Viewer is completely independent of the ZendeskAPI and doesn't need to know anything about its existence. It's a simple react application, that displays paginated tickets in a table with navigation enabled. You can click a row to view its details.
- Nodejs

- To install and run 
```sh
npm install
npm start
```
The above commands first install the required node modules then start the application at localhost port 3000
