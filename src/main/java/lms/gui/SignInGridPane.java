package lms.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class SignInGridPane extends GridPane {
    private Text headline;
    private Label userName, passwd;
    private TextField userNameField;
    private PasswordField passwdField;
    private Button signInButton;
    private HBox hBoxSignInButton;

    SignInGridPane() {
        super();
        setAlignment(Pos.CENTER);
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(25, 25, 25, 25));

        headline = new Text("Library Management System");
        headline.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        add(headline, 0, 0, 2, 1);

        userName = new Label("Username:");
        add(userName, 0, 1);

        userNameField = new TextField();
        add(userNameField, 1, 1);

        passwd = new Label("Password:");
        add(passwd, 0, 2);

        passwdField = new PasswordField();
        add(passwdField, 1, 2);

        signInButton = new Button("Sign In");
        hBoxSignInButton = new HBox(10);
        hBoxSignInButton.setAlignment(Pos.BOTTOM_RIGHT);
        hBoxSignInButton.getChildren().add(signInButton);
        add(hBoxSignInButton, 1, 4);

        /*final Text actionTarget = new Text();
        grid.add(actionTarget, 0, 4);*/

    }

    public Button getSignInButton() {
        return signInButton;
    }
}
