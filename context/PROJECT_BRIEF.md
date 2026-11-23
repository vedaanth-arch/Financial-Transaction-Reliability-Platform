# Financial Transaction Reliability Platform — Project Brief

## Purpose

An intermediate-level Java backend learning and portfolio project that simulates reliable transfers between accounts belonging to registered users. It is educational software, not a real banking system, and has no connection to banks, UPI, or payment networks.

## Product goals

- Practice REST API design and Java/Spring Boot application structure.
- Trace requests from controller through service and repository to PostgreSQL and back.
- Model users, accounts, transfers, and debit/credit ledger entries.
- Learn validation, error handling, authentication/authorization, persistence, transaction boundaries, idempotency, and concurrency controls.
- Verify behavior with automated tests and document the actual implementation.

## Planned capabilities

1. User registration and authentication.
2. Account creation, details, and balance retrieval.
3. Transfers between eligible accounts.
4. Transfer status and transaction history.
5. Consistent debit and credit ledger records.
6. Validation, standardized errors, ownership checks, and role-based authorization.
7. Tests, CI/CD, and technical documentation.

Administrative endpoints are optional and should be added only when justified.

## Baseline technology

Java 21, Spring Boot, Spring Web, Spring Data JPA/Hibernate, Maven, PostgreSQL, Flyway, Spring Security/JWT, Jakarta Bean Validation, JUnit 5, Mockito, Spring Boot Test, Testcontainers, OpenAPI/Swagger, Git/GitLab, and Docker/Compose/GitLab CI/CD. These are the intended baseline from `context/AGENTS.md`, not evidence that dependencies or application code already exist. Check the build before selecting versions or adding dependencies.

## Required implementation order

1. **API endpoints and contracts:** define endpoints, DTOs, validation, responses, errors, and initial flows before the schema. Clearly mark placeholders.
2. **Database:** design and implement schema, migrations, entities, repositories, PostgreSQL configuration, and connect services to persistence.
3. **API/database testing:** exercise requests against a database and verify responses and persisted effects, including failure/rollback and reliability behavior where implemented.
4. **Comprehensive database documentation:** document the implemented schema, constraints, relationships, migrations, and API-to-table flow.

Do not skip phases silently. A later phase can require revisiting an earlier one.

## Current repository facts (checked 2026-11-02)

The nested `financial-transaction-reliability-platform/` project has a Spring Boot `4.1.2` Maven build targeting Java 21, the generated application entry point, and one context-load test. On branch `datbase`, Spring Data JPA, Flyway, Flyway PostgreSQL support, and the PostgreSQL driver have been added; the datasource reads local environment variables and defaults to the requested local PostgreSQL URL and role. A first migration defines `users`, `accounts`, `transfers`, and `ledger_entries`. The migration is prepared but has not been applied because no database password was available to the agent. There are still no API controllers/contracts, entities, repositories, database-backed services, or integration tests. The project is in **Phase 1 not started; Phase 2 foundation in progress**. The four API groups remain plans, not existing endpoints. Route paths, DTO contracts, and exact error schemas remain to be agreed and documented when Phase 1 begins.

## Quality boundary

Do not call the project production-grade or bank-grade. Describe reliability properties only after implementation and verification. See `context/PROGRAMMING_INSTRUCTIONS.md` and the source of truth `context/AGENTS.md` for detailed working rules.
