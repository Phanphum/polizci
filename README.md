# GoodPoliz – Automated UI & Integration Testing

GoodPoliz is a mobile and backend system designed to support police operations through AI-assisted decision-making, real-time alerts, and secure communication.

This project includes **Flutter automated UI tests** using  
**Flutter Drive + ChromeDriver + Flutter Web**.

## Core Features
- **Incident Importance Ranking**
- **Real-Time Emergency Alerts**
- **Traffic & Crime Data Dashboard**
- **Secure Chat Box for Officers**
- **Performance Analytics Reporting**

This README summarizes the **Unit Testing**, **System Testing**, and **Automated UI Testing** implemented for the project.

---

# 1. Unit Testing (Backend – Java Spring Boot)

Our backend unit tests are implemented using **JUnit**, with three unit test suites covering both controller and service layers.  
Each suite includes multiple test cases designed using input space partitioning and logic coverage techniques.

### Unit Test Suites Overview

#### **1. ChatControllerTest**
Validates:
- Sending messages between two users
- Creating a new chat if none exists
- Basic input validation for message text (empty vs non-empty)
- Handling failures when saving messages

#### **2. CrimeIncidentControllerTest**
Validates:
- `getCrimeIncidents(type)` filtering logic
- Treating `null`, blank, and "All Types" as “no filter”
- Returning only incidents matching a specific type (e.g., "Traffic Accident")
- Returning an empty list when no incident matches the requested type  

#### **3. IncidentServiceTest**
Validates:
- The importance score calculation in `addNewIncident(Incident)`
- How incident type, time of day, place, and notes (severity keywords) contribute to the final score
- Mapping from score → rank level (`LOW`, `MEDIUM`, `HIGH`) and `isRanked` / `isNew` flags
- Repository interaction for saving the enriched incident

### Folder Structure
```text
test/
└── java
    └── com/example/demo
        ├── controller
        │   ├── ChatControllerTest.java
        │   └── CrimeIncidentControllerTest.java
        └── service
            └── IncidentServiceTest.java
```

# 2. System Testing (Manual Tests)

We performed manual system testing for three major workflows in the GoodPoliz mobile app.  
All detailed test cases (with IDs, steps, expected/actual results) are located in: 
```text
/manual test case/
```

#### **Test Suite 1 — Secure Chat: User Search**
Covers:
- Searching for an existing user (happy path)
- Searching for a non-existent user (unhappy path)

Focus: list filtering and proper empty-state handling.

---

#### **Test Suite 2 — Incident Importance Ranking: Add & Rank**
Covers:
- Creating an incident with all required fields  
- Form validation when required fields are missing  

Focus: incident creation workflow and ranking logic.

---

#### **Test Suite 3 — Incident Importance Ranking: Search Incident**
Covers:
- Searching for existing incident types  
- Empty-state handling when no matching incidents exist  

Focus: filtering logic and correctness of displayed results.

---

### Requirement Traceability Matrix
Located in the same folder: 
```text
manual test case/
└──Traceability_matrix.README.md
```

# 3. Automated UI Testing (Flutter Drive)

We implemented automated UI testing using:

- **Flutter Drive**  
- **ChromeDriver**  
- **Flutter Web**

Each automated test suite is directly based on its corresponding manual test suite.

### Automated Test Suites Overview

#### **1. search_chat_test.dart**
Automates:
- 


#### **2. search_incident_test.dart**
Automates:
- 

#### **3. incident_importance_ranking_test.dart**
Automates:
- 

### Folder Structure
```text
/automated_test_cases/
├── search_chat_test.dart
├── search_incident_test.dart
└── incident_importance_ranking_test.dart
```

---

## Running Automated UI Tests (Flutter Drive + Chrome)

### **Start ChromeDriver**
Before running any test, ChromeDriver must be running.

```sh
chromedriver --port=4444
```

### **Search Chat Test**
```sh
flutter drive --driver automated_test_cases/search_chat_test.dart --target=automated_test_cases/search_chat_test.dart -d chrome
```

### **Search Incident Test**
```sh
flutter drive --driver automated_test_cases/search_incident_test.dart --target=automated_test_cases/search_incident_test.dart -d chrome
```

### **Add & Rank Test**
```sh
flutter drive --driver automated_test_cases/incident_importance_ranking_test.dart --target=automated_test_cases/incident_importance_ranking_test.dart -d chrome
```
