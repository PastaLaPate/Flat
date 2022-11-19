package com.github.PastaLaPate.FPL_IDE.ui.panels.Partials;

import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class TopBar extends Panel {
    private GridPane topBar;
    Label title;

    public TopBar(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "TopBar";
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    @Override
    public void initComponents() {
        super.initComponents();
        this.topBar = this.layout;
        this.layout.setStyle("-fx-background-color: rgb(35, 40, 40);");
        setCanTakeAllWidth(this.topBar);

        // TopBar: center
        title = new Label("FPL IDE");
        title.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 18f));
        title.setStyle("-fx-text-fill: white;");
        setCenterH(title);
        this.layout.getChildren().add(title);

        // TopBar: right side
        GridPane topBarButton = new GridPane();
        topBarButton.setMinWidth(100d);
        topBarButton.setMaxWidth(100d);
        setCanTakeAllSize(topBarButton);
        setRight(topBarButton);
        this.layout.getChildren().add(topBarButton);

        ImageView closeBtn = new ImageView("images/cross.png");
        ImageView fullscreenBtn = new ImageView("images/maximize.png");
        ImageView minimizeBtn = new ImageView("images/minus.png");

        setCanTakeAllWidth(closeBtn, fullscreenBtn, minimizeBtn);
        closeBtn.setOpacity(0.7f);
        resizeTopBarButton(closeBtn);
        closeBtn.setOnMouseEntered(e -> closeBtn.setOpacity(1.f));
        closeBtn.setOnMouseExited(e -> closeBtn.setOpacity(.7f));
        closeBtn.setOnMouseClicked(e -> System.exit(0));
        closeBtn.setTranslateX(70f);

        fullscreenBtn.setOpacity(0.70f);
        resizeTopBarButton(fullscreenBtn);
        fullscreenBtn.setOnMouseEntered(e -> fullscreenBtn.setOpacity(1.0f));
        fullscreenBtn.setOnMouseExited(e -> fullscreenBtn.setOpacity(0.7f));
        fullscreenBtn.setOnMouseClicked(e -> this.panelManager.getStage().setMaximized(!this.panelManager.getStage().isMaximized()));
        fullscreenBtn.setTranslateX(35.0d);

        minimizeBtn.setOpacity(0.70f);
        resizeTopBarButton(minimizeBtn);
        minimizeBtn.setOnMouseEntered(e -> minimizeBtn.setOpacity(1.0f));
        minimizeBtn.setOnMouseExited(e -> minimizeBtn.setOpacity(0.7f));
        minimizeBtn.setOnMouseClicked(e -> this.panelManager.getStage().setIconified(true));
        minimizeBtn.setTranslateX(.0d);
        topBarButton.getChildren().addAll(closeBtn, fullscreenBtn, minimizeBtn);
    }

    private void resizeTopBarButton(ImageView imageView) {
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
    }
}
