package lms.gui.tabs;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class CopiesTab {
    public CopiesTab(TabPane tabPane) {
        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Copies");

        Label addNewCopyLabel = new Label("Add new copy");
        addNewCopyLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(addNewCopyLabel, HPos.CENTER);
        Label addISBNLabel = new Label("ISBN");
        GridPane.setHalignment(addISBNLabel, HPos.RIGHT);
        TextField addISBNTextField = new TextField();
        Button addButton = new Button("Add new copy");
        addButton.setMinWidth(200);

        Label changeEditionLabel = new Label("Change data (not Code)");
        changeEditionLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(changeEditionLabel, HPos.CENTER);
        Label changeCodeLabel = new Label("Code");
        GridPane.setHalignment(changeCodeLabel, HPos.RIGHT);
        TextField changeCodeTextField = new TextField();
        Label changeISBNLabel = new Label("ISBN");
        GridPane.setHalignment(changeISBNLabel, HPos.RIGHT);
        TextField changeISBNTextField = new TextField();
        Button changeButton = new Button("Change data");
        changeButton.setMinWidth(200);

        Label removeCopyLabel = new Label("Remove copy");
        removeCopyLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeCopyLabel, HPos.CENTER);
        Label removeCodeLabel = new Label("Code");
        GridPane.setHalignment(removeCodeLabel, HPos.RIGHT);
        TextField removeCodeTextField = new TextField();
        Button removeButton = new Button("Remove copy");
        removeButton.setMinWidth(200);

        Label copiesInSystemLabel = new Label("Copies");
        copiesInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(copiesInSystemLabel, HPos.CENTER);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(addNewCopyLabel, 0, 0, 2, 1);
        grid.add(addISBNLabel, 0, 1);
        grid.add(addISBNTextField, 1, 1);

        hbox = new HBox(10);
        hbox.getChildren().add(addButton);
        grid.add(hbox, 1, 2);

        grid.add(changeEditionLabel, 0, 4, 2, 1);
        grid.add(changeCodeLabel, 0, 5);
        grid.add(changeCodeTextField, 1 ,5);
        grid.add(changeISBNLabel, 0, 6);
        grid.add(changeISBNTextField, 1, 6);

        hbox = new HBox(10);
        hbox.getChildren().add(changeButton);
        grid.add(hbox, 1, 7);

        grid.add(removeCopyLabel, 0, 9, 2, 1);
        grid.add(removeCodeLabel, 0, 10);
        grid.add(removeCodeTextField, 1, 10);

        hbox = new HBox(10);
        hbox.getChildren().add(removeButton);
        grid.add(hbox, 1, 11);

        grid.add(copiesInSystemLabel, 4, 0, 3, 1);
        ListView<String> listOfCopies = new ListView<>();
        grid.add(listOfCopies, 4, 1, 3, 11);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }
}
