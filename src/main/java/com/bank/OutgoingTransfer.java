package com.bank;

import com.bank.exceptions.TransferAmountException;

public class OutgoingTransfer extends Transfer {
    public OutgoingTransfer(String date, double amount, String description, String sender, String recipient) throws TransferAmountException {
        super(date, amount, description, sender, recipient);
    }
    @Override
    public double calculate() {
        return -1 * this.getAmount();
    }
}
