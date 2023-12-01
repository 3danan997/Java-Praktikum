package ui;

import com.bank.PrivateBank;
import com.bank.exceptions.AccountDoesNotExistException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class KontoAnzeigenController implements Initializable {
    PrivateBank iBank = new PrivateBank("iBank", 0.03, 0.05, System.getProperty("user.dir") + "\\");
    ObservableList<String> accountsList = FXCollections.observableArrayList();
    @FXML
    private MenuItem auswaehlen;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ListView<String> listView = new ListView<>(accountsList);

    public KontoAnzeigenController() throws IOException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        accountsList.addAll(iBank.getAllAccounts());
//        System.out.println("Init\n"+accountsList);
        listView.setItems(accountsList);
    }

    /**
     * Zurück zur Hauptseite Stage gehen
     * @param actionEvent
     * @throws IOException
     */
    public void hauptSeite(javafx.event.ActionEvent actionEvent) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("BankView.fxml")));
        stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Das Konto beim dopple klicken öffnen
     * @param klick
     * @throws IOException
     */
    @FXML
    protected void doppleKlicken(MouseEvent klick) throws IOException {
        if (klick.getClickCount() == 2) {
            String tmp = listView.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("KontoView.fxml")));
            root = loader.load();

            KontoViewController kontoViewController = loader.getController();
            kontoViewController.DisplayName(tmp);

            stage = (Stage) ((Node) klick.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Behandelt das Menuitem Auswählen
     * @param actionEvent
     * @throws IOException
     */
    @FXML
    protected void handleAuswaehlen(ActionEvent actionEvent) throws IOException {
        String tmp = listView.getSelectionModel().getSelectedItem();

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("KontoView.fxml")));
        root = loader.load();

        KontoViewController kontoViewController = loader.getController();
        kontoViewController.DisplayName(tmp);

        stage = (Stage) auswaehlen.getParentPopup().getOwnerWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Behandelt das MenuItem Löschen
     * @throws AccountDoesNotExistException
     * @throws IOException
     */
    @FXML
    protected void handleLoeschen() throws AccountDoesNotExistException, IOException {
        final int selectedIdx = listView.getSelectionModel().getSelectedIndex();
        AlertFunc msg = new AlertFunc();
//        System.out.println(selectedIdx);
        if (selectedIdx != -1) {

            String selectedAccount = listView.getSelectionModel().getSelectedItem();
            final int newSelectedIdx = (selectedIdx == listView.getItems().size() - 1) ? selectedIdx - 1 : selectedIdx;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Konto löschen!");
            alert.setContentText("Sind Sie sicher, dass Sie dieses Konto " + selectedAccount + " löschen möchten?");
            Optional<ButtonType> answer = alert.showAndWait();
            if (answer.get() == ButtonType.OK) {
                System.out.println("Deleting account");
                iBank.deleteAccount(selectedAccount);
                accountsList.remove(selectedAccount);
                listView.getSelectionModel().select(newSelectedIdx);
            } else {
                msg.informationAlert("löschen abgebrochen", "Die Löschung wurde abgebrochen");
            }
        } else {
            msg.errorAlert("Fehler!", "Bitte ein gültiger Feld auswählen!");
        }
    }

    /**
     * Neues Konto erstellen
     * @param event
     * @throws IOException
     */
    @FXML
    protected void neuKontoEinrichten(ActionEvent event) throws IOException {
        FXMLLoader loader=new FXMLLoader(Objects.requireNonNull(getClass().getResource("KontoEinrichten.fxml")));
        root = loader.load();
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

//    @FXML
//    protected void aktualisieren(ActionEvent event) throws IOException {
//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("KontoAnzeigen.fxml")));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public void neueList(ObservableList<String> accountsList) {
//        this.accountsList = FXCollections.observableList(accountsList);
//        listView.setItems(this.accountsList);
//    }
}
