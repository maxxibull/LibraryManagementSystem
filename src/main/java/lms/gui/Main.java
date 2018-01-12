package lms.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lms.gui.scenes.AdminScene;
import lms.gui.scenes.LibrarianScene;
import lms.gui.scenes.ServiceScene;

import java.sql.*;

public class Main extends Application {
    private Connection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {
        SignInGridPane grid = new SignInGridPane();

        Scene signInScene = new Scene(grid, 300, 275);
        signInScene.setFill(Color.WHITE);

        Pane newPane = new Pane();

        primaryStage.setTitle("Library Management System");
        primaryStage.setScene(signInScene);
        primaryStage.show();

        grid.getSignInButton().setOnAction(e -> {
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
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void getConnection(String url) throws Exception {
        connection = DriverManager.getConnection(url);
    }
}
