package com.jonsuguiyama.zerosum.ledger;

public enum AccountType {
    ASSET(NormalBalance.DEBIT),
    EXPENSE(NormalBalance.DEBIT),
    LIABILITY(NormalBalance.CREDIT),
    EQUITY(NormalBalance.CREDIT),
    REVENUE(NormalBalance.CREDIT);

    private final NormalBalance normalBalance;

    AccountType(NormalBalance normalBalance) {
        this.normalBalance = normalBalance;
    }

    public NormalBalance getNormalBalance() {
        return normalBalance;
    }
}
