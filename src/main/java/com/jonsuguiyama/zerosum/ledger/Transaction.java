package com.jonsuguiyama.zerosum.ledger;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

public final class Transaction {

    private final List<LedgerEntry> entries;
    private final Instant postedAt;

    public Transaction(List<LedgerEntry> entries) {
        Objects.requireNonNull(entries, "entries must not be null");
        if (entries.isEmpty()) {
            throw new IllegalArgumentException("a transaction must have at least one entry");
        }

        BigDecimal totalDebits = sumByType(entries, EntryType.DEBIT);
        BigDecimal totalCredits = sumByType(entries, EntryType.CREDIT);

        if (totalDebits.compareTo(totalCredits) != 0) {
            throw new UnbalancedTransactionException(
                "debits (" + totalDebits + ") must equal credits (" + totalCredits + ")");
        }

        this.entries = List.copyOf(entries);
        this.postedAt = Instant.now();
    }

    private static BigDecimal sumByType(List<LedgerEntry> entries, EntryType type) {
        BigDecimal total = BigDecimal.ZERO;
        for (LedgerEntry entry : entries) {
            if (entry.getType() == type) {
                total = total.add(entry.getAmount());
            }
        }
        return total;
    }

    public List<LedgerEntry> getEntries() {
        return entries;
    }

    public Instant getPostedAt() {
        return postedAt;
    }
}
