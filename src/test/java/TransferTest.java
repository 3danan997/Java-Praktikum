import com.bank.*;
import com.bank.exceptions.TransferAmountException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class TransferTest {
    Transfer t1;
    Transfer t2;
    Transfer t3;
    OutgoingTransfer ot1;
    IncomingTransfer it1;
    @BeforeEach
    void init () throws TransferAmountException {
        t1 = new Transfer("9.12.2022", 150, "D1");
        t2 = new Transfer("10.12.2022", 400, "D2", "Adnan","Ali");
        t3 = new Transfer("11.12.2022", 500, "D3", "Ali","Adnan");
        ot1 = new OutgoingTransfer("12.12.2022", 200, "D4", "Ali","Adnan");
        it1 = new IncomingTransfer("13.12.2022", 300, "D5", "Ali","Adnan");
    }

    @Test
    @DisplayName("Testet den Copy Konstruktor")
    public void testCopy() throws TransferAmountException {
        Transfer tra4 = new Transfer(t1);
        Assertions.assertEquals(tra4.getAmount(),150 );
        Assertions.assertEquals(tra4.getDate(),"9.12.2022" );
        Assertions.assertEquals(tra4.getDescription(), "D1");
    }

    @Test
    @DisplayName("Testet ob der Konstruktor die richtigen Daten übergibt")
    public void testConstructor() {
        Assertions.assertEquals(t1.getAmount(),150 );
        Assertions.assertEquals(t1.getDate(),"9.12.2022" );
        Assertions.assertEquals(t1.getDescription(), "D1");
        Assertions.assertEquals(t2.getRecipient(),"Ali" );
        Assertions.assertEquals(t2.getSender(),"Adnan" );
    }

    @ParameterizedTest
    @DisplayName("Transfer Calculate Methode")
    @ValueSource( doubles = {500, 5000, 50000})
    public void testCalculate(double num) throws TransferAmountException {
        ot1.setAmount(num);
        it1.setAmount(num);
        Assertions.assertEquals(ot1.calculate(),-num );
        Assertions.assertEquals(it1.calculate(),num );
    }

    @Test
    @DisplayName("Testet ob equals die richtigen Werte liefert")
    public void testEquals() throws TransferAmountException {
        Transfer copyTra1 = new Transfer(t1);
        Assertions.assertEquals(copyTra1, t1);
        Assertions.assertNotEquals(t2, t3);
    }

    @Test
    @DisplayName("Testet ob die toString Methode den richtigen String zurückliefert")
    public void testToString() {
        Assertions.assertEquals(t1.toString(), "Date: 9.12.2022\n" +
                "Amount: 150.0\n" +
                "Description: D1\n" +
                "Sender: null\n" +
                "Recipient: null\n");
    }
}