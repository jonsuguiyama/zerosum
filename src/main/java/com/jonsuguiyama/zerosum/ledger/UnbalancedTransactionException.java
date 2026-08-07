package com.jonsuguiyama.zerosum.ledger;

public class UnbalancedTransactionException extends RuntimeException {
    public UnbalancedTransactionException(String message) {
        super(message);
    }
}
