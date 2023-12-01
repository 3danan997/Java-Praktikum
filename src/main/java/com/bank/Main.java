package com.bank;

import com.bank.exceptions.*;

import java.io.IOException;
import java.util.List;


public class Main {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        try {
            String dir=System.getProperty("user.dir")+"\\";
            PrivateBank iBank = new PrivateBank("iBank", 0.03, 0.05,dir);
            //Payment Klasse testen
            Payment a0 = new Payment("11.11.2011", 1000, "Dein Gehalt");
            iBank.addTransaction("Adnan",a0);
//            Payment a1 = new Payment("10.05.2022", 2000, "Gehalt", 0.05, 0);
//            Payment a2 = new Payment(a1);
//            Payment a3 = new Payment("10.06.2062", -400, "Geschenk", 0, 0.02);
//            Transfer ot1 = new OutgoingTransfer("20.02.2022", 100, "Ausleihe1", "Adnan", "Ali");
//            Transfer ot2 = new OutgoingTransfer("20.02.2022", 400, "Geschenk1", "Adnan", "Ali");
//            Transfer ot3 = new OutgoingTransfer("20.02.2022", 600, "Ausleihe2", "Ali", "Adnan");
//            Transfer it1 = new IncomingTransfer("20.02.2022", 100, "Ausleihe1", "Adnan", "Ali");
//            Transfer it2 = new IncomingTransfer("20.02.2022", 400, "Geschenk1", "Adnan", "Ali");
//            Transfer it3 = new IncomingTransfer("20.02.2022", 600, "Ausleihe2", "Ali", "Adnan");

            //Konto erstellen
//            iBank.createAccount("Ali",List.of(a1,a3,it1,it2,ot3));
//            iBank.createAccount("Adnan",List.of(a0,a1,a3,ot1,ot2,it3));
//            //------------------------------------------
//            //Sortierung
//            List<Transaction> tmp = iBank.getTransactions("Adnan");
////            tmp=iBank.getTransactionsSorted("Adnan",true);
////            tmp=iBank.getTransactionsSorted("Adnan",false);
////            tmp=iBank.getTransactionsByType("Adnan",true);
////            tmp=iBank.getTransactionsByType("Adnan",false);
//            System.out.println("Adnan Konto:\n" + tmp.toString().replace("[", "").replace("]", "").replace("\n, ", "\n\n"));
//            //------------------------------------------
//
//            System.out.println(iBank);
//            System.out.println("Adnan Kontostand:\n" + iBank.getAccountBalance("Adnan") + "\n");
//            System.out.println("Ali Kontostand:\n" + iBank.getAccountBalance("Ali") + "\n");
//            //Transaction löschen
//            if (iBank.containsTransaction("Adnan", ot2))
//                iBank.removeTransaction("Adnan", ot2);
//            if (iBank.containsTransaction("Adnan", ot2))//
//                iBank.addTransaction("Adnan", ot2);
//            if (iBank.containsTransaction("Ali", it2))
//                iBank.removeTransaction("Ali", it2);
//
//            System.out.println("Adnan neues Kontostand:\n" + iBank.getAccountBalance("Adnan"));
//            System.out.println("Ali neues Kontostand:\n" + iBank.getAccountBalance("Ali"));
//
//            //Equals testen
//            PrivateBank iiBank = new PrivateBank(iBank);
//            iiBank.createAccount("Adnan", iBank.getTransactions("Adnan"));
//            System.out.println(iiBank.equals(iBank));
//            iiBank.createAccount("Ali", iBank.getTransactions("Ali"));
//            System.out.println(iiBank.equals(iBank));
//
//            //PrivatBankAlt
//            Transfer t1=new Transfer("10.11.2022", 400, "Geschenk1", "adnan", "ali");
//            PrivateBankAlt altBank=new PrivateBankAlt("altBank",0.1,0.1);
//            altBank.createAccount("adnan",List.of(a0,a1));
//            altBank.createAccount("ali",List.of(a0,a1));
//
//
//            System.out.println("Kontostand ohne Transfer:\n"+altBank.getAccountBalance("adnan"));
//            System.out.println(altBank.getAccountBalance("ali"));
//            altBank.addTransaction("adnan",t1);
//            altBank.addTransaction("ali",t1);
//            System.out.println("Kontostand nach Transfer:\n"+altBank.getAccountBalance("adnan"));
//            System.out.println(altBank.getAccountBalance("ali"));
//
////            List<String>neu=iBank.getAllAccounts();
////            System.out.println(neu);
//            PrivateBank iBank= new PrivateBank("iBank",0.01,0.01,dir);
//            Payment a1 = new Payment("1.08.2021", 2000, "Gehalt", 0.05, 0);
//            Transfer it1 = new IncomingTransfer("20.02.2022", 100, "Ausleihe1", "Ali", "Adnan");
////            Transfer ot1 = new OutgoingTransfer("21.02.2022", 80, "Ausleihe2", "Adnan", "Ali");
//            iBank.createAccount("Adnan",List.of(a1,it1));
////            iBank.addTransaction("Adnan",a1);
//            List<Transaction> tmp = iBank.getTransactions("Adnan");
//            System.out.println("Adnan Konto:\n" + tmp.toString().replace("[", "").replace("]", "").replace("\n, ", "\n\n"));
//            System.out.println(iBank.getAccountBalance("Adnan"));
//            PrivateBank iiBank=new PrivateBank(iBank);
//            PrivateBank iiiBank=new PrivateBank(iBank);
//            System.out.println(iiBank.equals(iiiBank));
//            tmp = iiBank.getTransactions("Adnan");
//            System.out.println("Adnan Konto:\n" + tmp.toString().replace("[", "").replace("]", "").replace("\n, ", "\n\n"));
            iBank.deleteAccount("Adnan1");
        } catch (IOException | AccountDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (TransferAmountException e) {
            throw new RuntimeException(e);
        } catch (TransactionAlreadyExistException e) {
            throw new RuntimeException(e);
        } catch (TransactionAttributeException e) {
            throw new RuntimeException(e);
        } catch (OutgoingInterestException e) {
            throw new RuntimeException(e);
        } catch (IncomingInterestException e) {
            throw new RuntimeException(e);
        }
    }
}