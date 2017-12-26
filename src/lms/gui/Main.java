package lms.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SignInGridPane grid = new SignInGridPane();

        Scene signInScene = new Scene(grid, 300, 275);
        signInScene.setFill(Color.WHITE);

        AdminScene adminScene = new AdminScene();

        primaryStage.setTitle("LMS");
        primaryStage.setScene(signInScene);
        primaryStage.show();

        grid.getSignInButton().setOnAction(e -> {
            primaryStage.setScene(adminScene);
            primaryStage.centerOnScreen();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
