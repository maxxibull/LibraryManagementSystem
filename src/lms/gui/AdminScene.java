package lms.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import lms.gui.tabs.ClientsTab;
import lms.gui.tabs.UsersTab;

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

        for (int i = 0; i < 5; i++) {
            tab = new Tab();
            tab.setText("Tab" + i);
            HBox hbox = new HBox();
            hbox.getChildren().add(new Label("Tab" + i));
            hbox.setAlignment(Pos.CENTER);
            tab.setContent(hbox);
            tabPane.getTabs().add(tab);
        }
        borderPane.prefHeightProperty().bind(this.heightProperty());
        borderPane.prefWidthProperty().bind(this.widthProperty());

        borderPane.setCenter(tabPane);

        pane.getChildren().add(borderPane);

    }
}
