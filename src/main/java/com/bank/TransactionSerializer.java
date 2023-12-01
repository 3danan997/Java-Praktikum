package com.bank;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

public class TransactionSerializer implements JsonSerializer<Transaction> {
    @Override
    public JsonElement serialize(Transaction transaction, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonClassname = new JsonObject();
        JsonObject jsonInstance = new JsonObject();

        String classname = transaction.getClass().getSimpleName();
        jsonClassname.addProperty("CLASSNAME", classname);

        if (transaction instanceof Payment) {
            jsonInstance.addProperty("incomingInterest", ((Payment) transaction).getIncomingInterest());
            jsonInstance.addProperty("outgoingInterest", ((Payment) transaction).getOutgoingInterest());
        }
        else if (transaction instanceof Transfer) {
            jsonInstance.addProperty("sender", ((Transfer) transaction).getSender());
            jsonInstance.addProperty("recipient", ((Transfer) transaction).getRecipient());
        }
        jsonInstance.addProperty("date",transaction.getDate());
        jsonInstance.addProperty("amount",transaction.calculate());
        jsonInstance.addProperty("description",transaction.getDescription());

        jsonClassname.add("INSTANCE",jsonInstance);

        return jsonClassname;
    }
}
