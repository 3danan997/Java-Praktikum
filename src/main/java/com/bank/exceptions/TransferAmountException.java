package com.bank.exceptions;

public class TransferAmountException extends Exception{
    public TransferAmountException(String fehlermeldung){
        super(fehlermeldung);
    }
}
