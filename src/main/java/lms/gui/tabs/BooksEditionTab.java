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

public class BooksEditionTab {
    private Connection connection;
    private ObservableList<ObservableList> observableArrayList;
    private TableView tableView;

    public BooksEditionTab(TabPane tabPane, Connection connection) {
        this.connection = connection;

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

        addButton.setOnAction(e -> {
            if(!addISBNTextField.getText().isEmpty() && !addBookIDTextField.getText().isEmpty() &&
                    !addYearTextField.getText().isEmpty() && !addPublisherTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO Books_edition (ISBN, book_ID, year, publisher) VALUES (?, ?, ?, ?)");
                    ps.setString(1, addISBNTextField.getText());
                    ps.setInt(2, Integer.parseInt(addBookIDTextField.getText()));
                    ps.setString(3, addYearTextField.getText());
                    ps.setString(4, addPublisherTextField.getText());
                    ps.executeQuery();
                } catch(Exception ex) {
                    ex.printStackTrace();
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            addBookIDTextField.setText("");
            addISBNTextField.setText("");
            addPublisherTextField.setText("");
            addYearTextField.setText("");
        });

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

        changeButton.setOnAction(e -> {
            if(changeISBNTextField.getText().isEmpty()) {
                new AlertError();
            }
            else {
                try {
                    PreparedStatement ps = null;
                    if(!changeBookIDTextField.getText().isEmpty()) {
                        ps = connection.prepareStatement("UPDATE Books_edition SET book_ID = ? WHERE ISBN = ?");
                        ps.setInt(1, Integer.parseInt(changeBookIDTextField.getText()));
                        ps.setString(2, changeISBNTextField.getText());
                        ps.executeQuery();
                    }
                    if(!changeYearTextField.getText().isEmpty()) {
                        ps = connection.prepareStatement("UPDATE Books_edition SET year = ? WHERE ISBN = ?");
                        ps.setString(1, changeYearTextField.getText());
                        ps.setString(2, changeISBNTextField.getText());
                        ps.executeQuery();
                    }
                    if(!changePublisherTextField.getText().isEmpty()) {
                        ps = connection.prepareStatement("UPDATE Books_edition SET publisher = ? WHERE ISBN = ?");
                        ps.setString(1, changePublisherTextField.getText());
                        ps.setString(2, changeISBNTextField.getText());
                        ps.executeQuery();
                    }
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            changeBookIDTextField.setText("");
            changeISBNTextField.setText("");
            changePublisherTextField.setText("");
            changeYearTextField.setText("");
        });

        Label removeEditionLabel = new Label("Remove edition");
        removeEditionLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeEditionLabel, HPos.CENTER);
        Label removeISBNLabel = new Label("ISBN");
        GridPane.setHalignment(removeISBNLabel, HPos.RIGHT);
        TextField removeISBNTextField = new TextField();
        Button removeButton = new Button("Remove edition");
        removeButton.setMinWidth(200);

        removeButton.setOnAction(e -> {
            if(!removeISBNTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM Books_edition WHERE ISBN = ?;");
                    ps.setString(1, removeISBNTextField.getText());
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            removeISBNTextField.setText("");
        });

        Label editionsInSystemLabel = new Label("Editions");
        editionsInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(editionsInSystemLabel, HPos.CENTER);
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
            ResultSet rs = connection.createStatement().executeQuery("SELECT ISBN, book_ID, title, year, " +
                    "publisher, copies, borrowed FROM Books_edition BE JOIN Books_info BI ON BE.book_ID = BI.ID");

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
