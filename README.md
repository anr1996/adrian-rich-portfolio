# Adrian Rich Portfolio

![CI](https://github.com/anr1996/adrian-rich-portfolio/actions/workflows/ci.yml/badge.svg)

A full-stack personal portfolio website.

**Status:** in development

## Tech stack

- **Backend:** Java 25, Spring Boot 4, Spring Data JPA, JdbcClient, Flyway, Maven
- **Database:** PostgreSQL 18 (Docker Compose for local development)
- **Frontend:** React, TypeScript, Vite, plain CSS (in progress)
- **Testing:** JUnit, AssertJ, MockMvcTester, Testcontainers
- **CI:** GitHub Actions runs every test on each pull request

## Features (v1.0)

- Public REST API serving all site content from PostgreSQL: profile, projects with technology tags, education, certifications, experience, skills and links
- Public pages: Home, Projects, Resume, Education, Contact (in progress)
- Contact form that stores messages in the database (planned)
- Integration tests against a real PostgreSQL 18 container

An admin dashboard for editing content from the browser is planned for a later version.

## Running locally

Instructions coming as the project is built.
