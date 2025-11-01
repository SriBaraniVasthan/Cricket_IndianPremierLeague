# Cricket Premier League Application

## Overview

This is a Spring Boot based RESTful application that manages cricket league match results and computes a live scoreboard. The application supports submitting match results and retrieving aggregated league statistics such as matches won per team and overall league winner.

---

## Features

- Submit cricket match results with detailed team runs and win percentages.
- Query individual match results by ID.
- Compute and retrieve an up-to-date scoreboard summarizing team wins.
- In-memory storage of match results with thread-safe concurrent map implementation.
- Clean REST API with JSON request and response formats.
- Integration tests loading sample match data and verifying scoreboard correctness.

---

## Technology Stack

- Java 17
- Spring Boot 3.x
- RESTful Web Services
- JUnit 5 for integration and unit testing
- Maven for build and dependency management

---

## Getting Started

### Prerequisites

- Java 17 or higher installed
- Maven installed
- An IDE (e.g., IntelliJ IDEA, Eclipse) recommended for development

#Build the project and run tests:
- mvn clean install
  
#Run the application:
- mvn spring-boot:run
- The REST API will be available at: http://localhost:8080/

 ## API Endpoints

- `GET /result/{id}` : Retrieve match result by ID.
- `POST /result` : Submit a new match result JSON payload.
- `GET /scoreboard` : Get current league scoreboard summary.

---

## Testing
Python script to generate test data for 90 IPL matches(2 games against one opponent: Home and Away):  https://github.com/SriBaraniVasthan/Cricket_IndianPremierLeague/blob/main/iplMatchTestDataGenerator.py

Integration tests are included, which post sample match results from JSON files and validate the computed scoreboard. Run all tests with:
- mvn test

# Results

![Screenshot 2025-11-01 230730](https://github.com/user-attachments/assets/f24538aa-846e-4b01-88b2-fdf6a02af3ab)

![Screenshot 2025-11-01 230756](https://github.com/user-attachments/assets/83569669-2731-4c81-8659-4d0cd97c2f6c)

![Screenshot 2025-11-01 230712](https://github.com/user-attachments/assets/ade10fae-9fd0-4390-9066-273323e76e02)
