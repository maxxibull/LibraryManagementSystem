package lms.gui.tabs;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BooksEditionTab {
    public BooksEditionTab(TabPane tabPane) {
        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Editions");

        Label addNewBookLabel = new Label("Add new edition");
        addNewBookLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(addNewBookLabel, HPos.CENTER);
        Label addISBNLabel = new Label("ISBN");
        GridPane.setHalignment(addISBNLabel, HPos.RIGHT);
        TextField addISBNTextField = new TextField();
        Label addBookIDLabel = new Label("Book ID");
        GridPane.setHalignment(addBookIDLabel, HPos.RIGHT);
        TextField addBookIDTextField = new TextField();
        Label addYearLabel = new Label("Year");
        GridPane.setHalignment(addYearLabel, HPos.RIGHT);
        TextField addYearTextField = new TextField();
        Label addPublisherLabel = new Label("Publisher");
        GridPane.setHalignment(addPublisherLabel, HPos.RIGHT);
        TextField addPublisherTextField = new TextField();
        Button addButton = new Button("Add new edition");
        addButton.setMinWidth(200);

        Label changeEditionLabel = new Label("Change data (not ISBN)");
        changeEditionLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(changeEditionLabel, HPos.CENTER);
        Label changeISBNLabel = new Label("ISBN");
        GridPane.setHalignment(changeISBNLabel, HPos.RIGHT);
        TextField changeISBNTextField = new TextField();
        Label changeBookIDLabel = new Label("Book ID");
        GridPane.setHalignment(changeBookIDLabel, HPos.RIGHT);
        TextField changeBookIDTextField = new TextField();
        Label changeYearLabel = new Label("Year");
        GridPane.setHalignment(changeYearLabel, HPos.RIGHT);
        TextField changeYearTextField = new TextField();
        Label changePublisherLabel = new Label("Publisher");
        GridPane.setHalignment(changePublisherLabel, HPos.RIGHT);
        TextField changePublisherTextField = new TextField();
        Button changeButton = new Button("Change edition's data");
        changeButton.setMinWidth(200);

        Label removeEditionLabel = new Label("Remove edition");
        removeEditionLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeEditionLabel, HPos.CENTER);
        Label removeISBNLabel = new Label("Index");
        GridPane.setHalignment(removeISBNLabel, HPos.RIGHT);
        TextField removeISBNTextField = new TextField();
        Button removeButton = new Button("Remove edition");
        removeButton.setMinWidth(200);

        Label editionsInSystemLabel = new Label("Editions");
        editionsInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(editionsInSystemLabel, HPos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(addNewBookLabel, 0, 0, 2, 1);
        grid.add(addISBNLabel, 0, 1);
        grid.add(addISBNTextField, 1, 1);
        grid.add(addBookIDLabel, 0, 2);
        grid.add(addBookIDTextField, 1, 2);
        grid.add(addYearLabel, 0, 3);
        grid.add(addYearTextField, 1, 3);
        grid.add(addPublisherLabel, 0, 4);
        grid.add(addPublisherTextField, 1, 4);

        hbox = new HBox(10);
        hbox.getChildren().add(addButton);
        grid.add(hbox, 1, 5);

        grid.add(changeEditionLabel, 0, 7, 2, 1);
        grid.add(changeISBNLabel, 0, 8);
        grid.add(changeISBNTextField, 1, 8);
        grid.add(changeBookIDLabel, 0, 9);
        grid.add(changeBookIDTextField, 1, 9);
        grid.add(changeYearLabel, 0, 10);
        grid.add(changeYearTextField, 1, 10);
        grid.add(changePublisherLabel, 0, 11);
        grid.add(changePublisherTextField, 1, 11);

        hbox = new HBox(10);
        hbox.getChildren().add(changeButton);
        grid.add(hbox, 1, 12);

        grid.add(removeEditionLabel, 0, 14, 2, 1);
        grid.add(removeISBNLabel, 0, 15);
        grid.add(removeISBNTextField, 1, 15);

        hbox = new HBox(10);
        hbox.getChildren().add(removeButton);
        grid.add(hbox, 1, 16);

        grid.add(editionsInSystemLabel, 4, 0, 3, 1);
        ListView<String> listOfEditions = new ListView<>();
        grid.add(listOfEditions, 4, 1, 3, 16);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }
}
