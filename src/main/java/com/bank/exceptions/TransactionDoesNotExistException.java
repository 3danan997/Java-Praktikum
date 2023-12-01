package com.bank.exceptions;

public class TransactionDoesNotExistException extends Exception {
    public TransactionDoesNotExistException(String fehlermeldung) {
        super(fehlermeldung);
    }
}
