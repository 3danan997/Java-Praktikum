package com.bank;

import com.bank.exceptions.IncomingInterestException;
import com.bank.exceptions.OutgoingInterestException;
import com.bank.exceptions.TransferAmountException;

/**
 * The type Payment.
 */
public class Payment extends Transaction {
    /**
     * Attribut für ankommende Zinsen
     */
    private double incomingInterest;
    /**
     * Attribut für ausgehende Zinsen
     */
    private double outgoingInterest;

    /**
     * Parameterloser Konstruktor
     */
    public Payment() {
    }

    /**
     * Konstruktor mit allen Parametern
     *
     * @param date        Datum des Payments
     * @param amount      Betrag des Payments
     * @param description Beschriebung des Payments
     */
    public Payment(String date, double amount, String description) throws TransferAmountException {
        super(date, amount, description);
    }

    /**
     * Konstruktor mit allen Parametern
     *
     * @param date             Datum des Payments
     * @param amount           Betrag des Payments
     * @param description      Beschriebung des Payments
     * @param incomingInterest Ankommende Zinsen des Payments
     * @param outgoingInterest Ausgehende Zinsen des Payments
     */
    public Payment(String date, double amount, String description, double incomingInterest, double outgoingInterest) throws IncomingInterestException, OutgoingInterestException, TransferAmountException {
        this(date, amount, description);
        this.setIncomingInterest(incomingInterest);
        this.setOutgoingInterest(outgoingInterest);
    }

    /**
     * Kopie Konstruktor
     *
     * @param kopie Objekt, das kopiert wird
     */
    public Payment(Payment kopie) throws TransferAmountException, IncomingInterestException, OutgoingInterestException {
        super(kopie);
        this.setIncomingInterest(kopie.incomingInterest);
        this.setOutgoingInterest(kopie.outgoingInterest);
    }

    /**
     * getter für IncomingInterest (Ankommende Zinsen)
     *
     * @return IncomingInterest (Ankommende Zinsen) als Double
     */
    public double getIncomingInterest() {
        return incomingInterest;
    }

    /**
     * Setter, der überprüft, ob den Wert des IncomingInterests zwischen 0 und 1 ist,
     * oder es wird eine Fehlermeldung ausgegeben.
     *
     * @param incomingInterest ankommende Zinsen des Payments
     */
    public void setIncomingInterest(double incomingInterest) throws IncomingInterestException {
        if (incomingInterest < 0 || incomingInterest > 1)
            throw new IncomingInterestException("Incoming Interest: Bitte geben Sie einen positiven Wert zwischen 0 und 1 ein!");

        this.incomingInterest = incomingInterest;
    }

    /**
     * Getter für OutgoingInterest (ausgehende Zinsen)
     *
     * @return ausgehende Zinsen
     */
    public double getOutgoingInterest() {
        return outgoingInterest;
    }

    /**
     * Setter, der überprüft, ob den Wert des OutgoingInterests zwischen 0 und 1 ist,
     * oder es wird eine Fehlermeldung ausgegeben.
     *
     * @param outgoingInterest ausgehende Zinsen
     */
    public void setOutgoingInterest(double outgoingInterest) throws OutgoingInterestException {
        if (outgoingInterest<0||outgoingInterest>1)
            throw new OutgoingInterestException("Outgoing Interest: Bitte geben Sie einen positiven Wert zwischen 0 und 1 ein!");
        this.outgoingInterest = outgoingInterest;
    }

    /**
     * Ziehen die ankommenden Zinsen ab, falls den Betrag positiv ist.<br>
     * addieren die ausgehenden Zinsen hinzu, falls den Betrag negativ ist.
     *
     * @return Der Betrag mit oder ohne Zinsen
     */
    @Override
    public double calculate() {
        double tmp = this.getAmount();
        if (this.getAmount() > 0 && this.getIncomingInterest() > 0) {
            tmp = this.getAmount() - this.getAmount() * this.getIncomingInterest();
        } else if (this.getAmount() < 0 && this.getOutgoingInterest() > 0) {
            tmp = this.getAmount() + this.getAmount() * this.getOutgoingInterest();
        }
        return tmp;
    }

    /**
     * Methode um alle Klasseninhalt rückzugeben.
     *
     * @return alle informationen des Payments
     */
    @Override
    public String toString() {
        return super.toString() +
                "IncomingInterest: " + getIncomingInterest() + "\n" +
                "OutgoingInterest: " + getOutgoingInterest() + "\n";
    }

    /**
     *Überprüfung sämtliche Klassenattribute
     * @param obj Ein Objekt vom gleichen Typ der Klasse
     * @return True oder Falls
     */
    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;//super überprüfen
        Payment tmp = (Payment) obj;
        return ((tmp.getOutgoingInterest() == this.getOutgoingInterest()) &&
                (tmp.getIncomingInterest() == this.getIncomingInterest()));
    }
}
