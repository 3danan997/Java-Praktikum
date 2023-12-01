package com.bank;

import com.bank.exceptions.IncomingInterestException;
import com.bank.exceptions.OutgoingInterestException;
import com.bank.exceptions.TransferAmountException;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TransactionDeserializer implements JsonDeserializer<Transaction> {
    @Override
    public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        String classname = jsonElement.getAsJsonObject().get("CLASSNAME").getAsString();
        String date = jsonElement.getAsJsonObject().getAsJsonObject("INSTANCE").get("date").getAsString();
        double amount = jsonElement.getAsJsonObject().getAsJsonObject("INSTANCE").get("amount").getAsDouble();
        String description = jsonElement.getAsJsonObject().getAsJsonObject("INSTANCE").get("description").getAsString();
        if (classname.equals("Payment")){
            double incomingInterest = jsonElement.getAsJsonObject().getAsJsonObject("INSTANCE").get("incomingInterest").getAsDouble();
            double outgoingInterest = jsonElement.getAsJsonObject().getAsJsonObject("INSTANCE").get("outgoingInterest").getAsDouble();
            Payment tmp = null;
            try {
                tmp = new Payment(date, amount, description, incomingInterest, outgoingInterest);
            } catch (IncomingInterestException | OutgoingInterestException | TransferAmountException e) {
                throw new RuntimeException(e);
            }
            return tmp;
        }
        else if (classname.equals("IncomingTransfer")){
            String sender = jsonElement.getAsJsonObject().getAsJsonObject("INSTANCE").get("sender").getAsString();
            String recipient = jsonElement.getAsJsonObject().getAsJsonObject("INSTANCE").get("recipient").getAsString();
            IncomingTransfer tmp = null;
            try {
                tmp = new IncomingTransfer(date, amount, description, sender, recipient);
            } catch (TransferAmountException e) {
                throw new RuntimeException(e);
            }
            return  tmp;
        } else if (classname.equals("OutgoingTransfer")) {
            String sender = jsonElement.getAsJsonObject().getAsJsonObject("INSTANCE").get("sender").getAsString();
            String recipient = jsonElement.getAsJsonObject().getAsJsonObject("INSTANCE").get("recipient").getAsString();
            OutgoingTransfer tmp = null;
            try {
                tmp = new OutgoingTransfer(date, -amount, description, sender, recipient);
            } catch (TransferAmountException e) {
                throw new RuntimeException(e);
            }
            return  tmp;
        }

        return null;
    }
}
