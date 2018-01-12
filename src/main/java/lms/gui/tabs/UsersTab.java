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

public class UsersTab {
    private Connection connection;
    private ObservableList<ObservableList> observableArrayList;
    private TableView tableView;

    public UsersTab(TabPane tabPane, Connection connection) {
        this.connection = connection;

        HBox hbox = null;
        Tab tab = new Tab();
        ToggleGroup toggleGroup = new ToggleGroup();
        tab.setText("Users");

        Label removeUserLabel = new Label("Remove user");
        removeUserLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeUserLabel, HPos.CENTER);
        Label removeUsernameLabel = new Label("Username");
        GridPane.setHalignment(removeUsernameLabel, HPos.RIGHT);
        TextField removeUsernameTextField = new TextField();
        Button removeButton = new Button("Remove user");
        removeButton.setMinWidth(200);

        removeButton.setOnAction(e -> {
            if(!removeUsernameTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM Users WHERE login = ?");
                    ps.setString(1, removeUsernameTextField.getText());
                    ps.executeQuery();

                    ps = connection.prepareStatement("DROP USER ?@'localhost';");
                    ps.setString(1, removeUsernameTextField.getText());
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            removeUsernameTextField.setText("");
        });

        Label addUserLabel = new Label("Add new user");
        addUserLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(addUserLabel, HPos.CENTER);
        Label addUsernameLabel = new Label("Username");
        GridPane.setHalignment(addUsernameLabel, HPos.RIGHT);
        TextField addUsernameTextField = new TextField();
        Label addPasswordLabel = new Label("Password");
        GridPane.setHalignment(addPasswordLabel, HPos.RIGHT);
        TextField addPasswordTextField = new PasswordField();
        Label repeatPasswordLabel = new Label("Repeat password");
        GridPane.setHalignment(repeatPasswordLabel, HPos.RIGHT);
        TextField repeatPasswordTextField = new PasswordField();
        RadioButton adminRadioButton = new RadioButton("Admin");
        adminRadioButton.setUserData("admin");
        adminRadioButton.setToggleGroup(toggleGroup);
        adminRadioButton.setSelected(true);
        RadioButton librarianRadioButton = new RadioButton("Librarian");
        librarianRadioButton.setUserData("librarian");
        librarianRadioButton.setToggleGroup(toggleGroup);
        RadioButton serviceRadioButton = new RadioButton("Service");
        serviceRadioButton.setUserData("service");
        serviceRadioButton.setToggleGroup(toggleGroup);
        Button addButton = new Button("Add new user");
        addButton.setMinWidth(200);

        addButton.setOnAction(e -> {
            if(addPasswordTextField.getText().equals(repeatPasswordTextField.getText()) &&
                    !addUsernameTextField.getText().isEmpty() && !addPasswordTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO Users VALUES (?, ?)");
                    ps.setString(1, addUsernameTextField.getText());
                    ps.setString(2, toggleGroup.getSelectedToggle().getUserData().toString());
                    ps.executeQuery();

                    switch(toggleGroup.getSelectedToggle().getUserData().toString()) {
                        case "admin":
                            createAdminAccount(addUsernameTextField.getText(), addPasswordTextField.getText());
                            break;
                        case "librarian":
                            createLibrarianAccount(addUsernameTextField.getText(), addPasswordTextField.getText());
                            break;
                        case "service":
                            createServiceAccount(addUsernameTextField.getText(), addPasswordTextField.getText());
                            break;
                    }
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            addPasswordTextField.setText("");
            repeatPasswordTextField.setText("");
            addUsernameTextField.setText("");
            adminRadioButton.setSelected(true);
        });

        Label changePasswordUserLabel = new Label("Change password");
        changePasswordUserLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(changePasswordUserLabel, HPos.CENTER);
        Label changePasswordUsernameLabel = new Label("Username");
        GridPane.setHalignment(changePasswordUsernameLabel, HPos.RIGHT);
        TextField changePasswordUsernameTextField = new TextField();
        Label changePasswordLabel = new Label("Password");
        GridPane.setHalignment(changePasswordLabel, HPos.RIGHT);
        TextField changePasswordTextField = new PasswordField();
        Label repeatNewPasswordLabel = new Label("Repeat password");
        GridPane.setHalignment(repeatNewPasswordLabel, HPos.RIGHT);
        TextField repeatNewPasswordTextField = new PasswordField();
        Button changePasswordButton = new Button("Change password");
        changePasswordButton.setMinWidth(200);

        changePasswordButton.setOnAction(e -> {
            if(changePasswordTextField.getText().equals(repeatNewPasswordTextField.getText()) &&
                    !changePasswordUsernameTextField.getText().isEmpty() && !changePasswordTextField.getText().isEmpty()) {
                try {
                    PreparedStatement ps = connection.prepareStatement("SET PASSWORD FOR ?@'localhost' = PASSWORD(?);");
                    ps.setString(1, changePasswordUsernameTextField.getText());
                    ps.setString(2, changePasswordTextField.getText());
                    ps.executeQuery();
                } catch(Exception ex) {
                    new AlertError();
                }
            }
            else {
                new AlertError();
            }
            changePasswordUsernameTextField.setText("");
            changePasswordTextField.setText("");
            repeatNewPasswordTextField.setText("");
        });

        Label usersInSystemLabel = new Label("Users");
        usersInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(usersInSystemLabel, HPos.CENTER);
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

        grid.add(addUserLabel, 0, 0, 2, 1);
        grid.add(addUsernameLabel, 0, 1);
        grid.add(addUsernameTextField, 1, 1);
        grid.add(addPasswordLabel, 0, 2);
        grid.add(addPasswordTextField, 1, 2);
        grid.add(repeatPasswordLabel, 0, 3);
        grid.add(repeatPasswordTextField, 1, 3);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().addAll(adminRadioButton, librarianRadioButton, serviceRadioButton);
        grid.add(hbox, 0, 4, 2, 1);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(addButton);
        grid.add(hbox, 1, 5);

        grid.add(changePasswordUserLabel, 0, 7, 2, 1);
        grid.add(changePasswordUsernameLabel, 0, 8);
        grid.add(changePasswordUsernameTextField, 1, 8);
        grid.add(changePasswordLabel, 0, 9);
        grid.add(changePasswordTextField, 1, 9);
        grid.add(repeatNewPasswordLabel, 0, 10);
        grid.add(repeatNewPasswordTextField, 1, 10);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(changePasswordButton);
        grid.add(hbox, 1, 11);

        grid.add(removeUserLabel, 0, 13, 2, 1);
        grid.add(removeUsernameLabel, 0, 14);
        grid.add(removeUsernameTextField, 1, 14);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(removeButton);
        grid.add(hbox, 1, 15);

        grid.add(usersInSystemLabel, 4, 0, 3, 1);

        observableArrayList = FXCollections.observableArrayList();
        tableView = new TableView();
        fillTable();

        grid.add(tableView, 4, 1, 3, 14);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(refreshButton);
        grid.add(hbox, 6, 15);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }

    private void fillTable() {
        try {
            ResultSet rs = connection.createStatement().executeQuery("SELECT login, type FROM Users");

            for(int i = 0 ; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setMinWidth(125);
                col.setCellValueFactory((Callback <TableColumn.CellDataFeatures <ObservableList, String>,
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

    private void createAdminAccount(String login, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE USER ?@'localhost';");
            ps.setString(1, login);
            ps.executeQuery();

            ps = connection.prepareStatement("SET PASSWORD FOR ?@'localhost' = PASSWORD(?);");
            ps.setString(1, login);
            ps.setString(2, password);
            ps.executeQuery();

            ps = connection.prepareStatement("GRANT ALL ON *.* TO ?@'localhost' WITH GRANT OPTION;");
            ps.setString(1, login);
            ps.executeQuery();

            ps = connection.prepareStatement("GRANT CREATE USER ON *.* TO ?@'localhost';");
            ps.setString(1, login);
            ps.executeQuery();

            ps = connection.prepareStatement("FLUSH PRIVILEGES;");
            ps.executeQuery();
        } catch(Exception ex) {
            ex.printStackTrace();
            new AlertError();
        }
    }

    private void createLibrarianAccount(String login, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE USER ?@'localhost';");
            ps.setString(1, login);
            ps.executeQuery();

            ps = connection.prepareStatement("SET PASSWORD FOR ?@'localhost' = PASSWORD(?);");
            ps.setString(1, login);
            ps.setString(2, password);
            ps.executeQuery();

            ps = connection.prepareStatement("GRANT INSERT, UPDATE, DELETE, SELECT ON LMS.* TO ?@'localhost';");
            ps.setString(1, login);
            ps.executeQuery();

            ps = connection.prepareStatement("FLUSH PRIVILEGES;");
            ps.executeQuery();
        } catch(Exception ex) {
            ex.printStackTrace();
            new AlertError();
        }
    }

    private void createServiceAccount(String login, String password) {
        try {
            PreparedStatement ps = connection.prepareStatement("CREATE USER ?@'localhost';");
            ps.setString(1, login);
            ps.executeQuery();

            ps = connection.prepareStatement("SET PASSWORD FOR ?@'localhost' = PASSWORD(?);");
            ps.setString(1, login);
            ps.setString(2, password);
            ps.executeQuery();

            ps = connection.prepareStatement("GRANT INSERT, UPDATE, DELETE ON LMS.On_loan TO ?@'localhost';");
            ps.setString(1, login);
            ps.executeQuery();

            ps = connection.prepareStatement("GRANT SELECT ON LMS.* TO ?@'localhost';");
            ps.setString(1, login);
            ps.executeQuery();

            ps = connection.prepareStatement("FLUSH PRIVILEGES;");
            ps.executeQuery();
        } catch(Exception ex) {
            ex.printStackTrace();
            new AlertError();
        }
    }
}
