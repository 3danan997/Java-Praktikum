package ui;

import com.bank.PrivateBank;
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
import java.util.*;

public class BankViewController implements Initializable {
    PrivateBank iBank=new PrivateBank("iBank", 0.03, 0.05, System.getProperty("user.dir") + "\\");

    @FXML
    private Label bankName = new Label();

    public BankViewController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bankName.setText(iBank.getName());
    }

    /**
     * KontoAnzeigen Stage aufrufen
     * @param actionEvent
     * @throws IOException
     */
    public void kontoAnzeigen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("KontoAnzeigen.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
