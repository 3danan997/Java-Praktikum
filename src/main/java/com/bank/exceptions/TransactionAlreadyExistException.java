package com.bank.exceptions;

public class TransactionAlreadyExistException extends Exception {
    public TransactionAlreadyExistException(String fehlermeldung) {
        super(fehlermeldung);
    }
}
