package ui;

import javafx.scene.control.Alert;

/**
 * Hilfe klasse für Alert
 */
public class AlertFunc {
    /**
     * Hilfe Methode um Alert ERROR Methode aufzurufen
     * @param titel Die Meldungstitel
     * @param message Die Nachricht der Meldung
     */
    public void errorAlert(String titel,String message){
        Alert alert=new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titel);
        alert.setContentText(message);
        alert.showAndWait();
    }
    /**
     * Hilfe Methode um Alert INFORMATION Methode aufzurufen
     * @param titel Die Meldungstitel
     * @param message Die Nachricht der Meldung
     */
    public void informationAlert(String titel,String message){
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titel);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
