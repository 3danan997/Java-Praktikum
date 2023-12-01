package com.bank.exceptions;

public class TransactionAttributeException extends Exception {
    public TransactionAttributeException(String fehlermeldung) {
        super(fehlermeldung);
    }
}
