package com.bank;

import com.bank.exceptions.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class PrivateBankAlt implements Bank {
    private String name;
    private double incominginterest;
    private double outgoinginterest;
    /**
     * Kontoname und seine Liste von Transactions.
     */
    Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    /**
     * Instanziierung einer neuen Privatbank.
     */
    public PrivateBankAlt() {
    }

    /**
     * Instanziierung einer neuen Privatbank.
     *
     * @param name             the name
     * @param incominginterest the incominginterest
     * @param outgoinginterest the outgoinginterest
     */
    public PrivateBankAlt(String name, double incominginterest, double outgoinginterest) {
        this.setName(name);
        this.setIncominginterest(incominginterest);
        this.setOutgoinginterest(outgoinginterest);
    }

    /**
     * Instanziierung einer neuen kopierten Privatbank.
     *
     * @param kopie the kopie
     */
    public PrivateBankAlt(@NotNull PrivateBank kopie) {
        this(kopie.getName(), kopie.getIncominginterest(), kopie.getOutgoinginterest());
        //kopie.accountsToTransactions=this.accountsToTransactions;
    }

    /**
     * Gets name.
     *
     * @return der Name vom PrivatBank
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name der Name vom PrivatBank
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets incominginterest.
     *
     * @return die incominginterest vom PrivatBank
     */
    public double getIncominginterest() {
        return incominginterest;
    }

    /**
     * Sets incominginterest.
     *
     * @param incominginterest incominginterest vom PrivatBank
     */
    public void setIncominginterest(double incominginterest) {
        this.incominginterest = incominginterest;
    }

    /**
     * Gets outgoinginterest.
     *
     * @return outgoinginterest vom PrivatBank
     */
    public double getOutgoinginterest() {
        return outgoinginterest;
    }

    /**
     * Sets outgoinginterest.
     *
     * @param outgoinginterest outgoinginterest vom PrivatBank
     */
    public void setOutgoinginterest(double outgoinginterest) {
        this.outgoinginterest = outgoinginterest;
    }

    /**
     * Methode um alle Klasseninhalt zurückzugeben
     *
     * @return alle informationen des PrivatBanks
     */
    @Override
    public String toString() {
        return "Name: " + getName() + "\n" +
                "Incominginterest: " + getIncominginterest() + "\n" +
                "Outgoinginterest: " + getOutgoinginterest() + "\n";
    }

    /**
     * Überprüfung sämtliche Klassenattribute
     *
     * @param obj Ein Objekt vom gleichen Typ der Klasse
     * @return True oder False
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;//null überprüfen
        if (getClass() != obj.getClass()) return false;//Typ überprüfen und Cast
        if (this == obj) return true; //Selbstüberprüfen
        PrivateBank tmp = (PrivateBank) obj;
        return (tmp.getName().equals(this.getName()) &&
                tmp.getIncominginterest() == this.getIncominginterest() &&
                tmp.getOutgoinginterest() == this.getOutgoinginterest() &&
                tmp.accountsToTransactions.equals(this.accountsToTransactions));
    }

    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException {
        if (accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException("Konto ist bereits vorhanden");

        accountsToTransactions.put(account, new ArrayList<Transaction>());
    }

    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, AccountDoesNotExistException, OutgoingInterestException, IncomingInterestException {
        if (accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException("Konto ist bereits vorhanden");
        if (accountsToTransactions.get(account) != null && accountsToTransactions.get(account).contains(transactions))
            throw new TransactionAlreadyExistException("Transaction ist bereits vorhanden");

        accountsToTransactions.put(account, new ArrayList<>());
        for(Transaction tmp:transactions){
            this.addTransaction(account,tmp);
        }
    }

    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IncomingInterestException, OutgoingInterestException {
        if (accountsToTransactions.get(account).contains(transaction))
            throw new TransactionAlreadyExistException("Transaction ist bereits vorhanden");
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("Kein Konto mit diesem Namen: " + account + " gefunden");
//        if (accountsToTransactions.get(account).equals(transaction))
//            throw new TransactionAttributeException("Fehler bei der Validation von Transaction");

        if (transaction.getClass().isAssignableFrom(Payment.class)) {
            ((Payment) transaction).setIncomingInterest(this.getIncominginterest());
            ((Payment) transaction).setOutgoingInterest(this.getOutgoinginterest());
        }

        accountsToTransactions.get(account).add(transaction);
    }


    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException {
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("Kein Konto mit diesem Namen: " + account + " gefunden");
        if (!accountsToTransactions.get(account).contains(transaction))
            throw new TransactionDoesNotExistException("Keine Transaction mit dieser Werten gefunden");

        accountsToTransactions.get(account).remove(transaction);
    }

    @Override
    public boolean containsTransaction(String account, Transaction transaction) {
        if (accountsToTransactions.containsKey(account) && accountsToTransactions.get(account).contains(transaction))
            return true;
        return false;
    }

    @Override
    public double getAccountBalance(String account) {
        double kontostand = 0;
        for (Transaction tmp : accountsToTransactions.get(account)) {
            if (tmp instanceof Payment)
                kontostand += tmp.calculate();
            else if (tmp instanceof Transfer) {
                if (((Transfer) tmp).getSender().equals(account))
                    kontostand -= (tmp).calculate();
                else if (((Transfer) tmp).getRecipient().equals(account))
                    kontostand += (tmp).calculate();
            }
        }
        return kontostand;
    }

    @Override
    public List<Transaction> getTransactions(String account) {
        return accountsToTransactions.get(account);
    }

    @Override
    public List<Transaction> getTransactionsByType(String account, boolean positive) {
        List<Transaction> p = new ArrayList<>();//positive number
        List<Transaction> n = new ArrayList<>();//negative number
        accountsToTransactions.get(account).stream().forEach(i -> (i.calculate() < 0 ? n : p).add(i));
        return positive ? p : n;
    }

    @Override
    public List<Transaction> getTransactionsSorted(String account, boolean asc) {
        if (asc) {
            return this.accountsToTransactions.get(account).stream().sorted(Comparator.comparingDouble(Transaction::calculate)).collect(Collectors.toList());
        } else {
            return this.accountsToTransactions.get(account).stream().sorted(Comparator.comparingDouble(Transaction::calculate).reversed()).collect(Collectors.toList());
        }
    }
}
