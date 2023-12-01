package ui;

import com.bank.*;
import com.bank.exceptions.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.format.DateTimeFormatter;
import java.util.*;

public class KontoViewController implements Initializable {
    PrivateBank iBank = new PrivateBank("iBank", 0.03, 0.05, System.getProperty("user.dir") + "\\");
    ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
    Stage stage;
    Scene scene;
    Parent root;
    @FXML
    private ListView<Transaction> listView = new ListView<>();
    @FXML
    protected Label kontoinhaber = new Label();
    static String kontoname;
    @FXML
    private Label kontostand;
    @FXML
    private ChoiceBox<Object> sort = new ChoiceBox<>();

    public KontoViewController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //ChoiceBox init
        if (sort.getValue() == null) {
            sort.getItems().addAll("Keine", new Separator(), "Aufsteigende Sortierung", "Absteigende Sortierung", new Separator(), "Positive Beträge", "Negative Beträge");
            sort.getSelectionModel().select(0);
        }
    }

    public void DisplayName(String _name) {
        kontoinhaber.setText(_name);
        kontoname=_name;
        if (iBank.getAccountsToTransactions().get(kontoinhaber.getText()) != null) {
            kontostand.setText(String.format("%.2f", iBank.getAccountBalance(kontoinhaber.getText())) + " €");
            transactionsList = FXCollections.observableList(iBank.getAccountsToTransactions().get(kontoinhaber.getText()));
        }
        listView.setItems(transactionsList);
    }

    @FXML
    protected void zurueckZuKontoanzeigen(ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("KontoAnzeigen.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void handleLoeschen(ActionEvent event) throws AccountDoesNotExistException, TransactionDoesNotExistException, IOException {
        final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
        AlertFunc msg = new AlertFunc();
        if (selectedIdx != -1) {
            Transaction selectedTransaction = listView.getSelectionModel().getSelectedItem();

            final int newSelectedIdx = (selectedIdx == listView.getItems().size() - 1) ? selectedIdx - 1 : selectedIdx;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Transaction löschen");
            alert.setContentText("Sind Sie sicher, dass Sie dieses Transaction löschen möchten?");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                iBank.removeTransaction(kontoinhaber.getText(), selectedTransaction);
                handleSort();
                listView.getSelectionModel().select(newSelectedIdx);
                kontostand.setText(String.format("%.2f", iBank.getAccountBalance(kontoinhaber.getText())) + " €");
            } else {
                msg.informationAlert("löschen abgebrochen", "Die Löschung wurde abgebrochen");
            }
        } else {
            msg.errorAlert("Fehler!", "Bitte ein gültige Feld auswählen!");
        }
    }

    @FXML
    protected void handleSort() {
        if (!transactionsList.isEmpty()) {
            if (sort.getValue().equals("Keine"))
                transactionsList = FXCollections.observableList(iBank.getAccountsToTransactions().get(kontoinhaber.getText()));
            else if (sort.getValue().equals("Aufsteigende Sortierung"))
                transactionsList = FXCollections.observableList(iBank.getTransactionsSorted(kontoinhaber.getText(), true));
            else if (sort.getValue().equals("Absteigende Sortierung"))
                transactionsList = FXCollections.observableList(iBank.getTransactionsSorted(kontoinhaber.getText(), false));
            else if (sort.getValue().equals("Positive Beträge"))
                transactionsList = FXCollections.observableList(iBank.getTransactionsByType(kontoinhaber.getText(), true));
            else if (sort.getValue().equals("Negative Beträge"))
                transactionsList = FXCollections.observableList(iBank.getTransactionsByType(kontoinhaber.getText(), false));
            listView.setItems(transactionsList);
        }
    }

    @FXML
    protected void neuTransactionBtn(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("NeuTransaction.fxml")));
        root = loader.load();
        NeuTransactionController neuTransactionController=loader.getController();
        neuTransactionController.init(kontoinhaber.getText());
        stage=(Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
