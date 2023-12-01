package com.bank.exceptions;

public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(String fehlermeldung) {
        super(fehlermeldung);
    }
}
