package lms.gui.alerts;

import javafx.scene.control.Alert;

public class AlertError extends Alert {
    public AlertError() {
        super(Alert.AlertType.INFORMATION);
        setTitle("Error");
        setHeaderText(null);
        setContentText("Wrong data! Try again.");
        showAndWait();
    }
}
