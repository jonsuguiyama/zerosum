# zerosum: Design

## Purpose

A financial ledger engine built as a portfolio project, and as a daily-commit
habit while learning Java and Spring Boot. The core idea is double-entry
bookkeeping: money is never edited in place, only moved between accounts as
balanced transactions. This is a common "senior-level" differentiator versus
basic CRUD projects, and it's small enough to start with plain Java, no
framework required.

## Guiding constraint

The author is early in a Java fundamentals course (pre-Spring Boot, pre-Spring
Data JPA) and wants a project that:

1. Can be built meaningfully with today's Java knowledge.
2. Grows into a full Spring Boot + PostgreSQL app as the course progresses,
   without a rewrite.
3. Supports a genuine daily commit (no padding commits) even on low-time days.

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
- Build tool: Maven (continuity with the Spring Boot course, which uses
  Maven; adding `spring-boot-starter-*` dependencies later is additive, not a
  migration).
- Testing: JUnit 5. Tests are written first for each invariant (TDD), so the
  reasoning for a rule is visible before the code that enforces it.

### Phase 2 (once Spring Data JPA is covered)

- Wrap the Phase 1 domain classes with Spring Boot + PostgreSQL persistence
  and a REST API (create account, post transaction, get balance/history).
  The domain model doesn't change; a persistence and API layer is added on
  top of it.

### Phase 3 (once Spring Security / transactions are covered)

- Idempotency keys on the "post transaction" endpoint (duplicate requests
  must not double-post).
- Optimistic locking on account balances to handle concurrent transactions
  safely.
- Concurrency tests proving the above.

## Daily workflow

- Small feature branches per addition, PR into `main`, per the author's
  standing git workflow preference. Commits still count toward the GitHub
  contribution graph regardless of branch.
- Every commit is real work: a class, a test, a bug fix, or a docs entry.
  Never a blank/padding commit.
- Fallback for zero-time days: add one more test case or one `DEVLOG.md`
  entry. Still genuine, just small.
- `docs/DEVLOG.md`: one dated entry per work session, describing what was
  added and *why*. Doubles as a learning journal and as interview material
  later.

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
- Multi-currency support, exchange rates, or accounting periods/closing —
  not needed to demonstrate the double-entry concept.
