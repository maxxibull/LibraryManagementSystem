package lms.gui.tabs;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AuthorsTab {
    public AuthorsTab(TabPane tabPane) {
        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Authors");

        Label removeAuthorLabel = new Label("Remove author");
        removeAuthorLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeAuthorLabel, HPos.CENTER);
        Label removeIDLabel = new Label("ID");
        GridPane.setHalignment(removeIDLabel, HPos.RIGHT);
        TextField removeIDTextField = new TextField();
        Button removeButton = new Button("Remove author");
        removeButton.setMinWidth(200);

        Label addNewClientLabel = new Label("Add new author");
        addNewClientLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(addNewClientLabel, HPos.CENTER);
        Label addFirstNameLabel = new Label("First name");
        GridPane.setHalignment(addFirstNameLabel, HPos.RIGHT);
        TextField addFirstNameTextField = new TextField();
        Label addLastNameLabel = new Label("Last name");
        GridPane.setHalignment(addLastNameLabel, HPos.RIGHT);
        TextField addLastNameTextField = new TextField();
        Button addButton = new Button("Add new author");
        addButton.setMinWidth(200);

        Label changeAuthorDataLabel = new Label("Change data (not ID)");
        changeAuthorDataLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(changeAuthorDataLabel, HPos.CENTER);
        Label changeIDLabel = new Label("ID");
        GridPane.setHalignment(changeIDLabel, HPos.RIGHT);
        TextField changeIDTextField = new TextField();
        Label changeFirstNameLabel = new Label("First name");
        GridPane.setHalignment(changeFirstNameLabel, HPos.RIGHT);
        TextField changeFirstNameTextField = new TextField();
        Label changeLastNameLabel = new Label("Last name");
        GridPane.setHalignment(changeLastNameLabel, HPos.RIGHT);
        TextField changeLastNameTextField = new TextField();
        Button changeButton = new Button("Change data");
        changeButton.setMinWidth(200);

        Label authorsInSystemLabel = new Label("Authors");
        authorsInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(authorsInSystemLabel, HPos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(addNewClientLabel, 0, 0, 2, 1);
        grid.add(addFirstNameLabel, 0, 1);
        grid.add(addFirstNameTextField, 1, 1);
        grid.add(addLastNameLabel, 0, 2);
        grid.add(addLastNameTextField, 1, 2);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(addButton);
        grid.add(hbox, 1, 3);

        grid.add(changeAuthorDataLabel, 0, 5, 2, 1);
        grid.add(changeIDLabel, 0, 6);
        grid.add(changeIDTextField, 1, 6);
        grid.add(changeFirstNameLabel, 0, 7);
        grid.add(changeFirstNameTextField, 1, 7);
        grid.add(changeLastNameLabel, 0, 8);
        grid.add(changeLastNameTextField, 1, 8);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(changeButton);
        grid.add(hbox, 1, 9);

        grid.add(removeAuthorLabel, 0, 11, 2, 1);
        grid.add(removeIDLabel, 0, 12);
        grid.add(removeIDTextField, 1, 12);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(removeButton);
        grid.add(hbox, 1, 13);

        grid.add(authorsInSystemLabel, 4, 0, 3, 1);
        ListView<String> listOfAuthors = new ListView<>();
        grid.add(listOfAuthors, 4, 1, 3, 13);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }
}
