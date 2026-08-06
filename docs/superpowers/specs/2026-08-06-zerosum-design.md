# zerosum: Design

## Purpose

zerosum is a double-entry bookkeeping ledger engine. Money is never edited in
place, only moved between accounts as balanced transactions, where every
transaction's debits must equal its credits.

## Motivation

Since I'm currently studying and learning Java, I needed a project that
would challenge me incrementally. I'll be adding new features and making
things better as my knowledge and experience increases.

## Phases

### Phase 1 (now): Plain Java domain model, no framework

- `Account` — an id, a name, and a running balance derived from its entries
  (not stored/mutated directly).
- `LedgerEntry` — belongs to one account, has a direction (`DEBIT` or
  `CREDIT`) and an amount.
- `Transaction` — an immutable, timestamped set of `LedgerEntry` objects.
  Invariant: the sum of debits must equal the sum of credits (nets to zero).
  A `Transaction` that doesn't balance is rejected at construction time.
- Money is represented as `BigDecimal` (or integer minor units), never
  `float`/`double`.
- Build tool: Maven, Spring Boot's standard build tool, so adding
  `spring-boot-starter-*` dependencies in Phase 2 is additive, not a
  migration.
- Testing: JUnit 5. Tests are written first for each invariant (TDD), so the
  reasoning for a rule is visible before the code that enforces it.

### Phase 2 - covering Spring Data JPA

- Wrap the Phase 1 domain classes with Spring Boot + PostgreSQL persistence
  and a REST API (create account, post transaction, get balance/history).
  The domain model doesn't change; a persistence and API layer is added on
  top of it.

### Phase 3 - Spring Security / transactions

- Idempotency keys on the "post transaction" endpoint (duplicate requests
  must not double-post).
- Optimistic locking on account balances to handle concurrent transactions
  safely.
- Concurrency tests proving the above.

## Daily workflow

- Small feature branches per addition, PR into `main`.
- Every commit is real work: a class, a test, a bug fix, or a docs entry.
  Never a blank/padding commit.
- `docs/DEVLOG.md`: one dated entry per work session, describing what was
  added and *why*. Works as a learning journal.

## Error handling (Phase 1 scope)

Phase 1 has no I/O, so "error handling" means enforcing domain invariants via
exceptions at construction time:

- A `Transaction` whose debits and credits don't sum to equal amounts throws
  `UnbalancedTransactionException`.
- A negative or zero-amount `LedgerEntry` throws `IllegalArgumentException`.
- An empty `Transaction` (no entries) is rejected.

## Testing strategy

- JUnit 5, one test class per domain class.
- Tests are written before the implementation for each new invariant
  (test-driven development), so each commit's test proves what the previous
  commit's code was supposed to guarantee.

## Out of scope for now

- Any Spring/PostgreSQL code (Phase 2+).
- Frontend (Next.js) work — deferred until the backend domain and API exist.
- Multi-currency support, exchange rates, or accounting periods/closing.
