package com.github.PastaLaPate.FPL_IDE.ui;

import com.github.PastaLaPate.FPL_IDE.Main;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.MainPanel;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes.Console;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Partials.TopBar;
import com.github.PastaLaPate.FPL_IDE.util.Platform;
import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class PanelManager {

    private final Stage stage;
    private final GridPane contentPane = new GridPane();
    TopBar topBar;

    public PanelManager(Stage stage) {
        this.stage = stage;
        GridPane layout = new GridPane();
        this.stage.setMinWidth(640.0D);
        this.stage.setWidth(640.0D);
        this.stage.setMinHeight(360.0D);
        this.stage.setHeight(360.0D);
        this.stage.setResizable(true);
        this.stage.centerOnScreen();
        this.stage.getIcons().add(new Image("images/logo.png"));
        this.stage.setOnCloseRequest(e -> System.exit(0));

        if (Platform.isOnLinux()) {
            Scene scene = new Scene(layout);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().getResource("css/editor.css")).toExternalForm());
            this.stage.setScene(scene);
        } else {
            this.stage.initStyle(StageStyle.UNDECORATED);

            topBar = new TopBar(this);
            BorderlessScene scene = new BorderlessScene(this.stage, StageStyle.UNDECORATED, layout);
            scene.setResizable(true);
            scene.setMoveControl(topBar.getLayout());
            scene.removeDefaultCSS();
            scene.getStylesheets().add(Objects.requireNonNull(Main.class.getClassLoader().getResource("css/editor.css")).toExternalForm());

            this.stage.setScene(scene);

            RowConstraints topPaneConstraints = new RowConstraints();
            topPaneConstraints.setValignment(VPos.TOP);
            topPaneConstraints.setMinHeight(50);
            topPaneConstraints.setMaxHeight(100);

            layout.getRowConstraints().addAll(topPaneConstraints, new RowConstraints());
            layout.add(topBar.getLayout(), 0, 0);
        }
        layout.add(contentPane, 0, 1);
        GridPane.setVgrow(this.contentPane, Priority.ALWAYS);
        GridPane.setHgrow(this.contentPane, Priority.ALWAYS);
        GridPane.setVgrow(layout, Priority.ALWAYS);
        GridPane.setHgrow(layout, Priority.ALWAYS);
        this.stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public void show(IPanel panel) {
        contentPane.getChildren().removeAll();
        javafx.application.Platform.runLater(() -> {
            contentPane.getChildren().add(panel.getLayout());
            stage.setTitle("FPL IDE " + Main.VERSION + " - " + panel.getPanelName());
            panel.getDimension().setStageDimension(stage);
            panel.onShow();
            if (panel instanceof MainPanel) {
                topBar.makeRunButtonEnabled(Console.getRunHandler());
                topBar.makeSaveButtonEnabled();
            }
        });
    }
}