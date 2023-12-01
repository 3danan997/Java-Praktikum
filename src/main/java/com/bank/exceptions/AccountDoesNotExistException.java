package com.bank.exceptions;

public class AccountDoesNotExistException extends Exception {
    public AccountDoesNotExistException(String fehlermeldung) {
        super(fehlermeldung);
    }
}
