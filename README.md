# Java_Loan

This is a standalone Java version of the LoanCheck application using Spring Boot and Thymeleaf.

## Run the application

From `d:\Aks\03_Coding\Java_Loan`:

1. `mvn clean package`
2. `mvn spring-boot:run`

The app will start on `http://localhost:8081`.

## What is included

- Copied backend service logic from the original `LoanCheck` project
- A Thymeleaf frontend with:
  - landing page
  - multi-step loan application form
  - eligibility results and risk breakdown

## Notes

- The backend API and view are included in the same Spring Boot application.
- The new Java frontend is separate from the React frontend in the original repo.
