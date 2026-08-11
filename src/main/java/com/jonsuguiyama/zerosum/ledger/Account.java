package com.jonsuguiyama.zerosum.ledger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public final class Account {

    private final String id;
    private final String name;
    private final AccountType type;
    private final NormalBalance normalBalance;

    public Account(String id, String name, AccountType type) {
        Objects.requireNonNull(id, "id must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(type, "type must not be null");
        this.id = id;
        this.name = name;
        this.type = type;
        this.normalBalance = type.getNormalBalance();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public AccountType getType() {
        return type;
    }

    public NormalBalance getNormalBalance() {
        return normalBalance;
    }

    public BigDecimal balance(List<Transaction> transactions) {
        BigDecimal debits = BigDecimal.ZERO;
        BigDecimal credits = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            for (LedgerEntry entry : transaction.getEntries()) {
                if (!entry.getAccountId().equals(id)) {
                    continue;
                }
                if (entry.getType() == EntryType.DEBIT) {
                    debits = debits.add(entry.getAmount());
                } else {
                    credits = credits.add(entry.getAmount());
                }
            }
        }

        return normalBalance == NormalBalance.DEBIT
            ? debits.subtract(credits)
            : credits.subtract(debits);
    }
}
