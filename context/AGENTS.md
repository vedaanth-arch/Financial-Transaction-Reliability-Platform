# AGENTS.md — Financial Transaction Reliability Platform

## 1. Project Overview

### 1.1 Project purpose

Build an **intermediate-level Java backend application** called the Financial Transaction Reliability Platform.

The application simulates reliable financial transfers between accounts owned by registered users. Its purpose is to develop practical knowledge of Java, Spring Boot, REST API development, database design, transaction management, testing, and software engineering workflows.

This is an educational portfolio project with realistic financial-domain requirements. It is not a real banking system and does not connect to real banks, UPI, or external payment networks.

The application should progress beyond basic CRUD operations by implementing business rules, consistent financial records, error handling, authorization, and reliable database operations.

### 1.2 Primary learning objectives

The developer should understand how to:

- Design and implement REST API endpoints.
- Organize a Java application into understandable packages and files.
- Follow the flow of an HTTP request through controllers, services, repositories, and the database.
- Understand Java methods, parameters, return values, built-in functions, annotations, and exceptions.
- Design relational database tables and relationships.
- Implement financial transfers using atomic database transactions.
- Prevent duplicate transfer requests through idempotency.
- Handle concurrent requests and prevent invalid overdrafts.
- Write unit and integration tests.
- Use Git, GitLab, Maven, Docker, and GitLab CI/CD.
- Explain architectural decisions and tradeoffs during technical interviews.

### 1.3 Application capabilities

The planned capabilities include:

1. User registration and authentication.
2. Account creation and account information retrieval.
3. Account balance retrieval.
4. Transfers between eligible accounts.
5. Transfer status and transaction history.
6. Debit and credit ledger records.
7. Input validation and standardized error responses.
8. Account ownership and role-based authorization.
9. Administrative operations, when justified.
10. Automated tests, CI/CD, and technical documentation.

Implement these capabilities incrementally. Do not build all features in one large change.

### 1.4 Project quality expectations

The application must be understandable to its developer, testable, and organized for future extension.

Do not describe the application as production-grade or bank-grade merely because it uses Spring Boot, PostgreSQL, or security libraries. State the actual guarantees that have been implemented and verified.

---

## 2. Technology Stack

Use the following baseline unless a documented decision changes it.

- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA and Hibernate
- Maven
- PostgreSQL
- Flyway
- Spring Security and JWT
- Jakarta Bean Validation
- JUnit 5
- Mockito
- Spring Boot Test
- Testcontainers
- OpenAPI/Swagger
- Git and GitLab
- Docker and Docker Compose
- GitLab CI/CD

### Dependency rules

- Inspect the existing project before introducing dependencies.
- Explain why each new dependency is needed.
- Use versions compatible with the project's Spring Boot version.
- Prefer the existing dependency or framework functionality when it already solves the problem.
- Do not add Redis, Kafka, RabbitMQ, Kubernetes, or other advanced infrastructure without a demonstrated requirement.
- Keep dependency and Java versions consistent across the build, tests, Docker image, and CI/CD pipeline.

---

## 3. Mandatory Folder and Subfolder Architecture

### 3.1 Structural principle

Organize the application primarily by **major functional area**, with related subfolders inside each area.

Major API groups must have their own directories. Do not put every controller into one large `controller` folder.

Security and configuration must follow the same folder-and-subfolder principle.

Use the following as the target structure. Adjust package names and filenames when the existing implementation requires it, but preserve the organizational principles.

```text
financial-transaction-reliability-platform/
│
├── AGENTS.md
├── README.md
├── pom.xml
├── .gitignore
├── .gitlab-ci.yml
├── Dockerfile
├── compose.yaml
│
├── docs/
│   ├── architecture/
│   │   ├── system-overview.md
│   │   └── architecture-decisions.md
│   │
│   ├── api-flows/
│   │   ├── README.md
│   │   ├── auth-flow.md
│   │   ├── account-flow.md
│   │   ├── transfer-flow.md
│   │   └── transaction-history-flow.md
│   │
│   ├── api-reference/
│   │   ├── auth-api.md
│   │   ├── accounts-api.md
│   │   ├── transfers-api.md
│   │   └── transactions-api.md
│   │
│   └── database/
│       ├── README.md
│       ├── schema-overview.md
│       ├── table-definitions.md
│       ├── relationships.md
│       ├── constraints-and-indexes.md
│       └── migrations.md
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/financialplatform/
│   │   │       ├── FinancialPlatformApplication.java
│   │   │
│   │   │       ├── api/
│   │   │       │   ├── auth/
│   │   │       │   │   ├── controller/
│   │   │       │   │   ├── dto/
│   │   │       │   │   └── mapper/
│   │   │       │   │
│   │       │       ├── accounts/
│   │       │       │   ├── controller/
│   │       │       │   ├── dto/
│   │       │       │   └── mapper/
│   │       │       │
│   │       │       ├── transfers/
│   │       │       │   ├── controller/
│   │       │       │   ├── dto/
│   │       │       │   └── mapper/
│   │       │       │
│   │       │       ├── transactions/
│   │       │       │   ├── controller/
│   │       │       │   ├── dto/
│   │       │       │   └── mapper/
│   │       │       │
│   │       │       └── admin/
│   │       │           ├── controller/
│   │       │           ├── dto/
│   │       │           └── mapper/
│   │       │
│   │       ├── security/
│   │       │   ├── authentication/
│   │       │   ├── authorization/
│   │       │   ├── jwt/
│   │       │   ├── filters/
│   │       │   └── handlers/
│   │       │
│   │       ├── config/
│   │       │   ├── security/
│   │       │   ├── database/
│   │       │   ├── openapi/
│   │       │   └── application/
│   │       │
│   │       ├── service/
│   │       │   ├── auth/
│   │       │   ├── accounts/
│   │       │   ├── transfers/
│   │       │   ├── transactions/
│   │       │   └── admin/
│   │       │
│   │       ├── repository/
│   │       │   ├── users/
│   │       │   ├── accounts/
│   │       │   ├── transfers/
│   │       │   └── ledger/
│   │       │
│   │       ├── entity/
│   │       │   ├── users/
│   │       │   ├── accounts/
│   │       │   ├── transfers/
│   │       │   └── ledger/
│   │       │
│   │       ├── exception/
│   │       │   ├── domain/
│   │       │   ├── validation/
│   │       │   └── handler/
│   │       │
│   │       └── common/
│   │           ├── constants/
│   │           ├── enums/
│   │           └── response/
│   │
│   └── resources/
│       ├── application.yml
│       ├── application-local.yml
│       └── db/
│           └── migration/
│
└── src/test/
    ├── java/
    │   └── com/example/financialplatform/
    │       ├── api/
    │       ├── service/
    │       ├── repository/
    │       ├── security/
    │       └── integration/
    │
    └── resources/
        └── application-test.yml
```

This is a target structure, not a requirement to create empty folders immediately. Create folders when their functionality is introduced.

### 3.2 API folders

Each major API group must contain its related controllers, DTOs, and mapping logic.

Examples:

- `api/auth/` — registration and login endpoints.
- `api/accounts/` — account creation, details, and balance endpoints.
- `api/transfers/` — transfer creation and transfer status endpoints.
- `api/transactions/` — transaction history and filtering.
- `api/admin/` — restricted administrative endpoints.

Keep business logic in the corresponding service package. API controllers must delegate to services rather than implement database operations directly.

### 3.3 Security folders

Organize security functionality by responsibility.

- `security/authentication/` — authentication-related components.
- `security/authorization/` — authorization decisions and access rules.
- `security/jwt/` — token creation, parsing, and validation.
- `security/filters/` — security filters.
- `security/handlers/` — authentication and access-denied handling.

Avoid placing all security classes in a single package.

### 3.4 Configuration folders

Separate configuration by concern.

- `config/security/` — security framework configuration.
- `config/database/` — database-related configuration, when custom configuration is necessary.
- `config/openapi/` — API documentation configuration.
- `config/application/` — shared application-level configuration.

Keep standard Spring Boot configuration in the appropriate `application.yml` files. Do not create unnecessary Java configuration classes for behavior already supplied by Spring Boot.

### 3.5 API flow documentation outside API subfolders

The `docs/api-flows/` directory must remain separate from the source-code API folders.

For each implemented API, create or update a Markdown file explaining how its request moves through the application.

For example:

`docs/api-flows/transfer-flow.md`

This file must describe the flow from the HTTP request to the final HTTP response, identifying the actual source files, classes, functions, and database operations involved.

The flow document is mandatory for each completed API group.

### 3.6 Architecture discipline

- Do not move all business logic into controllers to reduce the number of files.
- Do not duplicate the same business rule across multiple layers.
- Keep DTOs separate from persistent entities.
- Avoid circular dependencies between packages.
- Keep package names consistent with the chosen base package.
- Document and justify deviations from this architecture.
- Prefer a modular monolith over microservices for this project.

---

## 4. Mandatory Coding and Explanation Standard

The developer is learning Java and Spring Boot. Code must be accompanied by detailed, structured explanations.

**Do not simply generate code and ask the developer to trust it.**

For each new API endpoint, important class, or meaningful function, provide the following documentation.

### 4.1 Endpoint description

Explain:

1. Endpoint name and purpose.
2. HTTP method and URL.
3. Authentication and authorization requirements.
4. Required headers, path variables, query parameters, and request body.
5. Request field meanings and validation rules.
6. Expected success status and response body.
7. Possible error status codes and their causes.
8. Relevant business rules.
9. Database tables read or changed.
10. Important assumptions and limitations.

### 4.2 Function and method explanation

For every important method, document the following:

**A. Method identity**

- File path.
- Class name.
- Method name and signature.
- Whether it is a custom method, inherited method, framework method, or language feature.

**B. Purpose**

Explain in detail what the method does and why the application needs it.

**C. Inputs and outputs**

Describe:

- Every parameter and its type.
- Where each parameter originates.
- The return type.
- What the returned value represents.
- Whether the method can return an empty result, `null`, an `Optional`, or a response object, as applicable.

**D. Built-in and framework functionality**

Explain important Java, Spring, JPA, and library functionality used by the method.

For each relevant function, annotation, or API, explain:

- What it is.
- What it does.
- Why it is used here.
- How it behaves.
- What it returns or changes.
- What important exceptions or failure cases it can produce.

Examples include `Optional.orElseThrow()`, `BigDecimal.compareTo()`, `ResponseEntity`, `@Valid`, `@Transactional`, repository query methods, and Spring dependency injection.

Do not explain every trivial syntax token repeatedly. Prioritize unfamiliar, important, or behavior-changing functionality.

**E. Execution sequence**

Describe the operations in the order they execute.

Explain which conditions are checked first, what happens on success, and what happens on failure.

**F. Next function or file**

Explain which function or file receives the output or control next, what data is passed to it, and why.

**G. Errors and exceptions**

Explain the relevant exceptions, where they originate, whether they are propagated or handled, and what HTTP response or transaction outcome results.

**H. Example**

Provide a small realistic example using sample request data and trace the resulting behavior.

### 4.3 Explain data movement between functions and files

For each API, show the complete request path.

Example:

1. `TransferController.createTransfer()` receives the HTTP request.
2. Spring converts the JSON request into a request DTO and performs configured validation.
3. The controller passes the validated request and authenticated identity to the transfer service.
4. The service checks the business rules and orchestrates the transfer.
5. The repository loads or locks the relevant accounts using the chosen concurrency strategy.
6. The persistence layer updates the accounts and writes the transfer and ledger records.
7. The transaction commits only when all required operations succeed.
8. The service returns the appropriate result to the controller.
9. The controller returns the HTTP response.
10. If an error occurs, the relevant exception-handling path converts it into a standardized error response.

Use the actual implemented class and method names. Do not invent function names in documentation for code that does not exist.

### 4.4 Required API flow document template

Create a separate Markdown document in `docs/api-flows/` for each API group.

Use this structure:

#### 1. API overview

Purpose, HTTP method, URL, authentication requirements, and expected behavior.

#### 2. Request entry point

Identify the controller file, method, parameters, annotations, and input validation.

#### 3. Controller-to-service flow

Explain the method calls, data passed between functions, return types, and error propagation.

#### 4. Service execution

Explain the business rules in execution order and the framework functionality used.

#### 5. Repository and database flow

Identify repository methods, entities, queries, and database tables involved. If persistence has not yet been implemented, explicitly state that.

#### 6. Return path

Explain how the service result becomes the HTTP response.

#### 7. Failure paths

Document validation errors, missing records, authorization failures, business conflicts, and unexpected failures as applicable.

#### 8. Sequence diagram

Include a Mermaid sequence diagram showing the implemented request flow.

#### 9. Worked example

Trace a representative request from its incoming JSON to the final response and database effects.

#### 10. Verification

List the relevant tests and the actual verification results. Do not claim a test passed unless it was run successfully.

### 4.5 Keep documentation synchronized

Whenever an endpoint, method, request DTO, response DTO, or persistence flow changes:

- Update the endpoint description.
- Update the relevant function explanations.
- Update the flow document.
- Update examples that are no longer accurate.
- Identify the affected tests.

Do not leave documentation describing an old implementation.

---

## 5. Financial Correctness Rules

These rules apply throughout implementation and testing.

### 5.1 Monetary values

- Use `BigDecimal`, not `float` or `double`, for monetary values.
- Use an appropriate fixed-precision database type such as `NUMERIC(19,2)` where it matches the supported currency rules.
- Define currency support, scale, rounding, and transfer limits explicitly.
- Reject zero and negative transfer amounts.
- Do not assume all currencies use two decimal places if multi-currency support is introduced.

### 5.2 Atomic transfers

A successful transfer must persist all required changes in one database transaction:

1. Source account debit.
2. Destination account credit.
3. Transfer record.
4. Corresponding debit and credit ledger entries.

If any required database operation fails, the transaction must roll back rather than leave a partial transfer.

Explain the behavior of `@Transactional`, transaction boundaries, and rollback rules whenever they are introduced.

Do not assume `@Transactional` alone prevents concurrent overdrafts.

### 5.3 Concurrency control

- Prevent concurrent operations from improperly overdrawing accounts.
- Choose a documented locking or concurrency-control strategy.
- Keep balance validation and balance updates within the appropriate transaction boundary.
- Consider deadlocks when locking multiple accounts.
- Use a consistent locking order when applicable.
- Verify concurrency behavior with database integration tests.

### 5.4 Idempotency

- Support an idempotency key for transfer creation.
- Define its scope, uniqueness constraints, and lifecycle.
- Store it durably.
- Repeated equivalent requests must not create duplicate transfers.
- A reused key with a different payload must be handled explicitly.
- Concurrent requests using the same key must not bypass duplicate protection.

### 5.5 Ledger integrity

- A completed transfer must have the expected matching debit and credit entries.
- Both entries must reference the same transfer.
- Ledger records should be append-only in normal application workflows.
- Use appropriate database constraints.
- Make balance and ledger consistency verifiable through tests and documentation.

---

## 6. Database and Security Rules

### 6.1 Database

Use PostgreSQL and Spring Data JPA for persistence.

Use Flyway for version-controlled schema changes once database implementation begins.

Use appropriate:

- Primary keys.
- Foreign keys.
- Unique constraints.
- Check constraints.
- Indexes.
- Nullability rules.
- Numeric precision and scale.
- Transaction boundaries.

Do not rely exclusively on Java validation for rules that must also be protected at the database level.

### 6.2 Security

- Store password hashes, never plaintext passwords.
- Authenticate protected requests.
- Enforce account ownership and role-based access in the backend.
- Never trust a client-supplied user ID as proof of identity.
- Protect JWT signing keys and database credentials.
- Keep secrets out of source control and logs.
- Do not expose internal stack traces or sensitive details in error responses.
- Document the authorization requirements of each protected endpoint.

Implement security in a staged manner appropriate to the project, but do not describe an unprotected endpoint as secure.

---

## 7. API and Error-Handling Conventions

Use RESTful endpoints and separate request and response DTOs.

Initial endpoint groups:

- `/api/auth` — registration and authentication.
- `/api/accounts` — account operations.
- `/api/transfers` — transfer creation and status.
- `/api/transactions` — transaction history.
- `/api/admin` — restricted administrative operations.

Use appropriate HTTP status codes, including:

- `200` — successful retrieval or operation.
- `201` — resource created.
- `400` — invalid request.
- `401` — authentication required or invalid.
- `403` — operation not authorized.
- `404` — resource not found.
- `409` — applicable business conflict.
- `500` — unexpected server failure.

Use centralized exception handling, such as `@RestControllerAdvice`, and a consistent error structure.

Do not catch and suppress exceptions without a clear reason. Explain whether an exception propagates, is translated, or triggers rollback.

---

## 8. Testing Strategy

Tests must verify behavior rather than merely confirm that methods execute.

### Unit tests

Cover relevant business rules, including:

- Invalid transfer amounts.
- Insufficient funds.
- Missing accounts.
- Transfers to the same account, if prohibited.
- Idempotency behavior.
- Authorization decisions.
- Expected domain exceptions.

Use Mockito where isolation from dependencies is useful.

### Integration tests

Use PostgreSQL-backed integration tests, preferably through Testcontainers, to verify:

- Entity mappings and database constraints.
- Transfer commit and rollback.
- Account balance changes.
- Ledger consistency.
- Duplicate request protection.
- Concurrent transfer behavior.
- Security and authorization behavior.

Mocks alone cannot prove database transaction correctness.

### Test reporting

Before completing a phase or feature:

1. Identify which tests should run.
2. Execute them when the environment permits.
3. Report the actual result.
4. Identify tests that were not run and why.
5. Never fabricate test output, coverage percentages, or success claims.

---

## 9. AI Agent Working Rules

### 9.1 Before coding

1. Read `AGENTS.md` and any applicable additional instructions.
2. Inspect the actual repository structure and relevant source files.
3. Identify the current implementation phase.
4. Identify the required endpoint, classes, functions, and dependencies.
5. Explain the planned change and its expected outputs.
6. Identify the validation rules, failure cases, and tests.
7. Confirm which documentation files will need updating.

Do not assume a file, function, database table, or endpoint exists without inspecting the repository.

### 9.2 During coding

- Make small, focused changes.
- Follow the mandatory folder structure.
- Explain the purpose of new files and important methods.
- Explain built-in and framework functions using the template in Section 4.
- Explain data movement between methods and files.
- Maintain a separate API flow document for each implemented API group.
- Preserve existing working behavior unless a change is required.
- Do not silently add unrelated features.
- Do not remove tests merely to make a build pass.
- Do not introduce dependencies without justification.
- Do not hardcode credentials or secrets.
- Explain errors and exceptions encountered during implementation.

If the developer is actively learning a concept, explain the concept before moving to a more advanced abstraction.

### 9.3 After coding

1. Summarize every meaningful file changed.
2. Explain the important functions and their call sequence.
3. Describe the data flow from the API request to the response.
4. Update the relevant API and flow documentation.
5. Run the applicable tests when possible.
6. Report the actual test and build results.
7. Identify remaining errors or limitations.
8. State whether the feature or phase completion criteria have been satisfied.

Do not mark a phase complete merely because source files were created.

### 9.4 Interaction and learning style

The agent should teach while implementing.

Prefer a small implementation followed by a clear explanation over a large, unexplained code dump.

When the developer requests a change:

- Explain the relevant design decision.
- Make the requested change without unnecessarily redesigning unrelated components.
- Explain how the change affects existing functions, files, database operations, and tests.
- Identify any documentation that must change.

The goal is not just to produce code. The goal is for the developer to understand and be able to explain the resulting application.

---

## 10. Scope and Implementation Order

Implement the project in the following four mandatory phases.

Do not silently skip a phase. A later phase may reveal issues that require returning to an earlier one.

### Phase 1 — Build API endpoints and API descriptions

**Objective:** Define and implement the API layer before building the database schema.

Tasks:

1. Establish the Spring Boot project structure.
2. Create the major API folders and their subfolders.
3. Identify each planned endpoint, its purpose, and its contract.
4. Define request DTOs and response DTOs.
5. Implement controller endpoints and validation where appropriate.
6. Define expected success and error responses.
7. Document authentication requirements, even if authentication is implemented later.
8. Create an API reference document for each API group.
9. Create an API flow document describing the intended request path.
10. Clearly identify service and repository behavior that is not implemented yet.

Temporary service implementations may be used to verify the API contract, but they must be clearly labeled. Do not pretend that placeholder responses are backed by real database operations.

**Required outputs:**

- Working project skeleton.
- Organized API folders.
- Defined endpoint contracts.
- Request and response DTOs.
- API descriptions and sample requests.
- Initial API flow documents.
- A list of database requirements discovered while designing the APIs.

**Completion criteria:**

- The agreed endpoint list is documented.
- Each endpoint has a defined request, response, status codes, and validation expectations.
- The API skeleton compiles.
- Available endpoint-level tests pass.
- Every implemented API group has an accurate flow document.
- Unimplemented behavior is explicitly identified.

### Phase 2 — Build the database and document its structure

**Objective:** Design and implement the relational database based on the API contracts and financial-domain rules.

Tasks:

1. Identify all required tables.
2. Define every table's purpose and columns.
3. Define data types, primary keys, foreign keys, uniqueness, and nullability.
4. Document the relationships between tables.
5. Identify necessary indexes and constraints.
6. Define the relationships between Java entities and database tables.
7. Create Flyway migration scripts.
8. Configure PostgreSQL connectivity.
9. Implement JPA entities and repositories.
10. Connect the service layer to actual persistence.
11. Update API flow documents to reflect the real database operations.

Expected tables include:

- `users`
- `accounts`
- `transfers`
- `ledger_entries`

Additional tables, such as audit logs, may be introduced when justified.

**Required outputs:**

- Database schema and Flyway migrations.
- JPA entity classes.
- Repository interfaces.
- PostgreSQL configuration.
- Documented table columns and data types.
- Documented primary and foreign keys.
- Documented relationships, constraints, and indexes.
- Updated service implementations and API flow documents.

**Completion criteria:**

- Migrations execute successfully against the configured database.
- All implemented entities map to the intended schema.
- Foreign keys and other required constraints are present.
- The application can connect to PostgreSQL.
- The implemented API services use the database rather than placeholder data.
- Database documentation matches the actual migrations and entity mappings.

### Phase 3 — Test APIs using database content

**Objective:** Verify that the API layer and database work together correctly.

Tasks:

1. Prepare controlled test data.
2. Run the application against the test database.
3. Send requests using an API client or automated integration tests.
4. Verify HTTP status codes and response bodies.
5. Verify persisted records directly through database queries or test assertions.
6. Verify balance changes and ledger entries.
7. Test invalid requests and missing resources.
8. Test authorization failures.
9. Test transaction rollback.
10. Test idempotency and concurrent transfers when implemented.
11. Record actual results and investigate failures.
12. Update API flow documents if the verified behavior differs from the documented behavior.

**Required outputs:**

- Repeatable test data or test fixtures.
- API test cases and automated integration tests.
- Request and response examples.
- Database verification queries or assertions.
- Test execution results.
- A record of known failures and their resolutions.
- Updated API flow documentation.

**Completion criteria:**

- All required tests for the implemented functionality have been executed successfully, or remaining failures are explicitly documented.
- Successful API requests produce the expected database changes.
- Invalid requests produce appropriate errors.
- Transfer records, account balances, and ledger entries remain consistent.
- Rollback, idempotency, and concurrency behavior are verified when those features are implemented.
- No unverified behavior is reported as proven.

### Phase 4 — Build comprehensive database documentation

**Objective:** Produce database documentation that accurately explains the implemented schema and its relationship to the application.

Tasks:

1. Document every implemented table.
2. Describe every column, data type, and constraint.
3. Explain each primary key and foreign key.
4. Explain one-to-one, one-to-many, or many-to-many relationships as applicable.
5. Document indexes and their purpose.
6. Explain how Java entities map to database tables.
7. Identify which API operations read or write each table.
8. Document Flyway migration order and schema evolution.
9. Explain financial consistency constraints and transaction boundaries.
10. Document representative SQL queries used for verification.
11. Compare the documentation against the actual migrations and entity mappings.

**Required outputs:**

- Database overview.
- Complete table and column reference.
- Relationship documentation.
- ER diagram.
- Constraints and index reference.
- Entity-to-table mapping reference.
- API-to-table mapping reference.
- Migration history and setup instructions.
- Database verification examples.

**Completion criteria:**

- Every implemented table and column is documented.
- All relationships match the real schema.
- The ER diagram matches the implemented tables and foreign keys.
- API-to-table mappings reflect actual repository and service behavior.
- Migration instructions work in a clean test environment.
- Documentation contains no invented tables, fields, constraints, or test results.

### Future phases

After the four required phases are complete, introduce additional phases for:

- Authentication and authorization hardening.
- GitLab CI/CD improvements.
- Docker packaging and deployment.
- Code-quality and dependency-security checks.
- Logging, health checks, and monitoring.
- Performance testing and justified architectural extensions.

Do not allow future tooling to distract from completing and verifying the current phase.

---

## 11. Definition of Done — Phase Completion and Actual Outputs

A phase is complete only when its deliverables exist, are reviewed, and satisfy their acceptance criteria.

For every phase, the agent must provide a completion report containing:

### A. Deliverables

List the actual files, folders, endpoints, migrations, diagrams, or tests created or modified.

### B. Behavior

Explain what now works and how a developer can verify it.

### C. Actual output

Provide relevant evidence, such as:

- Compilation or test command and its actual result.
- Example HTTP request and response.
- Database query and verified records.
- Successful migration output.
- An accurate API flow or ER diagram.
- GitLab pipeline status, when available.

Never invent console output or claim an operation was executed when it was not.

### D. Acceptance criteria

List each required criterion and mark it as:

- Verified.
- Partially verified.
- Not verified.
- Failed.

Explain any incomplete criterion.

### E. Known limitations

List remaining placeholders, missing tests, unresolved errors, and intentionally deferred features.

### F. Next phase readiness

State whether the current phase is ready to close and identify the prerequisites for the next phase.

### Overall project completion

The project is ready for portfolio presentation when the implemented APIs, database, testing, and documentation meet their agreed acceptance criteria; the build and required tests pass; setup instructions work; and known limitations are honestly documented.

Do not equate a successful compilation with a completed feature or a complete project.

---

## 12. Engineering Decisions and Documentation

Maintain the following documents as the project grows:

- `README.md` — setup, execution, environment variables, API usage, and test commands.
- `docs/architecture/system-overview.md` — major components and request flow.
- `docs/architecture/architecture-decisions.md` — significant design choices and tradeoffs.
- `docs/api-reference/` — endpoint contracts and examples.
- `docs/api-flows/` — file-by-file and function-by-function API execution paths.
- `docs/database/` — schema, relationships, constraints, and migration documentation.

Document important decisions, including:

- Transfer atomicity.
- Concurrency control.
- Idempotency semantics.
- Rollback behavior.
- Ledger integrity.
- Database constraints and indexes.
- Security boundaries.
- CI/CD design.

Use real measurements for performance claims. Never invent metrics, test coverage, reliability guarantees, or project outcomes.

The guiding principle is: **implement incrementally, explain the code, trace the data, verify the behavior, and keep documentation synchronized with the actual application.**