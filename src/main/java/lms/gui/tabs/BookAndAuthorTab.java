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

public class BookAndAuthorTab {
    private Connection connection;
    private ObservableList<ObservableList> observableArrayList;
    private TableView tableView;

    public BookAndAuthorTab(TabPane tabPane, Connection connection) {
        this. connection = connection;

        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Authors/books");

        Label removeRelationshipLabel = new Label("Remove relationship");
        removeRelationshipLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeRelationshipLabel, HPos.CENTER);
        Label removeBookIDLabel = new Label("Book ID");
        GridPane.setHalignment(removeBookIDLabel, HPos.RIGHT);
        TextField removeBookIDTextField = new TextField();
        Label removeAuthorIDLabel = new Label("Author ID");
        GridPane.setHalignment(removeAuthorIDLabel, HPos.RIGHT);
        TextField removeAuthorIDTextField = new TextField();
        Button removeButton = new Button("Remove relationship");
        removeButton.setMinWidth(200);

        removeButton.setOnAction(e -> {
            if(!removeAuthorIDTextField.getText().isEmpty() && !removeBookIDTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement(
                            "DELETE FROM Book_and_author WHERE author_ID = ? AND book_ID = ?;");
                    ps.setInt(1, Integer.parseInt(removeAuthorIDTextField.getText()));
                    ps.setInt(2, Integer.parseInt(removeBookIDTextField.getText()));
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            removeAuthorIDTextField.setText("");
            removeBookIDTextField.setText("");
        });

        Label addNewRelationshipLabel = new Label("Add new relationship");
        addNewRelationshipLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(addNewRelationshipLabel, HPos.CENTER);
        Label addBookIDLabel = new Label("Book ID");
        GridPane.setHalignment(addBookIDLabel, HPos.RIGHT);
        TextField addBookIDTextField = new TextField();
        Label addAuthorIDLabel = new Label("Author ID");
        GridPane.setHalignment(addAuthorIDLabel, HPos.RIGHT);
        TextField addAuthorIDTextField = new TextField();
        Button addButton = new Button("Add new relationship");
        addButton.setMinWidth(200);

        addButton.setOnAction(e -> {
            if(!addAuthorIDTextField.getText().isEmpty() && !addBookIDTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO Book_and_author VALUES (?, ?)");
                    ps.setInt(1, Integer.parseInt(addBookIDTextField.getText()));
                    ps.setInt(2, Integer.parseInt(addAuthorIDTextField.getText()));
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            addAuthorIDTextField.setText("");
            addBookIDTextField.setText("");
        });

        Label relationshipsInSystemLabel = new Label("Realtionships");
        relationshipsInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(relationshipsInSystemLabel, HPos.CENTER);
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

        grid.add(addNewRelationshipLabel, 0, 0, 2, 1);
        grid.add(addBookIDLabel, 0, 1);
        grid.add(addBookIDTextField, 1, 1);
        grid.add(addAuthorIDLabel, 0, 2);
        grid.add(addAuthorIDTextField, 1, 2);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(addButton);
        grid.add(hbox, 1, 3);

        grid.add(removeRelationshipLabel, 0, 5, 2, 1);
        grid.add(removeBookIDLabel, 0, 6);
        grid.add(removeBookIDTextField, 1, 6);
        grid.add(removeAuthorIDLabel, 0, 7);
        grid.add(removeAuthorIDTextField, 1, 7);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(removeButton);
        grid.add(hbox, 1, 8);

        grid.add(relationshipsInSystemLabel, 4, 0, 3, 1);

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
            ResultSet rs = connection.createStatement().executeQuery(
                    "SELECT book_ID, title, author_ID, first_name, last_name FROM Book_and_author BA " +
                    "JOIN Authors A ON A.ID = BA.author_ID JOIN Books_info BI ON BI.ID = BA.book_ID");

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
