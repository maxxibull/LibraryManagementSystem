package lms.gui.tabs;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BooksInfoTab {
    public BooksInfoTab(TabPane tabPane) {
        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Books' Data");

        Label addNewBookLabel = new Label("Add new book");
        addNewBookLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(addNewBookLabel, HPos.CENTER);
        Label addTitleLabel = new Label("Title");
        GridPane.setHalignment(addTitleLabel, HPos.RIGHT);
        TextField addTitleTextField = new TextField();
        Label addFirstEditionLabel = new Label("First edition (year)");
        GridPane.setHalignment(addFirstEditionLabel, HPos.RIGHT);
        TextField addFirstEditionTextField = new TextField();
        Button addButton = new Button("Add new book");
        addButton.setMinWidth(200);

        Label changeBookInfoLabel = new Label("Change data (not ID)");
        changeBookInfoLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(changeBookInfoLabel, HPos.CENTER);
        Label changeBookIDLabel = new Label("ID");
        GridPane.setHalignment(changeBookIDLabel, HPos.RIGHT);
        TextField changeBookIDTextField = new TextField();
        Label changeTitleLabel = new Label("Title");
        GridPane.setHalignment(changeTitleLabel, HPos.RIGHT);
        TextField changeTitleTextField = new TextField();
        Label changeFirstEditionLabel = new Label("First edition (year)");
        GridPane.setHalignment(changeFirstEditionLabel, HPos.RIGHT);
        TextField changeFirstEditionTextField = new TextField();
        Button changeButton = new Button("Change book's data");
        changeButton.setMinWidth(200);

        Label removeBookLabel = new Label("Remove book");
        removeBookLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeBookLabel, HPos.CENTER);
        Label removeIDLabel = new Label("Index");
        GridPane.setHalignment(removeIDLabel, HPos.RIGHT);
        TextField removeIDTextField = new TextField();
        Button removeButton = new Button("Remove book");
        removeButton.setMinWidth(200);

        Label booksInSystemLabel = new Label("Books");
        booksInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(booksInSystemLabel, HPos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(addNewBookLabel, 0, 0, 2, 1);
        grid.add(addTitleLabel, 0, 1);
        grid.add(addTitleTextField, 1, 1);
        grid.add(addFirstEditionLabel, 0, 2);
        grid.add(addFirstEditionTextField, 1, 2);

        hbox = new HBox(10);
        hbox.getChildren().add(addButton);
        grid.add(hbox, 1, 3);

        grid.add(changeBookInfoLabel, 0, 5, 2, 1);
        grid.add(changeBookIDLabel, 0, 6);
        grid.add(changeBookIDTextField, 1, 6);
        grid.add(changeTitleLabel, 0, 7);
        grid.add(changeTitleTextField, 1, 7);
        grid.add(changeFirstEditionLabel, 0, 8);
        grid.add(changeFirstEditionTextField, 1, 8);

        hbox = new HBox(10);
        hbox.getChildren().add(changeButton);
        grid.add(hbox, 1, 9);

        grid.add(removeBookLabel, 0, 11, 2, 1);
        grid.add(removeIDLabel, 0, 12);
        grid.add(removeIDTextField, 1, 12);

        hbox = new HBox(10);
        hbox.getChildren().add(removeButton);
        grid.add(hbox, 1, 13);

        grid.add(booksInSystemLabel, 4, 0, 3, 1);
        ListView<String> listOfBooks = new ListView<>();
        grid.add(listOfBooks, 4, 1, 3, 13);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }
}
