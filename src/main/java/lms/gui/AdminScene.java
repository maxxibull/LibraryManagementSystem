package lms.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lms.gui.tabs.*;

import java.sql.Connection;

public class AdminScene extends Scene {
    private TabPane tabPane;
    private BorderPane borderPane;
    private Tab tab;
    private Connection connection;

    AdminScene(Pane pane, Connection connection) {
        super(pane, 800, 700);
        this.connection = connection;
        setFill(Color.WHITE);

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        borderPane = new BorderPane();

        new UsersTab(tabPane, connection);
        new ClientsTab(tabPane, connection);
        new BooksInfoTab(tabPane, connection);
        new BooksEditionTab(tabPane);
        new AuthorsTab(tabPane);
        new CopiesTab(tabPane);
        new LimitsTab(tabPane);
        new ClientsInfoTab(tabPane);

        borderPane.prefHeightProperty().bind(this.heightProperty());
        borderPane.prefWidthProperty().bind(this.widthProperty());

        borderPane.setCenter(tabPane);

        pane.getChildren().add(borderPane);

    }
}
