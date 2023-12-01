package com.bank.exceptions;

public class IncomingInterestException extends Exception {
    public IncomingInterestException(String fehlermeldung){
        super(fehlermeldung);
    }
}
