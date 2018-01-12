package lms.gui.alerts;

import javafx.scene.control.Alert;

public class AlertFineToPay extends Alert {
    public AlertFineToPay(float fine) {
        super(Alert.AlertType.INFORMATION);
        setTitle("Fine To Pay");
        setHeaderText("Client have to pay a fine.");
        setContentText("Fine: " + fine + " PLN");
        showAndWait();
    }
}
