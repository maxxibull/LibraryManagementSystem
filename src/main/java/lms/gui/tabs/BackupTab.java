package lms.gui.tabs;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import lms.gui.alerts.AlertError;

import java.sql.Connection;

public class BackupTab {

    public BackupTab(TabPane tabPane, Connection connection) {
        Tab tab = new Tab();
        tab.setText("Backup");

        Button backupButton = new Button("Create backup");
        Button restoreButton = new Button("Restore backup");

        backupButton.setOnAction(e -> makeBackup());

        restoreButton.setOnAction(e -> restoreBackup());

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(backupButton, 0, 0);
        grid.add(restoreButton, 0, 1);

        tab.setContent(grid);
        tabPane.getTabs().add(tab);
    }

    /* You need to specify paths for backups if you want to restore database from sql script.
        backup.sql - file (in the main directory) that create appropriate database (LMS)
     */
    private void makeBackup() {
        Process p = null;
        try {
            Runtime runtime = Runtime.getRuntime();
            p = runtime.exec("mysqldump -u admin -padmin LMS -r backup.sql"); // change path

            int processComplete = p.waitFor();

            if (processComplete != 0) {
                new AlertError();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void restoreBackup() {
        // specify user, password and source path
        String[] restoreCmd = new String[]{"mysql ", "--user=admin --password=admin -e", "source backup.sql"};

        Process runtimeProcess;
        try {

            runtimeProcess = Runtime.getRuntime().exec(restoreCmd);
            int processComplete = runtimeProcess.waitFor();

            if (processComplete != 0) {
                new AlertError();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
