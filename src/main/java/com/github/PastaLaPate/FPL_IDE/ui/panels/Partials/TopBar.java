package com.github.PastaLaPate.FPL_IDE.ui.panels.Partials;

import com.github.PastaLaPate.FPL_IDE.ui.Constants;
import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class TopBar extends Panel {
    Label title;
    GridPane topBarButton;

    public TopBar(PanelManager panelManager) {
        super(panelManager);
        this.panelName = "TopBar";
    }

    @Override
    public void initComponents() {
        super.initComponents();
        this.layout.setStyle("-fx-background-color: rgb(35, 40, 40);");
        setCanTakeAllWidth(this.layout);

        // TopBar: left side
        ImageView imageView = new ImageView();
        imageView.setImage(new Image("images/logo.png"));
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setTranslateX(10);
        setLeft(imageView);
        this.layout.getChildren().add(imageView);

        // TopBar: center
        title = new Label("FPL IDE");
        title.setFont(Font.font("Consolas", FontWeight.BOLD, FontPosture.REGULAR, 18f));
        title.setStyle("-fx-text-fill: white;");
        setCenterH(title);
        this.layout.getChildren().add(title);

        // TopBar: right side
        topBarButton = new GridPane();
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
        setCenterV(closeBtn);

        fullscreenBtn.setOpacity(0.70f);
        resizeTopBarButton(fullscreenBtn);
        fullscreenBtn.setOnMouseEntered(e -> fullscreenBtn.setOpacity(1.0f));
        fullscreenBtn.setOnMouseExited(e -> fullscreenBtn.setOpacity(0.7f));
        fullscreenBtn.setOnMouseClicked(e -> this.panelManager.getStage().setMaximized(!this.panelManager.getStage().isMaximized()));
        fullscreenBtn.setTranslateX(35.0d);
        setCenterV(fullscreenBtn);

        minimizeBtn.setOpacity(0.70f);
        resizeTopBarButton(minimizeBtn);
        minimizeBtn.setOnMouseEntered(e -> minimizeBtn.setOpacity(1.0f));
        minimizeBtn.setOnMouseExited(e -> minimizeBtn.setOpacity(0.7f));
        minimizeBtn.setOnMouseClicked(e -> this.panelManager.getStage().setIconified(true));
        minimizeBtn.setTranslateX(.0d);
        setCenterV(minimizeBtn);
        topBarButton.getChildren().addAll(closeBtn, fullscreenBtn, minimizeBtn);
    }

    public void makeRunButtonEnabled(RunHandler runHandler) {
        ImageView runBtn = new ImageView("images/run.png");
        setCanTakeAllWidth(runBtn);
        runBtn.setOpacity(0.7f);
        resizeTopBarButton(runBtn);
        runBtn.setOnMouseEntered(e -> runBtn.setOpacity(1.f));
        runBtn.setOnMouseExited(e -> runBtn.setOpacity(.7f));
        runBtn.setOnMouseClicked(e -> runHandler.run());
        runBtn.setTranslateX(70f);
        runBtn.setTranslateY(25f);
        topBarButton.getChildren().add(runBtn);
    }

    public void makeSaveButtonEnabled() {
        Button button = new Button("Save");
        setCanTakeAllWidth(button);
        button.setTranslateX(50);
        button.setTranslateY(12);
        button.setBackground(null);
        button.setFont(new Font(Constants.JetBrainsMono.getFamily(), 15));
        button.setStyle("-fx-text-fill: white");
        layout.getChildren().add(button);
    }

    private void resizeTopBarButton(ImageView imageView) {
        imageView.setFitWidth(20);
        imageView.setFitHeight(20);
    }

    public interface RunHandler {
        void run();
    }
}
