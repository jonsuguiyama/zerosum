# Devlog

## 2026-08-06

Repo created. Wrote the initial design: a double-entry ledger engine,
starting as a plain Java domain model and growing into a full Spring Boot +
PostgreSQL app in later phases. Chose Maven as the build tool, since it's
Spring Boot's standard, so no build tool migration is needed once Phase 2
adds Spring Boot dependencies.

## 2026-08-07

Added `LedgerEntry` and `Transaction`, the first two domain classes. A
`LedgerEntry` belongs to one account, has a direction (debit or credit) and
a positive amount - it rejects zero/negative amounts and null fields at
construction. A `Transaction` groups a set of entries and enforces the core
double-entry invariant at construction time: debits must equal credits, or
it throws `UnbalancedTransactionException`. An empty transaction is also
rejected. Both are covered by JUnit 5 tests written before the
implementation.

## 2026-08-11

Added `Account`, completing the Phase 1 domain model. Introduced an
`AccountType` enum (asset, liability, equity, revenue, expense) that
derives each account's `NormalBalance` (debit or credit) automatically, so
the direction balances move in follows real accounting rules and an
account can't be constructed in an invalid state (e.g. an asset with a
credit normal balance). `Account.balance(transactions)` sums the relevant
entries and nets them against the account's normal balance to produce the
running balance, rather than storing/mutating a balance field directly.

This closes out Phase 1: `LedgerEntry`, `Transaction`, and `Account`
together form a plain Java domain model with no I/O, ready to be wrapped
with Spring Data JPA persistence and a REST API in Phase 2.
