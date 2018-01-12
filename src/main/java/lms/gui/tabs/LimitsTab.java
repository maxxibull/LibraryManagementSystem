package lms.gui.tabs;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import lms.gui.alerts.AlertError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LimitsTab {
    private Connection connection;

    public LimitsTab(TabPane tabPane, Connection connection) {
        this.connection = connection;

        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Limits");

        Label currentLimitStudentLabel = new Label("Current limits for students");
        currentLimitStudentLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(currentLimitStudentLabel, HPos.CENTER);
        Label currentBooksLimitStudentLabel = new Label("Limit of books");
        GridPane.setHalignment(currentBooksLimitStudentLabel, HPos.RIGHT);
        Text currentBooksLimitStudentText = new Text();
        Label currentDaysNumberStudentLabel = new Label("Number of days");
        GridPane.setHalignment(currentDaysNumberStudentLabel, HPos.RIGHT);
        Text currentDaysNumberStudentText = new Text();
        Label currentFineStudentLabel = new Label("Fine per day");
        GridPane.setHalignment(currentFineStudentLabel, HPos.RIGHT);
        Text currentFineStudentText = new Text();

        Label currentLimitEmployeeLabel = new Label("Current limits for employees");
        currentLimitEmployeeLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(currentLimitEmployeeLabel, HPos.CENTER);
        Label currentBooksLimitEmployeeLabel = new Label("Limit of books");
        GridPane.setHalignment(currentBooksLimitEmployeeLabel, HPos.RIGHT);
        Text currentBooksLimitEmployeeText = new Text();
        Label currentDaysNumberEmployeeLabel = new Label("Number of days");
        GridPane.setHalignment(currentDaysNumberEmployeeLabel, HPos.RIGHT);
        Text currentDaysNumberEmployeeText = new Text();
        Label currentFineEmployeeLabel = new Label("Fine per day");
        GridPane.setHalignment(currentFineEmployeeLabel, HPos.RIGHT);
        Text currentFineEmployeeText = new Text();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM Rules WHERE type = 'student'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            String type = rs.getString("type");
            int books_limit = rs.getInt("books_limit");
            int max_period = rs.getInt("max_period");
            float fine = rs.getFloat("fine_per_day");
            currentBooksLimitStudentText.setText(Integer.toString(books_limit));
            currentDaysNumberStudentText.setText(Integer.toString(max_period));
            currentFineStudentText.setText(Float.toString(fine));

            ps = connection.prepareStatement("SELECT * FROM Rules WHERE type = 'employee'");
            rs = ps.executeQuery();
            rs.next();
            type = rs.getString("type");
            books_limit = rs.getInt("books_limit");
            max_period = rs.getInt("max_period");
            fine = rs.getFloat("fine_per_day");
            currentBooksLimitEmployeeText.setText(Integer.toString(books_limit));
            currentDaysNumberEmployeeText.setText(Integer.toString(max_period));
            currentFineEmployeeText.setText(Float.toString(fine));
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        Label changeLimitsLabel = new Label("Change limits");
        changeLimitsLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(changeLimitsLabel, HPos.CENTER);
        Label changeBooksLimitLabel = new Label("Limit of books");
        GridPane.setHalignment(changeBooksLimitLabel, HPos.RIGHT);
        TextField changeBooksLimitTextField = new TextField();
        Label changeDaysNumberLabel = new Label("Number of days");
        GridPane.setHalignment(changeDaysNumberLabel, HPos.RIGHT);
        TextField changeDaysNumberTextField = new TextField();
        Label changeFineLabel = new Label("Fine per day");
        GridPane.setHalignment(changeFineLabel, HPos.RIGHT);
        TextField changeFineTextField = new TextField();
        Button changeButton = new Button("Change data");
        changeButton.setMinWidth(200);

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton studentRadioButton = new RadioButton("Student");
        studentRadioButton.setUserData("student");
        studentRadioButton.setToggleGroup(toggleGroup);
        studentRadioButton.setSelected(true);
        GridPane.setHalignment(studentRadioButton, HPos.LEFT);
        RadioButton employeeRadioButton = new RadioButton("Employee");
        employeeRadioButton.setUserData("employee");
        employeeRadioButton.setToggleGroup(toggleGroup);
        GridPane.setHalignment(employeeRadioButton, HPos.RIGHT);

        changeButton.setOnAction(e -> {
            try {
                PreparedStatement ps = null;
                if(!changeBooksLimitTextField.getText().isEmpty()) {
                    ps = connection.prepareStatement("UPDATE Rules SET books_limit = ? WHERE type = ?");
                    ps.setInt(1, Integer.parseInt(changeBooksLimitTextField.getText()));
                    ps.setString(2, toggleGroup.getSelectedToggle().getUserData().toString());
                    ps.executeQuery();
                }
                if(!changeDaysNumberTextField.getText().isEmpty()) {
                    ps = connection.prepareStatement("UPDATE Rules SET max_period = ? WHERE type = ?");
                    ps.setInt(1, Integer.parseInt(changeDaysNumberTextField.getText()));
                    ps.setString(2, toggleGroup.getSelectedToggle().getUserData().toString());
                    ps.executeQuery();
                }
                if(!changeFineTextField.getText().isEmpty()) {
                    ps = connection.prepareStatement("UPDATE Rules SET fine_per_day = ? WHERE type = ?");
                    ps.setFloat(1, Float.parseFloat(changeFineTextField.getText()));
                    ps.setString(2, toggleGroup.getSelectedToggle().getUserData().toString());
                    ps.executeQuery();
                }
            } catch(Exception ex) {
                new AlertError();
            }
            changeBooksLimitTextField.setText("");
            changeDaysNumberTextField.setText("");
            changeFineTextField.setText("");

            try {
                PreparedStatement ps = connection.prepareStatement("SELECT * FROM Rules WHERE type = 'student'");
                ResultSet rs = ps.executeQuery();
                rs.next();
                String type = rs.getString("type");
                int books_limit = rs.getInt("books_limit");
                int max_period = rs.getInt("max_period");
                float fine = rs.getFloat("fine_per_day");
                currentBooksLimitStudentText.setText(Integer.toString(books_limit));
                currentDaysNumberStudentText.setText(Integer.toString(max_period));
                currentFineStudentText.setText(Float.toString(fine));

                ps = connection.prepareStatement("SELECT * FROM Rules WHERE type = 'employee'");
                rs = ps.executeQuery();
                rs.next();
                type = rs.getString("type");
                books_limit = rs.getInt("books_limit");
                max_period = rs.getInt("max_period");
                fine = rs.getFloat("fine_per_day");
                currentBooksLimitEmployeeText.setText(Integer.toString(books_limit));
                currentDaysNumberEmployeeText.setText(Integer.toString(max_period));
                currentFineEmployeeText.setText(Float.toString(fine));
            } catch(Exception ex) {
                ex.printStackTrace();
            }
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(currentLimitStudentLabel, 0, 0, 2, 1);
        grid.add(currentBooksLimitStudentLabel, 0, 1);
        grid.add(currentBooksLimitStudentText, 1, 1);
        grid.add(currentDaysNumberStudentLabel, 0, 2);
        grid.add(currentDaysNumberStudentText, 1, 2);
        grid.add(currentFineStudentLabel, 0, 3);
        grid.add(currentFineStudentText, 1, 3);

        grid.add(currentLimitEmployeeLabel, 0, 5, 2, 1);
        grid.add(currentBooksLimitEmployeeLabel, 0, 6);
        grid.add(currentBooksLimitEmployeeText, 1, 6);
        grid.add(currentDaysNumberEmployeeLabel, 0, 7);
        grid.add(currentDaysNumberEmployeeText, 1, 7);
        grid.add(currentFineEmployeeLabel, 0, 8);
        grid.add(currentFineEmployeeText, 1, 8);

        grid.add(changeLimitsLabel, 0, 10, 2, 1);
        grid.add(changeBooksLimitLabel, 0, 11);
        grid.add(changeBooksLimitTextField, 1, 11);
        grid.add(changeDaysNumberLabel, 0, 12);
        grid.add(changeDaysNumberTextField, 1, 12);
        grid.add(changeFineLabel, 0, 13);
        grid.add(changeFineTextField, 1, 13);

        hbox = new HBox(10);
        hbox.getChildren().addAll(studentRadioButton, employeeRadioButton);
        grid.add(hbox, 1, 14);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(changeButton);
        grid.add(hbox, 1, 15);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }
}
