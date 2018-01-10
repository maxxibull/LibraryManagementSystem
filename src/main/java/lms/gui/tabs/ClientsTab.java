package lms.gui.tabs;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ClientsTab {
    public ClientsTab(TabPane tabPane) {
        HBox hbox = null;
        Tab tab = new Tab();
        ToggleGroup toggleGroup = new ToggleGroup();
        tab.setText("Clients");

        Label removeClientLabel = new Label("Remove client");
        removeClientLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeClientLabel, HPos.CENTER);
        Label removeIndexLabel = new Label("Index");
        GridPane.setHalignment(removeIndexLabel, HPos.RIGHT);
        TextField removeIndexTextField = new TextField();
        Button removeButton = new Button("Remove client");
        removeButton.setMinWidth(200);

        Label addNewClientLabel = new Label("Add new client");
        addNewClientLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(addNewClientLabel, HPos.CENTER);
        Label addIndexLabel = new Label("Index");
        GridPane.setHalignment(addIndexLabel, HPos.RIGHT);
        TextField addIndexTextField = new TextField();
        Label addFirstNameLabel = new Label("First name");
        GridPane.setHalignment(addFirstNameLabel, HPos.RIGHT);
        TextField addFirstNameTextField = new TextField();
        Label addLastNameLabel = new Label("Last name");
        GridPane.setHalignment(addLastNameLabel, HPos.RIGHT);
        TextField addLastNameTextField = new TextField();
        Label addEmailLabel = new Label("E-mail");
        GridPane.setHalignment(addEmailLabel, HPos.RIGHT);
        TextField addEmailTextField = new TextField();
        RadioButton studentRadioButton = new RadioButton("Student");
        studentRadioButton.setUserData("student");
        studentRadioButton.setToggleGroup(toggleGroup);
        studentRadioButton.setSelected(true);
        GridPane.setHalignment(studentRadioButton, HPos.LEFT);
        RadioButton employeeRadioButton = new RadioButton("Employee");
        employeeRadioButton.setUserData("employee");
        employeeRadioButton.setToggleGroup(toggleGroup);
        GridPane.setHalignment(employeeRadioButton, HPos.RIGHT);
        Button addButton = new Button("Add new client");
        addButton.setMinWidth(200);

        Label changeClientDataLabel = new Label("Change data (not index)");
        changeClientDataLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(changeClientDataLabel, HPos.CENTER);
        Label changeClientDataIndexLabel = new Label("Index");
        GridPane.setHalignment(changeClientDataIndexLabel, HPos.RIGHT);
        TextField changeClientDataIndexTextField = new TextField();
        Label changeFirstNameLabel = new Label("First name");
        GridPane.setHalignment(changeFirstNameLabel, HPos.RIGHT);
        TextField changeFirstNameTextField = new TextField();
        Label changeLastNameLabel = new Label("Last name");
        GridPane.setHalignment(changeLastNameLabel, HPos.RIGHT);
        TextField changeLastNameTextField = new TextField();
        Label changeEmailLabel = new Label("E-mail");
        GridPane.setHalignment(changeEmailLabel, HPos.RIGHT);
        TextField changeEmailTextField = new TextField();
        Button changeButton = new Button("Change data");
        changeButton.setMinWidth(200);

        Label clientsInSystemLabel = new Label("Clients");
        clientsInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(clientsInSystemLabel, HPos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(addNewClientLabel, 0, 0, 2, 1);
        grid.add(addIndexLabel, 0, 1);
        grid.add(addIndexTextField, 1, 1);
        grid.add(addFirstNameLabel, 0, 2);
        grid.add(addFirstNameTextField, 1, 2);
        grid.add(addLastNameLabel, 0, 3);
        grid.add(addLastNameTextField, 1, 3);
        grid.add(addEmailLabel, 0, 4);
        grid.add(addEmailTextField, 1, 4);

        hbox = new HBox(10);
        hbox.getChildren().addAll(studentRadioButton, employeeRadioButton);
        grid.add(hbox, 1, 5);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(addButton);
        grid.add(hbox, 1, 6);

        grid.add(changeClientDataLabel, 0, 8, 2, 1);
        grid.add(changeClientDataIndexLabel, 0, 9);
        grid.add(changeClientDataIndexTextField, 1, 9);
        grid.add(changeFirstNameLabel, 0, 10);
        grid.add(changeFirstNameTextField, 1, 10);
        grid.add(changeLastNameLabel, 0, 11);
        grid.add(changeLastNameTextField, 1, 11);
        grid.add(changeEmailLabel, 0, 12);
        grid.add(changeEmailTextField, 1, 12);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(changeButton);
        grid.add(hbox, 1, 13);

        grid.add(removeClientLabel, 0, 15, 2, 1);
        grid.add(removeIndexLabel, 0, 16);
        grid.add(removeIndexTextField, 1, 16);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(removeButton);
        grid.add(hbox, 1, 17);

        grid.add(clientsInSystemLabel, 4, 0, 3, 1);
        ListView<String> listOfClients = new ListView<>();
        grid.add(listOfClients, 4, 1, 3, 17);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }
}
