[JAVA_BADGE]:https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white
[SPRING_BADGE]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[MYSQL_BAGDE]: https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white
[MAVEN]: https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white

![java][JAVA_BADGE]
![spring][SPRING_BADGE]
![MySQL][MYSQL_BAGDE]
![Apache Maven][MAVEN]

<h1 align="center" style="font-weight: bold;">ManyEvents API 💻</h1>

<p align="center">
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> •
 <a href="#colab">Collaborators</a> •
 <a href="#contribute">Contribute</a>
</p>

<p align="center">
  <b>ManyEvents is an API that enables the registration of online events, as well as the enrollment of people with or without referrals. It also includes features that allow users to check how many people a user has referred and display a ranking of the top three referrers.</b>
</p>

<h2 id="started">🚀 Getting started</h2>

<h3>Prerequisites</h3>


- [Java 21+](https://www.oracle.com/br/java/technologies/downloads/)
- [Docker](https://www.docker.com/)
- <a href="#docker-compose">MySQL image</a>
- [Maven](https://maven.apache.org/download.cgi)

<h3>Cloning</h3>

```bash
git clone https://github.com/moments342/ManyEvents_API
```
<h3 id="docker-compose"> Uploading the MySQL image</h3>

Create a `docker-compose.yml` with the same information as below.
```yaml
services:
  mysql:
    image: mysql:8.4
    restart: always
    container_name: mysql-nlw
    environment:
      - MYSQL_ROOT_PASSWORD=mysql
    ports: 
      - '3306:3306'
    networks: 
      - nlw-network
networks:
  nlw-network:
    driver: bridge
```

Then, run this in the terminal from the directory where the file is located:
```yaml
docker-compose up -d
```
<h3> Environment Variables</h3>

Use the example below as a reference to configure your `application.properties` file.

```yaml
spring.application.name=events
spring.datasource.username=root
spring.datasource.password=mysql
spring.datasource.url=jdbc:mysql://localhost:3306/db_events
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect

spring.jpa.show-sql = true
```

<h3>Starting</h3>

Open the directory where the project is located and run this in the terminal:

```bash
mvn clean install
mvn spring-boot:run
``````


<h2 id="routes">📍 API Endpoints</h2>

Here you can list the main routes of your API, and what are their expected request bodies.
​
| route               | description                                          
|----------------------|-----------------------------------------------------
| <kbd>POST /events</kbd>     | Register a new event in the database. [request details](#post-events-detail)
| <kbd>GET /events</kbd>     | Show all events. [response details](#get-all-events)
| <kbd>GET /events/{prettyName}</kbd>     | Search for the event by prettyName [response details](#get-event-by-prettyName)
| <kbd>POST /subscription/{prettyName}</kbd>     | Subscribing to an event without a referral. [request details](#post-common-subscription)
| <kbd>POST /subscription/{prettyName}/{userId}</kbd>     | Subscribing to an event with a referral. [request details](#post-indication-subscription)
| <kbd>GET /subscription/{prettyName}/ranking</kbd>     | Generate a ranking of subscriptions based on the prettyName of an event. [response details](#get-ranking)
| <kbd>GET /subscription/{prettyName}/ranking/{userId}</kbd>     | Show a user's position in the ranking. [response details](#get-ranking-by-id)


<h3 id="post-events-detail">POST /events</h3>

**REQUEST**
```json
{
  "title": "Cool Event",
  "location": "Online",
  "price": 0.0,
  "startDate": "2000-01-01",
  "endDate": "2000-01-01",
  "startTime": "19:00:00",
  "endTime": "23:00:00"
}
```

**RESPONSE**
```json
{
    "eventId": 1,
    "title": "Cool Event",
    "prettyName": "cool-event",
    "location": "Online",
    "price": 0.0,
    "startDate": "2000-01-01",
    "endDate": "2000-01-01",
    "startTime": "19:00:00",
    "endTime": "23:00:00"
}
```

<h3 id="get-all-events">GET /events</h3>

**RESPONSE**
```json
{
    "eventId": 1,
    "title": "Cool Event",
    "prettyName": "cool-event",
    "location": "Online",
    "price": 0.0,
    "startDate": "2000-01-01",
    "endDate": "2000-01-01",
    "startTime": "19:00:00",
    "endTime": "23:00:00"
},
{
    "eventId": 2,
    "title": "Cool Event 2",
    "prettyName": "cool-event-2",
    "location": "Online",
    "price": 0.0,
    "startDate": "2001-01-01",
    "endDate": "2001-01-01",
    "startTime": "19:00:00",
    "endTime": "23:00:00"
}
```

<h3 id="get-event-by-prettyName">GET /events/{prettyName}</h3>

**RESPONSE**

```json
{
  "eventId": 2,
  "title": "Cool Event 2",
  "prettyName": "cool-event-2",
  "location": "Online",
  "price": 0.0,
  "startDate": "2001-01-01",
  "endDate": "2001-01-01",
  "startTime": "19:00:00",
  "endTime": "23:00:00"
}
```
<h3 id="post-common-subscription">POST /subscription/{prettyName}</h3>

**REQUEST**

```json
{
  "name": "John Wiki",
  "email": "jonhwikiwiki@gmail.com"
}
```

**RESPONSE**

```json
{
  "subscriptionNumber": 1,
  "designation": "http://coolevent.com/subscription/cool-event/1"
}
```

<h3 id="post-indication-subscription">POST /subscription/{prettyName}/{userId}</h3>

**REQUEST**

```json
{
  "name": "Decation",
  "email": "Decation@gmail.com"
}
```

**RESPONSE**

```json
{
  "subscriptionNumber": 2,
  "designation": "http://coolevent.com/subscription/cool-event/2"
}
```

<h3 id="get-ranking">GET /subscription/{prettyName}/ranking</h3>

**RESPONSE**

```json
{
    "subscribers": 3,
    "userId": 1,
    "name": "John Wiki"
},
{
    "subscribers": 2,
    "userId": 2,
    "name": "Decation"
},
{
    "subscribers": 1,
    "userId": 3,
    "name": "Larry bird"
}
```

<h3 id="get-ranking-by-id">GET /subscription/{prettyName}/ranking/{userId}</h3>

**RESPONSE**

```json
{
  "item": {
          "subscribers": 1,
          "userId": 3,
          "name": "Larry bird"
      },
    "position": 3
}
```

<h2 id="expections">❌ EXPECTION HANDLING</h2>

**In case of a invalid prettyName:**

```json
{
    "message": "Event {prettyName} not found"
}
```

**In the case of an attempt to register a user who is already registered.**

```json
{
    "message": "User {userName} is already registered for the event: Cool Event 2"
}
```

**In the case of an invalid userId in the referral link.**

```json
{
    "message": "User {userId} not found"
}
```

**If a request is made for a non-existent ranking.**

```json
{
    "message": "Event ranking of {prettyName} not found"
}
```

**If a request is made for the ranking position of a user who has not referred anyone.**

```json
{
    "message": "There are no registrations with referrals from the user: {userId}"
}
```

<h2>🌟 Thank you for visiting</h2>
I really appreciate the attention, feel free to contribute!
