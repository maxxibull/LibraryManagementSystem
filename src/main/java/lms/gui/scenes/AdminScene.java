package lms.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lms.gui.tabs.*;

import java.sql.Connection;

public class AdminScene extends Scene {
    private TabPane tabPane;
    private BorderPane borderPane;
    private Connection connection;

    public AdminScene(Pane pane, Connection connection, Stage primaryStage) {
        super(pane, 900, 700);
        this.connection = connection;

        setFill(Color.WHITE);

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        borderPane = new BorderPane();

        new UsersTab(tabPane, connection);
        new LimitsTab(tabPane, connection);
        new ClientsTab(tabPane, connection);
        new ClientsInfoTab(tabPane, connection);
        new BooksInfoTab(tabPane, connection);
        new AuthorsTab(tabPane, connection);
        new BookAndAuthorTab(tabPane, connection);
        new BooksEditionTab(tabPane, connection);
        new CopiesTab(tabPane, connection);
        new ManagementLoans(tabPane, connection);
        new HistoryTab(tabPane, connection);
        new BackupTab(tabPane, connection);
        new LogOutTab(tabPane, primaryStage);

        borderPane.prefHeightProperty().bind(this.heightProperty());
        borderPane.prefWidthProperty().bind(this.widthProperty());

        borderPane.setCenter(tabPane);

        pane.getChildren().add(borderPane);

    }
}
