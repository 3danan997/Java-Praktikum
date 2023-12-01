package ui;

import com.bank.PrivateBank;
import com.bank.exceptions.AccountAlreadyExistsException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class KontoEinrichtenController implements Initializable {
    PrivateBank iBank = new PrivateBank("iBank", 0.03, 0.05, System.getProperty("user.dir") + "\\");
    @FXML
    private TextField textField;
    @FXML
    private Label warn;

    public KontoEinrichtenController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Der Prozess von NeuKontoEinrichten abbrechen und zur KontoAnzeigen View zurückgehen
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void abbrechen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("KontoAnzeigen.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /**
     * Der Prozess von NeuKontoEinrichten bestätigen
     * @param event
     * @throws IOException
     */
    @FXML
    protected void submit(ActionEvent event) throws IOException {
        if (!textField.getText().trim().isEmpty()) {
            try {
                iBank.createAccount(textField.getText());
                abbrechen(event);
            } catch (AccountAlreadyExistsException e) {
                warn.setText("Konto ist bereits vorhanden");
            }
        } else
            warn.setText("Sie müssen zuerst einen Name eingeben!");
    }
}
