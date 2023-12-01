package com.bank;

import com.bank.exceptions.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Private bank.
 */
public class PrivateBank implements Bank {
    private String directory;
    private String name;
    private double incominginterest;
    private double outgoinginterest;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    public Map<String, List<Transaction>> getAccountsToTransactions() {
        return accountsToTransactions;
    }

    public void setAccountsToTransactions(Map<String, List<Transaction>> accountsToTransactions) {
        this.accountsToTransactions = accountsToTransactions;
    }

    /**
     * Kontoname und seine Liste von Transactions.
     */
    protected Map<String, List<Transaction>> accountsToTransactions = new HashMap<>();

    /**
     * Instanziierung einer neuen Privatbank.
     */
    public PrivateBank() {
    }

    /**
     * Instanziierung einer neuen Privatbank.
     *
     * @param name             the name
     * @param incominginterest the incominginterest
     * @param outgoinginterest the outgoinginterest
     */
    public PrivateBank(String name, double incominginterest, double outgoinginterest, String dir) throws IOException {
        this.setName(name);
        this.setIncominginterest(incominginterest);
        this.setOutgoinginterest(outgoinginterest);
        this.setDirectory(dir);
        readAccounts();
    }

    /**
     * Instanziierung einer neuen kopierten Privatbank.
     *
     * @param kopie the kopie
     */
    public PrivateBank(@NotNull PrivateBank kopie) throws IOException {
        this(kopie.getName(), kopie.getIncominginterest(), kopie.getOutgoinginterest(), kopie.getDirectory());
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
        return "Name: " + getName() + "\r\n" +
                "Incominginterest: " + getIncominginterest() + "\r\n" +
                "Outgoinginterest: " + getOutgoinginterest() + "\r\n";
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
        if (!tmp.getAccountsToTransactions().equals(this.getAccountsToTransactions()))
            return false;
        return (tmp.getName().equals(this.getName()) &&
                tmp.getIncominginterest() == this.getIncominginterest() &&
                tmp.getOutgoinginterest() == this.getOutgoinginterest() &&
                tmp.getDirectory().equals(this.getDirectory()));
    }

    @Override
    public void createAccount(String account) throws AccountAlreadyExistsException, IOException {
        if (accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException("Konto ist bereits vorhanden");

        accountsToTransactions.put(account, new ArrayList<Transaction>());
        writeAccounts(account);
    }

    @Override
    public void createAccount(String account, List<Transaction> transactions) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, AccountDoesNotExistException, OutgoingInterestException, IncomingInterestException, IOException {
        createAccount(account, transactions, false);
    }

    public void createAccount(String account, List<Transaction> transactions, boolean readMode) throws AccountAlreadyExistsException, TransactionAlreadyExistException, TransactionAttributeException, AccountDoesNotExistException, OutgoingInterestException, IncomingInterestException, IOException {
        if (accountsToTransactions.containsKey(account))
            throw new AccountAlreadyExistsException("Konto ist bereits vorhanden");
        if (accountsToTransactions.get(account) != null && accountsToTransactions.get(account).contains(transactions))
            throw new TransactionAlreadyExistException("Transaction ist bereits vorhanden");
        accountsToTransactions.put(account, new ArrayList<>());
        for (Transaction tmp : transactions) {
            this.addTransaction(account, tmp, readMode);
        }
    }

    @Override
    public void addTransaction(String account, Transaction transaction) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, OutgoingInterestException, IOException, IncomingInterestException {
        addTransaction(account, transaction, false);
    }

    public void addTransaction(String account, Transaction transaction, boolean readMode) throws TransactionAlreadyExistException, AccountDoesNotExistException, TransactionAttributeException, IncomingInterestException, OutgoingInterestException, IOException {
        if (this.containsTransaction(account, transaction))
            throw new TransactionAlreadyExistException("Transaction ist bereits vorhanden");
        if (accountsToTransactions.size() != 0 && !accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("Kein Konto mit diesem Namen: " + account + " gefunden");

        if (transaction.getClass().isAssignableFrom(Payment.class)) {
            ((Payment) transaction).setIncomingInterest(this.getIncominginterest());
            ((Payment) transaction).setOutgoingInterest(this.getOutgoinginterest());
        }

        accountsToTransactions.get(account).add(transaction);
        if (!readMode)
            writeAccounts(account);
    }

    @Override
    public void removeTransaction(String account, Transaction transaction) throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("Kein Konto mit diesem Namen: " + account + " gefunden");
        if (!this.containsTransaction(account, transaction))
            throw new TransactionDoesNotExistException("Keine Transaction mit dieser Werten gefunden");

        accountsToTransactions.get(account).remove(transaction);
        writeAccounts(account);
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
        if (!accountsToTransactions.get(account).isEmpty()) {
            for (Transaction tmp : accountsToTransactions.get(account)) {
                kontostand += tmp.calculate();
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

    private void readAccounts() throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        TransactionDeserializer test = new TransactionDeserializer();
        gsonBuilder.registerTypeHierarchyAdapter(Transaction.class, test);
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        Type listType = new TypeToken<List<Transaction>>() {
        }.getType();

        String pathname = this.getDirectory() + getName() + "\\accounts\\";
        File dir = new File(pathname);

        boolean mk;
        if (dir.mkdirs()) {
            mk = dir.createNewFile();
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(pathname))) {
            for (Path file : stream) {
                List<Transaction> accList = gson.fromJson(new FileReader(file.toFile()), listType);
                String filename = file.getFileName().toString().split(" ")[0];
                accountsToTransactions.put(filename, accList);
            }
        }
    }

    private void writeAccounts(String account) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        TransactionSerializer test = new TransactionSerializer();
        gsonBuilder.registerTypeHierarchyAdapter(Transaction.class, test);
        String pathname = this.getDirectory() + getName() + "\\accounts\\";

        File dir = new File(pathname);

        boolean mk;
        try {
            if (!dir.mkdirs())
                mk = dir.mkdirs();
            mk = dir.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Gson gson = gsonBuilder.setPrettyPrinting().create();
        String path = pathname + account + " Konto.json";
        File file = new File(path);
        String data = gson.toJson(accountsToTransactions.get(account));
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(data);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ein Konto löschen
     *
     * @param account der Kontoname
     * @throws AccountDoesNotExistException
     * @throws IOException
     */
    public void deleteAccount(String account) throws AccountDoesNotExistException, IOException {
        if (!accountsToTransactions.containsKey(account))
            throw new AccountDoesNotExistException("Kein Konto mit diesem Namen: " + account + " gefunden");
        accountsToTransactions.remove(account);
        try {
            File deleteFile = new File(System.getProperty("user.dir") + "\\" + this.getName() + "\\accounts\\" + account + " Konto.json");           //file to be delete
            if (deleteFile.delete())                      //returns Boolean value
            {
                System.out.println(deleteFile.getName() + " deleted");   //getting and printing the file name
            } else {
                System.out.println("failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        for (File file: Objects.toString(deleteFiles.listFiles())) {
//            if (file.getName().equals( account + " Konto.json")){
//                boolean res = file.delete();
//                if(res)
//                    System.out.println("File delete "+ account + " " + file.getName());
//                else
//                    System.out.println("File not deleted "+ account + " " + file.getName());
//            }
//        }
    }

    /**
     * @return List mit allen Konten im Bank
     */
    public List<String> getAllAccounts() {
        return new ArrayList<>(accountsToTransactions.keySet());
    }
}
