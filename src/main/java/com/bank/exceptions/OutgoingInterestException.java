package com.bank.exceptions;

public class OutgoingInterestException extends Exception {
    public OutgoingInterestException(String fehlermeldung){
        super(fehlermeldung);
    }
}
