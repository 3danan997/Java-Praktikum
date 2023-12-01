package com.bank;

import com.bank.exceptions.TransferAmountException;

public class IncomingTransfer extends Transfer {
    public IncomingTransfer(String date, double amount, String description, String sender, String recipient) throws TransferAmountException {
        super(date, amount, description, sender, recipient);
    }
    @Override
    public double calculate() {
        return this.getAmount();
    }
}
