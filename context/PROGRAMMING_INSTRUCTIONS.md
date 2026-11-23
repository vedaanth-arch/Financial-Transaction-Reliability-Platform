# Programming Instructions

These working instructions summarize the developer's requirements and the detailed standards in [`AGENTS.md`](AGENTS.md). When they differ in detail, follow `context/AGENTS.md` as the full project standard.

## Work one feature at a time

- Implement one complete, reviewable feature or logically complete sub-feature before starting the next.
- Before coding, inspect the current code and the relevant context docs; state the scope, affected files, expected data flow, failure cases, and documentation updates.
- Keep changes focused. Do not rewrite a completed module or unrelated working code without a concrete requirement. If revision is necessary, explain why and preserve existing behavior where possible.
- Do not build multiple large features in one unexplained change. Ask the developer when a genuinely blocking ambiguity remains; otherwise make and document a reasonable assumption.

## Explain the code in simple English

For each new endpoint, important class, or meaningful method, explain its purpose, inputs and outputs, execution order, important Java/Spring/JPA behavior, next method/file in the flow, and relevant exceptions. Put short explanatory comments near non-obvious code when useful; comments must explain behavior rather than repeat syntax. Avoid repetitive comments on trivial statements.

For API features, document the request contract, authentication and authorization, validation, success and error responses, business rules, and affected tables (or explicitly say persistence is not implemented).

## Trace data and exceptions

Document the real sequence from HTTP request to response using actual file, class, method, and repository names. Explain which values are passed at each boundary and where data is read or written. Trace failures too: exception source, propagation/translation, HTTP error result, and transaction rollback/commit effect. Never invent code paths or database effects.

## Keep context files current

After each logically complete sub-feature, update:

- `PROJECT_PROGRESS.md` with completed scope, changed files, verification actually performed, issues, and next step.
- The relevant API reference (`API_FLOWS/AUTH_API.md`, `ACCOUNTS_API.md`, `TRANSFERS_API.md`, or `TRANSACTIONS_API.md`) and `API_FLOWS/README.md` with the implemented contract and flow.
- `DATABASE_DOCUMENTATION.md` whenever data model, migrations, entities, repositories, constraints, or API/database behavior changes.

Record planned and implemented behavior separately. Do not report unrun tests as passing or plans as implemented. Keep examples and flow diagrams aligned with the current code.

## Financial and dependency discipline

Use `BigDecimal` for money, define and enforce currency/scale rules, and preserve atomicity and ledger consistency for transfers. Address overdrafts, concurrency, and duplicate requests according to the chosen and documented implementation. Inspect the existing build before adding dependencies, explain each necessary dependency, and do not add infrastructure without a demonstrated need. Never commit secrets.

## Four phases

1. API endpoints and contracts first.
2. Database schema and persistence second.
3. API testing against database content third.
4. Comprehensive database documentation fourth.

Follow the phase tasks, acceptance criteria, and Definition of Done in `context/AGENTS.md`. Report actual results and known limitations at completion. No implementation or tests were present when these instructions were created; this is not a claim that any phase has been completed.
