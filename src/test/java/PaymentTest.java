import com.bank.Payment;
import com.bank.exceptions.IncomingInterestException;
import com.bank.exceptions.OutgoingInterestException;
import com.bank.exceptions.TransferAmountException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class PaymentTest {
    Payment p1;
    Payment p2;
    Payment p3;

    @BeforeEach
    void init() throws TransferAmountException, OutgoingInterestException, IncomingInterestException {
        p1 = new Payment("9.12.2022", 150, "D1");
        p2 = new Payment("12.12.2022", 400, "D2", 0.01, 0.02);
        p3 = new Payment("13.12.2022", -500, "D3", 0.003, 0.01);
    }

    @Test
    @DisplayName("Kopie Konstruktor")
    public void testKopie() throws TransferAmountException, OutgoingInterestException, IncomingInterestException {
        Payment Pay3 = new Payment(p1);
        Assertions.assertEquals(Pay3.getAmount(), 150);
        Assertions.assertEquals(Pay3.getDate(), "9.12.2022");
        Assertions.assertEquals(Pay3.getDescription(), "D1");
    }

    @Test
    @DisplayName("Payment Konstruktor")
    public void testConstructor() {
        Assertions.assertEquals(p1.getAmount(), 150);
        Assertions.assertEquals(p1.getDate(), "9.12.2022");
        Assertions.assertEquals(p1.getDescription(), "D1");

        Assertions.assertEquals(p2.getIncomingInterest(), 0.01);
        Assertions.assertEquals(p2.getOutgoingInterest(), 0.02);
    }

    @ParameterizedTest
    @DisplayName("Payment Calculate Methode")
    @ValueSource(doubles = {0.01, 0.02, 0.05})
    public void testCalculate(double num) throws TransferAmountException, OutgoingInterestException, IncomingInterestException {
        Payment testPayment = new Payment(p2);
        testPayment.setIncomingInterest(num);
        testPayment.setOutgoingInterest(num);
        Assertions.assertEquals(testPayment.calculate(), p2.getAmount() - p2.getAmount() * num);
    }

    @Test
    @DisplayName("Payment equals Methode")
    public void testEquals() throws TransferAmountException, OutgoingInterestException, IncomingInterestException {
        Payment copyPay1 = new Payment(p1);
        Assertions.assertEquals(copyPay1, p1);
        Assertions.assertNotEquals(p2, p3);
    }

    @Test
    @DisplayName("Payment toString Methode")
    public void testToString() {
        Assertions.assertEquals(p1.toString(), "Date: 9.12.2022\n" +
                "Amount: 150.0\n" +
                "Description: D1\n" +
                "IncomingInterest: 0.0\n" +
                "OutgoingInterest: 0.0\n");
    }
}
