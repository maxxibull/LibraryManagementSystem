package lms.gui.tabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;
import lms.gui.alerts.AlertError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CopiesTab {
    private Connection connection;
    private ObservableList<ObservableList> observableArrayList;
    private TableView tableView;

    public CopiesTab(TabPane tabPane, Connection connection) {
        this.connection = connection;

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

        addButton.setOnAction(e -> {
            if(!addISBNTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO Books (ISBN) VALUES (?)");
                    ps.setString(1, addISBNTextField.getText());
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            addISBNTextField.setText("");
        });

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

        changeButton.setOnAction(e -> {
            if(!changeCodeTextField.getText().isEmpty() && !changeISBNTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement("UPDATE Books SET ISBN = ? WHERE code = ?");
                    ps.setString(1, changeISBNTextField.getText());
                    ps.setInt(2, Integer.parseInt(changeCodeTextField.getText()));
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            changeCodeTextField.setText("");
            changeISBNTextField.setText("");
        });

        Label removeCopyLabel = new Label("Remove copy");
        removeCopyLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeCopyLabel, HPos.CENTER);
        Label removeCodeLabel = new Label("Code");
        GridPane.setHalignment(removeCodeLabel, HPos.RIGHT);
        TextField removeCodeTextField = new TextField();
        Button removeButton = new Button("Remove copy");
        removeButton.setMinWidth(200);

        removeButton.setOnAction(e -> {
            if(!removeCodeTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM Books WHERE code = ?;");
                    ps.setInt(1, Integer.parseInt(removeCodeTextField.getText()));
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            removeCodeTextField.setText("");
        });

        Label copiesInSystemLabel = new Label("Copies");
        copiesInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(copiesInSystemLabel, HPos.CENTER);
        Button refreshButton = new Button("Refresh");

        refreshButton.setOnAction(e -> {
            tableView.getItems().clear();
            tableView.getColumns().clear();
            fillTable();
            tableView.refresh();
        });

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

        observableArrayList = FXCollections.observableArrayList();
        tableView = new TableView();
        fillTable();

        grid.add(tableView, 4, 1, 3, 15);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(refreshButton);
        grid.add(hbox, 6, 16);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }

    private void fillTable() {
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT code, Books.ISBN, title, was_borrowed"
                    + " FROM Books JOIN Books_edition BE ON BE.ISBN = Books.ISBN JOIN Books_info BI ON BI.ID = BE.book_ID");

            for(int i = 0 ; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures <ObservableList, String>,
                        ObservableValue<String>>) param ->
                        new SimpleStringProperty(param.getValue().get(j).toString()));

                tableView.getColumns().addAll(col);
            }

            while(rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i = 1 ; i <= rs.getMetaData().getColumnCount(); i++){
                    row.add(rs.getString(i));
                }
                observableArrayList.add(row);

            }

            tableView.setItems(observableArrayList);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
