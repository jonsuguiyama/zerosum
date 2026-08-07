package com.jonsuguiyama.zerosum.ledger;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LedgerEntryTest {

    @Test
    void createsEntryWithValidFields() {
        LedgerEntry entry = new LedgerEntry("acc-1", EntryType.DEBIT, new BigDecimal("100.00"));

        assertEquals("acc-1", entry.getAccountId());
        assertEquals(EntryType.DEBIT, entry.getType());
        assertEquals(new BigDecimal("100.00"), entry.getAmount());
    }

    @Test
    void rejectsZeroAmount() {
        assertThrows(IllegalArgumentException.class, () ->
            new LedgerEntry("acc-1", EntryType.DEBIT, BigDecimal.ZERO));
    }

    @Test
    void rejectsNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () ->
            new LedgerEntry("acc-1", EntryType.CREDIT, new BigDecimal("-5.00")));
    }

    @Test
    void rejectsNullAccountId() {
        assertThrows(NullPointerException.class, () ->
            new LedgerEntry(null, EntryType.DEBIT, BigDecimal.TEN));
    }
}
