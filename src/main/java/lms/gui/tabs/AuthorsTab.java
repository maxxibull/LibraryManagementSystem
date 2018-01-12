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

public class AuthorsTab {
    private Connection connection;
    private ObservableList<ObservableList> observableArrayList;
    private TableView tableView;

    public AuthorsTab(TabPane tabPane, Connection connection) {
        this.connection = connection;

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

        removeButton.setOnAction(e -> {
            if(!removeIDTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM Authors WHERE ID = ?;");
                    ps.setInt(1, Integer.parseInt(removeIDTextField.getText()));
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            removeIDTextField.setText("");
        });

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

        addButton.setOnAction(e -> {
            if(!addFirstNameTextField.getText().isEmpty() && !addLastNameTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO Authors (first_name, last_name) VALUES (?, ?)");
                    ps.setString(1, addFirstNameTextField.getText());
                    ps.setString(2, addLastNameTextField.getText());
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            addFirstNameTextField.setText("");
            addLastNameTextField.setText("");
        });

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

        changeButton.setOnAction(e -> {
            if(changeIDTextField.getText().isEmpty()) {
                new AlertError();
            }
            else {
                try {
                    PreparedStatement ps = null;
                    if(!changeFirstNameTextField.getText().isEmpty()) {
                        ps = connection.prepareStatement("UPDATE Authors SET first_name = ? WHERE ID = ?");
                        ps.setString(1, changeFirstNameTextField.getText());
                        ps.setInt(2, Integer.parseInt(changeIDTextField.getText()));
                        ps.executeQuery();
                    }
                    if(!changeLastNameTextField.getText().isEmpty()) {
                        ps = connection.prepareStatement("UPDATE Authors SET last_name = ? WHERE ID = ?");
                        ps.setString(1, changeLastNameTextField.getText());
                        ps.setInt(2, Integer.parseInt(changeIDTextField.getText()));
                        ps.executeQuery();
                    }
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            changeFirstNameTextField.setText("");
            changeLastNameTextField.setText("");
            changeIDTextField.setText("");
        });

        Label authorsInSystemLabel = new Label("Authors");
        authorsInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(authorsInSystemLabel, HPos.CENTER);
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
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM Authors");

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