package lms.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lms.gui.tabs.*;

public class AdminScene extends Scene {
    private TabPane tabPane;
    private BorderPane borderPane;
    private Tab tab;

    AdminScene(Pane pane) {
        super(pane, 800, 700);
        setFill(Color.WHITE);

        tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        borderPane = new BorderPane();

        new UsersTab(tabPane);
        new ClientsTab(tabPane);
        new BooksInfoTab(tabPane);
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
