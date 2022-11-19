package com.github.PastaLaPate.FPL_IDE.ui;

import com.github.PastaLaPate.FPL_IDE.Main;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Partials.TopBar;
import com.github.PastaLaPate.FPL_IDE.util.Platform;
import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class PanelManager {

    private final Stage stage;
    private GridPane layout;
    private final GridPane contentPane = new GridPane();

    public PanelManager(Stage stage) {
        this.stage = stage;
        this.layout = new GridPane();
        this.stage.setMinWidth(640.0D);
        this.stage.setWidth(640.0D);
        this.stage.setMinHeight(360.0D);
        this.stage.setHeight(360.0D);
        this.stage.setResizable(true);
        this.stage.centerOnScreen();
        this.stage.setOnCloseRequest(e -> System.exit(0));

        if (Platform.isOnLinux()) {
            Scene scene = new Scene(this.layout);
            this.stage.setScene(scene);
        } else {
            this.stage.initStyle(StageStyle.UNDECORATED);

            TopBar topBar = new TopBar(this);
            BorderlessScene scene = new BorderlessScene(this.stage, StageStyle.UNDECORATED, this.layout);
            scene.setResizable(true);
            scene.setMoveControl(topBar.getLayout());
            scene.removeDefaultCSS();

            this.stage.setScene(scene);

            RowConstraints topPaneContraints = new RowConstraints();
            topPaneContraints.setValignment(VPos.TOP);
            topPaneContraints.setMinHeight(25);
            topPaneContraints.setMaxHeight(25);

            this.layout.getRowConstraints().addAll(topPaneContraints, new RowConstraints());
            this.layout.add(topBar.getLayout(), 0, 0);
        }
        this.layout.add(contentPane, 0, 1);
        GridPane.setVgrow(this.contentPane, Priority.ALWAYS);
        GridPane.setHgrow(this.contentPane, Priority.ALWAYS);
        GridPane.setVgrow(this.layout, Priority.ALWAYS);
        GridPane.setHgrow(this.layout, Priority.ALWAYS);
        this.stage.show();
    }

    public Stage getStage() {
        return stage;
    }

    public void show(IPanel panel) {
        contentPane.getChildren().removeAll();
        contentPane.getChildren().add(panel.getLayout());
        stage.setTitle("FPL IDE " + Main.VERSION + " - " + panel.getPanelName());
        panel.getDimension().setStageDimension(stage);
        panel.onShow();
    }
}