package ui;

import com.bank.Payment;
import com.bank.PrivateBank;
import com.bank.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PaymentController {
    PrivateBank iBank = new PrivateBank("iBank", 0.03, 0.05, System.getProperty("user.dir") + "\\");
    Stage stage;
    Parent root;
    String kontoinhaber;
    @FXML
    private TextField paymentBetrag;
    @FXML
    private TextArea paymentDesc;
    @FXML
    private Label betragUnglutig;

    public PaymentController() throws IOException {
    }

    public void init(String name) {
//        System.out.println(name);
        kontoinhaber=name;
    }

    @FXML
    protected void einzahlung(ActionEvent event) throws IOException {
        if (paymentBetrag.getText().trim().isEmpty())
            betragUnglutig.setText("Sie müssen zuerst das Betrag Feld eingeben.");
        else {
            try {
                double betrag = Double.parseDouble(paymentBetrag.getText());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                Payment tmp = new Payment(dtf.format(java.time.LocalDate.now()), betrag, paymentDesc.getText());
                iBank.addTransaction(kontoinhaber, tmp);
                abbrechen(event);
            } catch (TransferAmountException | TransactionAlreadyExistException | AccountDoesNotExistException |
                     TransactionAttributeException | OutgoingInterestException | IOException |
                     IncomingInterestException e) {
                throw new RuntimeException(e);
            } catch (NumberFormatException e) {
                betragUnglutig.setText("Nur Ziffern sind erlaubt!");
                paymentBetrag.clear();
            }
        }

    }
    @FXML
    protected void abbrechen(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("KontoView.fxml")));
        root = loader.load();

        KontoViewController kontoViewController = loader.getController();
        kontoViewController.DisplayName(kontoinhaber);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    protected void zurueckZuTransaktion(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NeuTransaction.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
