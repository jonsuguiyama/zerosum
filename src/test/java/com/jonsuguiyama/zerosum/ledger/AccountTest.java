package com.jonsuguiyama.zerosum.ledger;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    @Test
    void balanceIsZeroWithNoTransactions() {
        Account cash = new Account("cash", "Cash", AccountType.ASSET);

        assertEquals(BigDecimal.ZERO, cash.balance(List.of()));
    }

    @Test
    void debitIncreasesADebitNormalAccount() {
        Account cash = new Account("cash", "Cash", AccountType.ASSET);
        Account revenue = new Account("revenue", "Revenue", AccountType.REVENUE);

        Transaction sale = new Transaction(List.of(
            new LedgerEntry("cash", EntryType.DEBIT, new BigDecimal("100.00")),
            new LedgerEntry("revenue", EntryType.CREDIT, new BigDecimal("100.00"))
        ));

        assertEquals(new BigDecimal("100.00"), cash.balance(List.of(sale)));
        assertEquals(new BigDecimal("100.00"), revenue.balance(List.of(sale)));
    }

    @Test
    void creditDecreasesADebitNormalAccount() {
        Account cash = new Account("cash", "Cash", AccountType.ASSET);

        Transaction refund = new Transaction(List.of(
            new LedgerEntry("revenue", EntryType.DEBIT, new BigDecimal("20.00")),
            new LedgerEntry("cash", EntryType.CREDIT, new BigDecimal("20.00"))
        ));

        assertEquals(new BigDecimal("-20.00"), cash.balance(List.of(refund)));
    }

    @Test
    void onlyCountsEntriesForThisAccount() {
        Account cash = new Account("cash", "Cash", AccountType.ASSET);

        Transaction unrelated = new Transaction(List.of(
            new LedgerEntry("other", EntryType.DEBIT, new BigDecimal("10.00")),
            new LedgerEntry("another", EntryType.CREDIT, new BigDecimal("10.00"))
        ));

        assertEquals(BigDecimal.ZERO, cash.balance(List.of(unrelated)));
    }

    @Test
    void exposesTypeAndDerivedNormalBalance() {
        Account cash = new Account("cash", "Cash", AccountType.ASSET);

        assertEquals(AccountType.ASSET, cash.getType());
        assertEquals(NormalBalance.DEBIT, cash.getNormalBalance());
    }
}
