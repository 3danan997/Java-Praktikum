package ui;

import com.bank.IncomingTransfer;
import com.bank.OutgoingTransfer;
import com.bank.PrivateBank;
import com.bank.Transfer;
import com.bank.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class TransferController implements Initializable {
    PrivateBank iBank = new PrivateBank("iBank", 0.03, 0.05, System.getProperty("user.dir") + "\\");
    Stage stage;
    Parent root;
    String kontoinhaber;
    @FXML
    private TextField transferBetrag;
    @FXML
    private TextArea transferDesc;
    @FXML
    private DatePicker transferDatum = new DatePicker();
    @FXML
    private TextField transferEmpfaenger;

    public TransferController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        transferDatum.setValue(LocalDate.now());
        transferDatum.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });
    }

    public void init(String name) {
        kontoinhaber = name;
    }

    @FXML
    protected void senden(ActionEvent event) throws IOException {
        AlertFunc msg = new AlertFunc();
        if (transferEmpfaenger.getText().trim().isEmpty())
            msg.errorAlert("Fehler!", "Sie müssen zuerst das Empfänger Feld eingeben.");
        else if (transferBetrag.getText().trim().isEmpty())
            msg.errorAlert("Fehler!", "Sie müssen zuerst das Betrag Feld eingeben.");
        else {
            try {
                double betrag = Double.parseDouble(transferBetrag.getText());
                Transfer outTransfer = new OutgoingTransfer(transferDatum.getEditor().getText(), betrag, transferDesc.getText(), kontoinhaber, transferEmpfaenger.getText());
//                System.out.println("Empfänger -> " + transferEmpfaenger.getText() + "\n" + "absender -> " + kontoinhaber);
//                System.out.println(iBank.getAccountsToTransactions().containsKey(transferEmpfaenger.getText()));
                iBank.addTransaction(kontoinhaber, outTransfer);
                if (iBank.getAccountsToTransactions().containsKey(transferEmpfaenger.getText())) {
                    Transfer inTransfer = new IncomingTransfer(transferDatum.getEditor().getText(), betrag, transferDesc.getText(), kontoinhaber, transferEmpfaenger.getText());
                    iBank.addTransaction(transferEmpfaenger.getText(), inTransfer);
                }
                abbrechen(event);
            } catch (TransactionAlreadyExistException | AccountDoesNotExistException |
                     TransactionAttributeException | OutgoingInterestException | IOException |
                     IncomingInterestException e) {
                throw new RuntimeException(e);
            } catch (TransferAmountException e) {
                msg.errorAlert("Fehler!", "Der eingegebene Betrag ist ungültig.");
            } catch (NumberFormatException e) {
                msg.errorAlert("Fehler!", "Nur Ziffern sind erlaubt!");
                transferBetrag.clear();
            }
        }
    }

    @FXML
    protected void zurueckZuTransaktion(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NeuTransaction.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
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
}
