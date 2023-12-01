
import com.bank.*;
import com.bank.exceptions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PrivateBankTest {
    static PrivateBank testBank;
    static PrivateBank xBank;
    IncomingTransfer it1;
    Payment p1,p2,p3,p4;
    static String dir="C:\\Users\\adnan\\OneDrive\\Pictures\\FH Aachen\\OOS\\Praktikum\\OOS Praktikum\\";

    public PrivateBankTest() throws TransferAmountException {
    }

    @BeforeAll
    static void begin() throws IOException {
        testBank = new PrivateBank("testBank", 0.01, 0.02,dir);
        xBank = new PrivateBank("xBank", 0.04, 0.07,dir);
    }

    @BeforeEach
    void init()  {
        try {
            p1 = new Payment("10.11.2022", 100, "Geschenk", 0.01, 0.01);
            p2 = new Payment("5.12.2022", 400, "Geschenk1", 0.01,0.01);
            p3 = new Payment("6.12.2022", -200, "Auszahlen", 0.01,0.01);
            p4 = new Payment("7.12.2022", -160, "Auszahlen1", 0.03,0.04);
            it1 = new IncomingTransfer("8.12.22", 200, "ausleihe", "Ali", "Adnan");

            Assertions.assertDoesNotThrow(()-> testBank.createAccount("Adnan"));
            Assertions.assertDoesNotThrow(()->  testBank.addTransaction("Adnan", p1));
            Assertions.assertDoesNotThrow(()-> testBank.addTransaction("Adnan", p2));
            Assertions.assertDoesNotThrow(()-> testBank.addTransaction("Adnan", p3));
            Assertions.assertDoesNotThrow(()-> testBank.addTransaction("Adnan", it1));
        } catch (IncomingInterestException | OutgoingInterestException | TransferAmountException e) {
            throw new RuntimeException(e);
        }
    }
    @AfterEach
    void clean() {
        Assertions.assertDoesNotThrow(()->testBank.getAccountsToTransactions().remove("Adnan"));
        File deleteDir = new File(dir+testBank.getName()+"\\accounts");
        for (File file:deleteDir.listFiles()) {
            file.delete();
        }
        deleteDir.delete();
    }
    @Test
    @DisplayName("PrivateBank AccountAlreadyExistsException testen")
    void testCreateAccount() {
        Throwable exception = Assertions.assertThrows(AccountAlreadyExistsException.class, ()-> testBank.createAccount("Adnan"));
        Assertions.assertTrue(testBank.getAccountsToTransactions().containsKey("Adnan"));
    }


    @Test
    @DisplayName("PrivateBank equals Methode Test")
    public void testEquals() throws IOException {
        PrivateBank iiBank = new PrivateBank(testBank);
        PrivateBank iiiBank = new PrivateBank(testBank);
        Assertions.assertTrue(iiBank.equals(iiiBank));
        Assertions.assertFalse(iiBank.equals(xBank));
    }

    @Test
    @DisplayName("PrivateBank Konstruktor")
    public void testKonstruktor() {
        Assertions.assertEquals(testBank.getName(), "testBank");
        Assertions.assertEquals(testBank.getDirectory(), dir);
        Assertions.assertEquals(testBank.getIncominginterest(), 0.01);
        Assertions.assertEquals(testBank.getOutgoinginterest(), 0.02);
    }

    @Test
    @DisplayName("PrivateBank ToString Methode")
    void testToString() {
        Assertions.assertEquals(testBank.toString(),"Name: " + testBank.getName() + "\r\n" +
                "Incominginterest: " + testBank.getIncominginterest() + "\r\n" +
                "Outgoinginterest: " + testBank.getOutgoinginterest() + "\r\n" );
    }

    @Test
    @DisplayName("PrivateBank contaninsTransaction Methode")
    void containsTransaction() {
        Assertions.assertTrue(testBank.containsTransaction("Adnan", it1));
        Assertions.assertFalse(testBank.containsTransaction("Ali", it1));
    }

    @Test
    @DisplayName("Prüft ob die Transaction in der zurückgegebenen Liste enthalten sind")
    void getTransactions() {
        Assertions.assertTrue(testBank.getTransactions("Adnan").contains(p1));
        Assertions.assertTrue(testBank.getTransactions("Adnan").contains(p2));
        Assertions.assertTrue(testBank.getTransactions("Adnan").contains(p3));
        Assertions.assertTrue(testBank.getTransactions("Adnan").contains(it1));
    }

    @Test
    @DisplayName("TransactionsSorted Methode Testen")
    void getTransactionsSorted() {
        Assertions.assertEquals(p2, testBank.getTransactionsSorted("Adnan", false).get(0));
        Assertions.assertEquals(p3, testBank.getTransactionsSorted("Adnan", true).get(0));
    }

    @Test
    @DisplayName("TransactionsByType testen")
    void getTransactionsByType() {
        Assertions.assertFalse(testBank.getTransactionsByType("Adnan", true).contains(p3));
        Assertions.assertFalse(testBank.getTransactionsByType("Adnan", false).contains(it1));
    }

    @Test
    @DisplayName("getAccountBalance Methode testen")
    void getAccountBalance() {
        assertEquals(p1.calculate()+ p2.calculate()+ p3.calculate()+ it1.calculate(), testBank.getAccountBalance("Adnan"));
    }

    @Test
    @DisplayName("removeTransaction Methode")
    void removeTransaction() {
        Assertions.assertTrue(testBank.containsTransaction("Adnan", p1));
        Throwable exception = Assertions.assertThrows(TransactionDoesNotExistException.class, ()-> testBank.removeTransaction("Adnan", p4));
        Assertions.assertDoesNotThrow(()-> testBank.removeTransaction("Adnan", p1));
        Throwable exception1 = Assertions.assertThrows(AccountDoesNotExistException.class , ()-> testBank.removeTransaction("Ali", p1));
        Assertions.assertFalse(testBank.containsTransaction("Adnan", p1));
        Assertions.assertDoesNotThrow(()-> testBank.addTransaction("Adnan", p1));

    }
}