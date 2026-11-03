# API Flows

## Status

The four Phase 1 API groups are implemented as an in-memory Spring MVC demo on branch `Api`. All users, bearer tokens, accounts, and transfers are temporary and disappear when the application restarts. No JPA repository, database, migration, or persistence behavior is used by these endpoints. Spring's DataSource, JPA, and Flyway auto-configuration are disabled for this profile by `application.properties`; existing database properties and migration remain intact for later phases.

Detailed flows and endpoint examples: [Authentication](AUTH_API.md), [Accounts](ACCOUNTS_API.md), [Transfers](TRANSFERS_API.md), and [Transactions](TRANSACTIONS_API.md).

## Implemented endpoint index

| Method | Path | Authentication | Behavior |
|---|---|---|---|
| POST | `/api/auth/register` | None | Create user, 201 |
| POST | `/api/auth/login` | None | Check credentials, issue temporary bearer token, 200 |
| POST | `/api/accounts` | Bearer | Create INR account, optional starting balance, 201 |
| GET | `/api/accounts/{accountId}` | Bearer, owner | Account details, 200 |
| GET | `/api/accounts/{accountId}/balance` | Bearer, owner | Balance, 200 |
| POST | `/api/transfers` | Bearer, source owner, Idempotency-Key | Transfer, 201 |
| GET | `/api/transfers/{transferId}` | Bearer, source owner | Transfer status/details, 200 |
| GET | `/api/transactions?accountId={uuid}` | Bearer, account owner | Newest-first transfer history, 200 |

Errors from domain and validation paths use `{timestamp,status,code,message,details}`. See each group document for field rules, actual classes/methods, exception behavior, worked examples, and verification limits. Authentication is demo authentication: opaque in-memory tokens have no expiry or logout endpoint. Transfers serialize within one JVM and implement process-local idempotency only; they do not have database atomicity, durable deduplication, or multi-instance concurrency guarantees.
