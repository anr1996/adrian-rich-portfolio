# Adrian Rich Portfolio

A full-stack personal portfolio website with a private admin dashboard for managing its content.

**Status:** in development

## Tech stack

- **Backend:** Java 25, Spring Boot, Spring Security (JWT), Spring Data JPA, Flyway, Maven
- **Database:** PostgreSQL 18 (Docker Compose for local development)
- **Frontend:** React, TypeScript, Vite, plain CSS
- **Testing:** JUnit, Mockito, Testcontainers, Vitest, React Testing Library
- **CI:** GitHub Actions

## Features

- Public pages: Home, Projects (filterable by technology), Resume (with PDF download), Education, Contact
- Contact form with validation and rate limiting
- Admin dashboard: manage all site content, read messages, upload the resume PDF, view page-view stats
- Admin login secured with Argon2id password hashing and an HttpOnly JWT cookie

## Running locally

Instructions coming as the project is built.
