package com.jonsuguiyama.zerosum.ledger;

import java.math.BigDecimal;
import java.util.Objects;

public final class LedgerEntry {

    private final String accountId;
    private final EntryType type;
    private final BigDecimal amount;

    public LedgerEntry(String accountId, EntryType type, BigDecimal amount) {
        Objects.requireNonNull(accountId, "accountId must not be null");
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(amount, "amount must not be null");
        if (amount.signum() <= 0) {
            throw new IllegalArgumentException("amount must be positive, got: " + amount);
        }
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
    }

    public String getAccountId() {
        return accountId;
    }

    public EntryType getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
