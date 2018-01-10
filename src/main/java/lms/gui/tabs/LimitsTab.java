package lms.gui.tabs;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LimitsTab {
    public LimitsTab(TabPane tabPane) {
        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Limits");

        Label changeLimitsLabel = new Label("Change limits");
        changeLimitsLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(changeLimitsLabel, HPos.CENTER);
        Label changeBooksLimitLabel = new Label("Limit of books");
        GridPane.setHalignment(changeBooksLimitLabel, HPos.RIGHT);
        TextField changeBooksLimitTextField = new TextField();
        Label changeDaysNumberLabel = new Label("Number of days");
        GridPane.setHalignment(changeDaysNumberLabel, HPos.RIGHT);
        TextField changeDaysNumberTextField = new TextField();
        Label changeFineLabel = new Label("Fine per day");
        GridPane.setHalignment(changeFineLabel, HPos.RIGHT);
        TextField changeFineTextField = new TextField();
        Button changeButton = new Button("Change data");
        changeButton.setMinWidth(200);

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton studentRadioButton = new RadioButton("Student");
        studentRadioButton.setUserData("student");
        studentRadioButton.setToggleGroup(toggleGroup);
        studentRadioButton.setSelected(true);
        GridPane.setHalignment(studentRadioButton, HPos.LEFT);
        RadioButton employeeRadioButton = new RadioButton("Employee");
        employeeRadioButton.setUserData("employee");
        employeeRadioButton.setToggleGroup(toggleGroup);
        GridPane.setHalignment(employeeRadioButton, HPos.RIGHT);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(changeLimitsLabel, 0, 0, 2, 1);
        grid.add(changeBooksLimitLabel, 0, 1);
        grid.add(changeBooksLimitTextField, 1, 1);
        grid.add(changeDaysNumberLabel, 0, 2);
        grid.add(changeDaysNumberTextField, 1, 2);
        grid.add(changeFineLabel, 0, 3);
        grid.add(changeFineTextField, 1, 3);

        hbox = new HBox(10);
        hbox.getChildren().addAll(studentRadioButton, employeeRadioButton);
        grid.add(hbox, 1, 4);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(changeButton);
        grid.add(hbox, 1, 5);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }
}
