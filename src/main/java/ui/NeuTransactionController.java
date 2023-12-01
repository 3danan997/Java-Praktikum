package ui;

import com.bank.*;
import com.bank.exceptions.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

public class NeuTransactionController implements Initializable {
    Stage stage;
    Parent root;
    String kontoinhaber;


    public void init(String kontoinhaber) {
        this.kontoinhaber = kontoinhaber;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    protected void paymentBtn(ActionEvent event) throws IOException {
        this.datenWeitergeben(event,"Payment.fxml");
    }
    @FXML
    protected void transferBtn(ActionEvent event) throws IOException {
        this.datenWeitergeben(event,"Transfer.fxml");
    }
    @FXML
    protected void abbrechen(ActionEvent event) throws IOException {
        this.datenWeitergeben(event,"KontoView.fxml");
    }
    @FXML
    protected void datenWeitergeben(ActionEvent event,String fxmlView) throws IOException {
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(fxmlView)));
        root = loader.load();
        if (fxmlView.equals("Payment.fxml")){
            PaymentController paymentController = loader.getController();
            paymentController.init(kontoinhaber);
        } else if (fxmlView.equals("Transfer.fxml")) {
            TransferController transferController = loader.getController();
            transferController.init(kontoinhaber);
        } else if (fxmlView.equals("KontoView.fxml")) {
            KontoViewController kontoViewController = loader.getController();
            kontoViewController.DisplayName(kontoinhaber);
        }


        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
