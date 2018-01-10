package lms.gui.tabs;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UsersTab {
    public UsersTab(TabPane tabPane) {
        HBox hbox = null;
        Tab tab = new Tab();
        tab.setText("Users");

        Label removeUserLabel = new Label("Remove user");
        removeUserLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(removeUserLabel, HPos.CENTER);
        Label removeUsernameLabel = new Label("Username");
        GridPane.setHalignment(removeUsernameLabel, HPos.RIGHT);
        TextField removeUsernameTextField = new TextField();
        Button removeButton = new Button("Remove user");
        removeButton.setMinWidth(200);

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
        Button addButton = new Button("Add new user");
        addButton.setMinWidth(200);

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

        Label usersInSystemLabel = new Label("Users");
        usersInSystemLabel.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        GridPane.setHalignment(usersInSystemLabel, HPos.CENTER);

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
        hbox.getChildren().add(addButton);
        grid.add(hbox, 1, 4);

        grid.add(changePasswordUserLabel, 0, 6, 2, 1);
        grid.add(changePasswordUsernameLabel, 0, 7);
        grid.add(changePasswordUsernameTextField, 1, 7);
        grid.add(changePasswordLabel, 0, 8);
        grid.add(changePasswordTextField, 1, 8);
        grid.add(repeatNewPasswordLabel, 0, 9);
        grid.add(repeatNewPasswordTextField, 1, 9);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(changePasswordButton);
        grid.add(hbox, 1, 10);

        grid.add(removeUserLabel, 0, 12, 2, 1);
        grid.add(removeUsernameLabel, 0, 13);
        grid.add(removeUsernameTextField, 1, 13);

        hbox = new HBox(10);
        hbox.setAlignment(Pos.BOTTOM_RIGHT);
        hbox.getChildren().add(removeButton);
        grid.add(hbox, 1, 14);

        grid.add(usersInSystemLabel, 4, 0, 3, 1);
        ListView<String> listOfUsers = new ListView<>();
        grid.add(listOfUsers, 4, 1, 3, 14);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }
}
