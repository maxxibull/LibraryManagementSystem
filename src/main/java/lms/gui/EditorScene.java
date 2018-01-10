package lms.gui;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EditorScene extends Scene {
    private MenuBar menuBar;
    private Menu menu1;
    private Menu menu2;
    private Menu menu3;

    EditorScene() {
        super(new VBox(), 800, 600);
        setFill(Color.WHITE);

        menuBar = new MenuBar();
        menu1 = new Menu("menu1");
        menu2 = new Menu("menu2");
        menu3 = new Menu("menu3");
        menuBar.getMenus().addAll(menu1, menu2, menu3);

        ((VBox) getRoot()).getChildren().addAll(menuBar);
    }
}
