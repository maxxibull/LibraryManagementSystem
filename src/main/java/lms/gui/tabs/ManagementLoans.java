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
import javafx.scene.text.Text;
import javafx.util.Callback;
import lms.gui.alerts.AlertError;
import lms.gui.alerts.AlertFineToPay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ManagementLoans {
    private Connection connection;
    private ObservableList<ObservableList> observableArrayList;
    private TableView tableView;

    public ManagementLoans(TabPane tabPane, Connection connection) {
        this.connection = connection;

        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Loans");

        Label lendBookLabel = new Label("Lend book");
        lendBookLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(lendBookLabel, HPos.CENTER);
        Label indexLabel = new Label("Client index");
        GridPane.setHalignment(indexLabel, HPos.RIGHT);
        TextField indexTextField = new TextField();
        Label bookCodeLabel = new Label("Book code");
        GridPane.setHalignment(bookCodeLabel, HPos.RIGHT);
        TextField bookCodeTextField = new TextField();
        Button addButton = new Button("Accept");
        addButton.setMinWidth(200);

        Label acceptedLabel = new Label("Accepted");
        acceptedLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(acceptedLabel, HPos.CENTER);
        Label newIDLabel = new Label("ID");
        GridPane.setHalignment(newIDLabel, HPos.RIGHT);
        Text newIDText = new Text();
        Label newBorrowDateLabel = new Label("Borrow date");
        GridPane.setHalignment(newBorrowDateLabel, HPos.RIGHT);
        Text newBorrowDateText = new Text();
        Label newDueDateLabel = new Label("Due date");
        GridPane.setHalignment(newDueDateLabel, HPos.RIGHT);
        Text newDueDateText = new Text();
        Label newBookCodeLabel = new Label("Book code");
        GridPane.setHalignment(newBookCodeLabel, HPos.RIGHT);
        Text newBookCodeText = new Text();
        Label newIndexLabel = new Label("Client index");
        GridPane.setHalignment(newIndexLabel, HPos.RIGHT);
        Text newIndexText = new Text();

        addButton.setOnAction(e -> {
            if(!indexTextField.getText().isEmpty() && !bookCodeTextField.getText().isEmpty()) {
                try {
                    String loanID = getLoanID();
                    PreparedStatement ps = connection.prepareStatement(
                            "INSERT INTO On_loan (ID, borrow_date, book_code, client_indx) VALUES (?, ?, ?, ?)");
                    ps.setString(1, loanID);
                    ps.setDate(2, getCurrentDate());
                    ps.setInt(3, Integer.parseInt(bookCodeTextField.getText()));
                    ps.setInt(4, Integer.parseInt(indexTextField.getText()));
                    ps.executeQuery();

                    ps = connection.prepareStatement("SELECT * FROM On_loan WHERE ID = ?");
                    ps.setString(1, loanID);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    newIDText.setText(rs.getString("ID"));
                    newBorrowDateText.setText(rs.getDate("borrow_date").toString());
                    newDueDateText.setText(rs.getDate("due_date").toString());
                    newBookCodeText.setText(Integer.toString(rs.getInt("book_code")));
                    newIndexText.setText(Integer.toString(rs.getInt("client_indx")));
                } catch(Exception ex) {
                    new AlertError();
                    newIDText.setText("");
                    newBorrowDateText.setText("");
                    newDueDateText.setText("");
                    newBookCodeText.setText("");
                    newIndexText.setText("");
                }
            }
            else {
                new AlertError();
                newIDText.setText("");
                newBorrowDateText.setText("");
                newDueDateText.setText("");
                newBookCodeText.setText("");
                newIndexText.setText("");
            }
            indexTextField.setText("");
            bookCodeTextField.setText("");
        });

        Label returnBookLabel = new Label("Return book");
        returnBookLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(returnBookLabel, HPos.CENTER);
        Label returnBookCodeLabel = new Label("Book code");
        GridPane.setHalignment(bookCodeLabel, HPos.RIGHT);
        TextField returnBookCodeTextField = new TextField();
        Button returnButton = new Button("Accept");
        returnButton.setMinWidth(200);

        returnButton.setOnAction(e -> {
            if(!returnBookCodeTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement("SELECT ID FROM ON_loan WHERE book_code = ?");
                    ps.setInt(1, Integer.parseInt(returnBookCodeTextField.getText()));
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    String loanID = rs.getString("ID");

                    ps = connection.prepareStatement("DELETE FROM On_loan WHERE book_code = ?");
                    ps.setInt(1, Integer.parseInt(returnBookCodeTextField.getText()));
                    ps.executeQuery();

                    ps = connection.prepareStatement("SELECT fine FROM History WHERE loan_ID = ?");
                    ps.setString(1, loanID);
                    rs = ps.executeQuery();
                    rs.next();
                    float fine = rs.getFloat("fine");
                    new AlertFineToPay(fine);
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            returnBookCodeTextField.setText("");
        });

        Label onLoanLabel = new Label("On loan");
        onLoanLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(onLoanLabel, HPos.CENTER);
        Button refreshButton = new Button("Refresh");

        refreshButton.setOnAction(e -> {
            tableView.getItems().clear();
            tableView.getColumns().clear();
            fillTable();
            tableView.refresh();
        });

        observableArrayList = FXCollections.observableArrayList();
        tableView = new TableView();
        fillTable();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(lendBookLabel, 0, 0, 2, 1);
        grid.add(indexLabel, 0, 1);
        grid.add(indexTextField, 1, 1);
        grid.add(bookCodeLabel, 0, 2);
        grid.add(bookCodeTextField, 1, 2);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(addButton);
        grid.add(hbox, 1, 3);

        grid.add(acceptedLabel, 0, 5, 2, 1);
        grid.add(newIDLabel, 0, 6);
        grid.add(newIDText, 1, 6);
        grid.add(newBorrowDateLabel, 0, 7);
        grid.add(newBorrowDateText, 1, 7);
        grid.add(newDueDateLabel, 0, 8);
        grid.add(newDueDateText, 1, 8);
        grid.add(newBookCodeLabel, 0, 9);
        grid.add(newBookCodeText, 1, 9);
        grid.add(newIndexLabel, 0, 10);
        grid.add(newIndexText, 1, 10);

        grid.add(returnBookLabel, 0, 12, 2, 1);
        grid.add(returnBookCodeLabel, 0, 13);
        grid.add(returnBookCodeTextField, 1, 13);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(returnButton);
        grid.add(hbox, 1, 14);

        grid.add(onLoanLabel, 4, 0, 2, 1);
        grid.add(tableView, 3, 1, 4, 14);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(refreshButton);
        grid.add(hbox, 6, 15);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }

    private String getLoanID() {
        DateFormat dateFormat = new SimpleDateFormat("yyMMddhhmm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private java.sql.Date getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        java.sql.Date currDate = new java.sql.Date(cal.get(Calendar.YEAR) - 1900, cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));
        return currDate;
    }

    private void fillTable() {
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM On_loan");

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
