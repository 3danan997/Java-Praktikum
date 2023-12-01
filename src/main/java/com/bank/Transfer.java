package com.bank;

import com.bank.exceptions.TransferAmountException;

import java.util.Objects;

/**
 * The type Transfer.
 */
public class Transfer extends Transaction {
    /**
     * Absender Attribut
     */
    private String sender;
    /**
     * Empfänger Attribut
     */
    private String recipient;

    /**
     * Parameterlose Konstruktor
     */
    public Transfer() {
    }

    /**
     * Konstruktor mit drei Parametern
     *
     * @param date        Datum des Transfers
     * @param amount      Betrag des Transfers
     * @param description Beschriebung des Transfers
     */
    public Transfer(String date, double amount, String description) throws TransferAmountException {
        super(date, amount, description);
    }

    /**
     * Konstruktor mit allen Parametern
     *
     * @param date        Datum des Transfers
     * @param amount      Betrag des Transfers
     * @param description Beschriebung des Transfers
     * @param sender      Absender des Transfers
     * @param recipient   Empfänger des Transfers
     */
    public Transfer(String date, double amount, String description, String sender, String recipient) throws TransferAmountException {
        super(date, amount, description);
        this.sender = sender;
        this.recipient = recipient;
    }

    /**
     * Kopie Konstruktor
     *
     * @param kopie Objekt, das kopiert wird
     */
    public Transfer(Transfer kopie) throws TransferAmountException {
        super(kopie);
        this.setSender(kopie.sender);
        this.setRecipient(kopie.recipient);
    }

    /**
     * Setter Überschreiben, damit sie überprüft, ob der Betrag positiv ist
     * oder es wird eine Fehlermeldung ausgegeben.
     *
     * @param amount Betrag, der überwiest wird
     */
    @Override
    public void setAmount(double amount) throws TransferAmountException {
        if (amount<0)
            throw new TransferAmountException("Transfer: Der eingegebene Betrag ist ungültig.");
        this.amount = amount;

    }

    /**
     * Hier wird nur den Betrag vom Transfer zurückgegeben.
     *
     * @return Betrag des Transfers
     */
    @Override
    public double calculate() {
        return this.getAmount();
    }

    /**
     * Getter für Absender
     *
     * @return Absender des Transfers
     */
    public String getSender() {
        return sender;
    }

    /**
     * Setter für Absender
     *
     * @param sender Absender des Transfers
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Getter für Empfänger
     *
     * @return Empfänger des Transfers
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Setter für Empfänger
     *
     * @param recipient Empfänger des Transfers
     */
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    /**
     * Methode um alle Klasseninhalt rückzugeben
     * @return alle informationen des Transfers
     */
    @Override
    public String toString() {
        return super.toString() +
                "Sender: " + getSender() + "\n" +
                "Recipient: " + getRecipient() + "\n";
    }

    /**
     *Überprüfung sämtliche Klassenattribute
     * @param obj Ein Objekt vom gleichen Typ der Klasse
     * @return True oder Falls
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Transfer tmp = (Transfer) obj;
        return ((Objects.equals(tmp.getRecipient(), this.getRecipient())) &&
                Objects.equals(tmp.getSender(), this.getSender()));
    }
}
