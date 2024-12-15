# Load Tests for Spring Petclinic REST API

## Required Setup

- Install [Java 17](https://nodejs.org/) for the [spring-petclinic-rest](https://github.com/spring-petclinic/spring-petclinic-rest) API.
- Install [Python](https://www.python.org/) and [Pip](https://pip.pypa.io/en/stable/installation/) as prerequisites for Locust.
- Install [Locust](https://docs.locust.io/en/stable/)

Start REST API:

```bash
git clone https://github.com/spring-petclinic/spring-petclinic-rest.git
cd spring-petclinic-rest
./mvnw spring-boot:run
```

## Explanation of the Locust script

1. Create Owner (POST /owners):

- We send a POST request to the /owners endpoint with a JSON payload containing basic owner information (firstName, lastName, address, city, telephone).
- Upon successful creation, we extract the id (which corresponds to ownerId) from the response.

2. Create Pet (POST /owners/{ownerId}/pets):

- After successfully creating an owner, we send a POST request to /owners/{ownerId}/pets, where {ownerId} is replaced by the actual ID returned from the owner creation response.
- We create a random pet for that owner, with attributes such as name, birthDate, and type.

3. Locust Setup:

- HttpUser: Locust's base class for user behavior.
- wait_time: Specifies the wait time between each task (randomized between 1 and 3 seconds).
- @task(1): Marks the method to be executed as part of the load test. We are using @task(1) to indicate it's the primary task for this test.

## Execute Tests

Run the Locust test by executing the following in your terminal:

```bash
locust -f locustfile.py --host=http://localhost:9966
```
Open http://localhost:8089 in your browser to start the load test and configure the number of users and spawn rate.