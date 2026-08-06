# zerosum

A double-entry bookkeeping ledger engine, built incrementally while learning
Java and Spring Boot.

Money is never edited in place, only moved between accounts as balanced
transactions (every transaction's debits must equal its credits). Starts as
a plain Java domain model and grows into a Spring Boot + PostgreSQL API as
the underlying skills come online.

See [docs/superpowers/specs/2026-08-06-zerosum-design.md](docs/superpowers/specs/2026-08-06-zerosum-design.md)
for the full design, and [docs/DEVLOG.md](docs/DEVLOG.md) for a running log
of what was built and why.
