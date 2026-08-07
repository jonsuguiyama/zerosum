package com.jonsuguiyama.zerosum.ledger;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransactionTest {

    @Test
    void acceptsBalancedEntries() {
        LedgerEntry debit = new LedgerEntry("cash", EntryType.DEBIT, new BigDecimal("50.00"));
        LedgerEntry credit = new LedgerEntry("revenue", EntryType.CREDIT, new BigDecimal("50.00"));

        Transaction transaction = new Transaction(List.of(debit, credit));

        assertEquals(2, transaction.getEntries().size());
    }

    @Test
    void rejectsUnbalancedEntries() {
        LedgerEntry debit = new LedgerEntry("cash", EntryType.DEBIT, new BigDecimal("50.00"));
        LedgerEntry credit = new LedgerEntry("revenue", EntryType.CREDIT, new BigDecimal("40.00"));

        assertThrows(UnbalancedTransactionException.class, () ->
            new Transaction(List.of(debit, credit)));
    }

    @Test
    void rejectsEmptyEntryList() {
        assertThrows(IllegalArgumentException.class, () -> new Transaction(List.of()));
    }
}
