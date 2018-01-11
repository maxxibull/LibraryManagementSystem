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
import lms.gui.AlertError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BooksInfoTab {
    private Connection connection;
    private ObservableList<ObservableList> observableArrayList;
    private TableView tableView;

    public BooksInfoTab(TabPane tabPane, Connection connection) {
        this.connection = connection;

        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Books' Data");

        Label addNewBookLabel = new Label("Add new book");
        addNewBookLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(addNewBookLabel, HPos.CENTER);
        Label addTitleLabel = new Label("Title");
        GridPane.setHalignment(addTitleLabel, HPos.RIGHT);
        TextField addTitleTextField = new TextField();
        Button addButton = new Button("Add new book");
        addButton.setMinWidth(200);

        addButton.setOnAction(e -> {
            if(!addTitleTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO Books_info (title) VALUES (?);");
                    ps.setString(1, addTitleTextField.getText());
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            addTitleTextField.setText("");
        });

        Label changeBookInfoLabel = new Label("Change data (not ID)");
        changeBookInfoLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(changeBookInfoLabel, HPos.CENTER);
        Label changeBookIDLabel = new Label("ID");
        GridPane.setHalignment(changeBookIDLabel, HPos.RIGHT);
        TextField changeBookIDTextField = new TextField();
        Label changeTitleLabel = new Label("Title");
        GridPane.setHalignment(changeTitleLabel, HPos.RIGHT);
        TextField changeTitleTextField = new TextField();
        Button changeButton = new Button("Change book's data");
        changeButton.setMinWidth(200);

        changeButton.setOnAction(e -> {
            if(!changeBookIDTextField.getText().isEmpty() && !changeTitleTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement(
                            "UPDATE Books_info SET title = ? WHERE ID = ?");
                    ps.setString(1, changeTitleTextField.getText());
                    ps.setInt(2, Integer.parseInt(changeBookIDTextField.getText()));
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            changeBookIDTextField.setText("");
            changeTitleTextField.setText("");
        });

        Label removeBookLabel = new Label("Remove book");
        removeBookLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeBookLabel, HPos.CENTER);
        Label removeIDLabel = new Label("ID");
        GridPane.setHalignment(removeIDLabel, HPos.RIGHT);
        TextField removeIDTextField = new TextField();
        Button removeButton = new Button("Remove book");
        removeButton.setMinWidth(200);

        removeButton.setOnAction(e -> {
            if(!removeIDTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM Books_info WHERE ID = ?;");
                    ps.setInt(1, Integer.parseInt(removeIDTextField.getText()));
                    ps.executeQuery();
                } catch (Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            removeIDTextField.setText("");
        });

        Label booksInSystemLabel = new Label("Books");
        booksInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(booksInSystemLabel, HPos.CENTER);
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

        grid.add(addNewBookLabel, 0, 0, 2, 1);
        grid.add(addTitleLabel, 0, 1);
        grid.add(addTitleTextField, 1, 1);

        hbox = new HBox(10);
        hbox.getChildren().add(addButton);
        grid.add(hbox, 1, 2);

        grid.add(changeBookInfoLabel, 0, 4, 2, 1);
        grid.add(changeBookIDLabel, 0, 5);
        grid.add(changeBookIDTextField, 1, 5);
        grid.add(changeTitleLabel, 0, 6);
        grid.add(changeTitleTextField, 1, 6);

        hbox = new HBox(10);
        hbox.getChildren().add(changeButton);
        grid.add(hbox, 1, 8);

        grid.add(removeBookLabel, 0, 9, 2, 1);
        grid.add(removeIDLabel, 0, 10);
        grid.add(removeIDTextField, 1, 10);

        hbox = new HBox(10);
        hbox.getChildren().add(removeButton);
        grid.add(hbox, 1, 11);

        grid.add(booksInSystemLabel, 4, 0, 3, 1);

        observableArrayList = FXCollections.observableArrayList();
        tableView = new TableView();
        fillTable();

        grid.add(tableView, 4, 1, 3, 13);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(refreshButton);
        grid.add(hbox, 6, 14);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }

    private void fillTable() {
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Books_info");

            for(int i = 0 ; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setMinWidth(150);
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
