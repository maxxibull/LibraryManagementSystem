package lms.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SignInGridPane grid = new SignInGridPane();

        Scene signInScene = new Scene(grid, 300, 275);
        signInScene.setFill(Color.WHITE);

        Pane newPane = new Pane();
        AdminScene adminScene = new AdminScene(newPane);

        primaryStage.setTitle("LMS");
        primaryStage.setScene(signInScene);
        primaryStage.show();

        Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/Lab03?user=root&password=");

        grid.getSignInButton().setOnAction(e -> {
            primaryStage.setScene(adminScene);
            primaryStage.centerOnScreen();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
