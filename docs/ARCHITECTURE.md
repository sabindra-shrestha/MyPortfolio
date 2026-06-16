# Portfolio Architecture

## System Overview
Monolithic Spring Boot backend with Angular frontend,
PostgreSQL database, and Apache Kafka for event-driven features.

## Tech Stack
- **Backend:** Java 17, Spring Boot 3, Spring Security, JWT
- **Database:** PostgreSQL (AWS RDS)
- **Message Broker:** Apache Kafka
- **Frontend:** Angular 17, Angular Material, RxJS
- **Cloud:** AWS EC2, RDS, S3, CloudFront, Route53
- **DevOps:** Docker, GitHub Actions

## Database Tables
- USERS — admin authentication
- PROJECTS — portfolio projects
- SKILLS — technical skills
- BLOGS — blog posts
- CONTACTS — contact form submissions

## API Base URL
`/api/v1/`

## Authentication
JWT Bearer token. Admin-only endpoints require
`Authorization: Bearer <token>` header.
