package lms.gui.tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lms.gui.SignInGridPane;
import lms.gui.scenes.AdminScene;
import lms.gui.scenes.LibrarianScene;
import lms.gui.scenes.ServiceScene;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LogOutTab {
    private Connection connection;

    public LogOutTab(TabPane tabPane, Stage primaryStage) {
        Tab tab = new Tab();
        tab.setText("Log Out");

        Button logOutButton = new Button("Log Out");

        logOutButton.setOnAction(e -> {
            SignInGridPane grid = new SignInGridPane();

            Scene signInScene = new Scene(grid, 300, 275);
            signInScene.setFill(Color.WHITE);

            Pane newPane = new Pane();

            primaryStage.setScene(signInScene);

            grid.getSignInButton().setOnAction(a -> {
                String login = grid.getUserNameField().getText();
                grid.getUserNameField().setText("");
                String password = grid.getPasswdField().getText();
                grid.getPasswdField().setText("");
                String url = "jdbc:mariadb://localhost:3306/LMS?user=" + login + "&password=" + password;

                try {
                    getConnection(url);
                    PreparedStatement ps = connection.prepareStatement("SELECT type FROM Users WHERE login = ?");
                    ps.setString(1, login);
                    ResultSet rs = ps.executeQuery();
                    rs.next();
                    String text = rs.getString("type");
                    if(text.equals("admin")) {
                        primaryStage.setScene(new AdminScene(newPane, connection, primaryStage));
                        primaryStage.centerOnScreen();
                    }
                    else if(text.equals("librarian")) {
                        primaryStage.setScene(new LibrarianScene(newPane, connection, primaryStage));
                        primaryStage.centerOnScreen();
                    }
                    else if(text.equals("service")) {
                        primaryStage.setScene(new ServiceScene(newPane, connection, primaryStage));
                        primaryStage.centerOnScreen();
                    }
                    else {
                        throw new Exception();
                    }
                } catch(Exception ex) {
                    grid.getActionTarget().setText("Wrong data");
                }
            });
        });

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(logOutButton, 0, 0);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }

    private void getConnection(String url) throws Exception {
        connection = DriverManager.getConnection(url);
    }
}
