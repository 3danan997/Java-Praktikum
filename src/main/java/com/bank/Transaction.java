package com.bank;

import com.bank.exceptions.TransferAmountException;

import java.util.Objects;

/**
 * The type Transaction.
 */
public abstract class Transaction implements CalculateBill {
    /**
     * Attribute zum Speichern des Datums
     */
    protected String date;
    /**
     * Attribute für Betrag
     */
    protected double amount;
    /**
     * Attribute zur Beschreibung des Betrags
     */
    protected String description;

    /**
     * Parameterloser Konstruktor
     */
    public Transaction() {}

    /**
     * Konstruktor mit allen Parametern
     *
     * @param date        Datum des Transactions
     * @param amount      Betrag des Transactions
     * @param description Beschriebung des Transactions
     */
    public Transaction(String date, double amount, String description) throws TransferAmountException {
        this.setDate(date);
        this.setAmount(amount);
        this.setDescription(description);
    }

    /**
     * Kopie Konstruktor
     *
     * @param kopie Objekt, das kopiert wird
     */
    public Transaction(Transaction kopie) throws TransferAmountException {
        this(kopie.date, kopie.amount, kopie.description);
    }

    /**
     * Getter für Datum
     *
     * @return Datum des Transactions
     */
    public String getDate() {
        return date;
    }

    /**
     * Setter für Datum
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Getter für Betrag
     *
     * @return den Betrag des Transactions
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Setter für Betrag
     *
     * @param amount the amount
     */
    public void setAmount(double amount) throws TransferAmountException {
        this.amount = amount;
    }

    /**
     * Getter für Beschreibung
     *
     * @return die Beschriebung des Transactions
     */
    public String getDescription() {
        return description;
    }

    /**
     * Setter für Beschreibung
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Methode um alle Klasseninhalt rückzugeben
     * @return alle informationen des Transactions
     */
    @Override
    public String toString() {
        return "Date: " + getDate() + "\n" +
                "Amount: " + calculate() + "\n" +
                "Description: " + getDescription() + "\n";
    }

    /**
     *Überprüfung sämtliche Klassenattribute
     * @param obj Ein Objekt vom gleichen Typ der Klasse
     * @return True oder Falls
     */
    @Override
    public boolean equals(Object obj){
        if (obj == null) return false;//null überprüfen
        if (getClass() != obj.getClass()) return false;//Typ überprüfen und Cast
        if (this == obj) return true; //Selbstüberprüfen
        Transaction tmp=(Transaction) obj;
        return ((tmp.getAmount()==this.getAmount()) && (Objects.equals(tmp.getDate(), this.getDate())) &&
                (Objects.equals(tmp.getDescription(), this.getDescription())));
    }
}
