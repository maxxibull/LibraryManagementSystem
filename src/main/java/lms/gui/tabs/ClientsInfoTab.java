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

public class ClientsInfoTab {
    private Connection connection;
    private ObservableList<ObservableList> observableArrayList;
    private TableView tableView;

    public ClientsInfoTab(TabPane tabPane, Connection connection) {
        this.connection = connection;

        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Search client");

        Label searchClientLabel = new Label("Search client");
        searchClientLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(searchClientLabel, HPos.CENTER);
        Label searchIndexLabel = new Label("Index");
        GridPane.setHalignment(searchIndexLabel, HPos.RIGHT);
        TextField searchIndexTextField = new TextField();
        Label searchFirstNameLabel = new Label("First name");
        GridPane.setHalignment(searchFirstNameLabel, HPos.RIGHT);
        TextField searchFirstNameTextField = new TextField();
        Label searchLastNameLabel = new Label("Last name");
        GridPane.setHalignment(searchLastNameLabel, HPos.RIGHT);
        TextField searchLastNameTextField = new TextField();
        Button searchButton = new Button("Search");
        searchButton.setMinWidth(200);

        observableArrayList = FXCollections.observableArrayList();
        tableView = new TableView();

        searchButton.setOnAction(e -> {
            try {
                tableView.getItems().clear();
                tableView.getColumns().clear();
                if (!searchIndexTextField.getText().isEmpty() && !searchFirstNameTextField.getText().isEmpty() &&
                        !searchLastNameTextField.getText().isEmpty()) {
                    PreparedStatement ps = connection.prepareStatement(
                            "SELECT * FROM Clients WHERE indx = ? OR first_name = ? OR last_name = ?");
                    ps.setInt(1, Integer.parseInt(searchIndexTextField.getText()));
                    ps.setString(2, searchFirstNameTextField.getText());
                    ps.setString(3, searchLastNameTextField.getText());
                    ResultSet rs = ps.executeQuery();
                    fillTable(rs);
                }
                else if(!searchFirstNameTextField.getText().isEmpty() && !searchLastNameTextField.getText().isEmpty()) {
                    PreparedStatement ps = connection.prepareStatement(
                            "SELECT * FROM Clients WHERE first_name = ? OR last_name = ?");
                    ps.setString(1, searchFirstNameTextField.getText());
                    ps.setString(2, searchLastNameTextField.getText());
                    ResultSet rs = ps.executeQuery();
                    fillTable(rs);
                }
                else if(!searchIndexTextField.getText().isEmpty() && !searchFirstNameTextField.getText().isEmpty()) {
                    PreparedStatement ps = connection.prepareStatement(
                            "SELECT * FROM Clients WHERE indx = ? OR first_name = ?");
                    ps.setInt(1, Integer.parseInt(searchIndexTextField.getText()));
                    ps.setString(2, searchFirstNameTextField.getText());
                    ResultSet rs = ps.executeQuery();
                    fillTable(rs);
                }
                else if(!searchIndexTextField.getText().isEmpty() && !searchLastNameTextField.getText().isEmpty()) {
                    PreparedStatement ps = connection.prepareStatement(
                            "SELECT * FROM Clients WHERE indx = ? OR first_name = ?");
                    ps.setInt(1, Integer.parseInt(searchIndexTextField.getText()));
                    ps.setString(2, searchLastNameTextField.getText());
                    ResultSet rs = ps.executeQuery();
                    fillTable(rs);
                }
                else if(!searchIndexTextField.getText().isEmpty()) {
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM Clients WHERE indx = ?");
                    ps.setInt(1, Integer.parseInt(searchIndexTextField.getText()));
                    ResultSet rs = ps.executeQuery();
                    fillTable(rs);
                }
                else if(!searchFirstNameTextField.getText().isEmpty()) {
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM Clients WHERE first_name = ?");
                    ps.setString(1, searchFirstNameTextField.getText());
                    ResultSet rs = ps.executeQuery();
                    fillTable(rs);
                }
                else if(!searchLastNameTextField.getText().isEmpty()) {
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM Clients WHERE last_name = ?");
                    ps.setString(1, searchLastNameTextField.getText());
                    ResultSet rs = ps.executeQuery();
                    fillTable(rs);
                }
                else {
                    PreparedStatement ps = connection.prepareStatement("SELECT * FROM Clients");
                    ResultSet rs = ps.executeQuery();
                    fillTable(rs);
                }
                tableView.refresh();
            } catch(Exception ex) {
                new AlertError();
            }
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(searchClientLabel, 0, 0, 2, 1);
        grid.add(searchIndexLabel, 0, 1);
        grid.add(searchIndexTextField, 1, 1);
        grid.add(searchFirstNameLabel, 0, 2);
        grid.add(searchFirstNameTextField, 1, 2);
        grid.add(searchLastNameLabel, 0, 3);
        grid.add(searchLastNameTextField, 1, 3);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(searchButton);
        grid.add(hbox, 1, 4);

        grid.add(tableView, 4, 0, 3, 10);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }

    private void fillTable(ResultSet rs) {
        try {
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
