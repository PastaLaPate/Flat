package com.github.PastaLaPate.FPL_IDE.ui;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public interface IPanel {
    void initComponents();
    void onShow();
    GridPane getLayout();

    String getPanelName();

    Dimension getDimension();
    default void setCanTakeAllSize(Node node) {
        GridPane.setHgrow(node, Priority.ALWAYS);
        GridPane.setVgrow(node, Priority.ALWAYS);
    }

    default void setCanTakeAllWidth(Node... nodes) {
        for (Node n : nodes) {
            GridPane.setHgrow(n, Priority.ALWAYS);
        }
    }

    default void setLeft(Node node) {
        GridPane.setHalignment(node, HPos.LEFT);
    }

    default void setRight(Node node) {
        GridPane.setHalignment(node, HPos.RIGHT);
    }


    default void setTop(Node node) {
        GridPane.setValignment(node, VPos.TOP);
    }


    default void setBottom(Node node) {
        GridPane.setValignment(node, VPos.BOTTOM);
    }


    default void setBaseLine(Node node) {
        GridPane.setValignment(node, VPos.BASELINE);
    }


    default void setCenterH(Node node) {
        GridPane.setHalignment(node, HPos.CENTER);
    }


    default void setCenterV(Node node) {
        GridPane.setValignment(node, VPos.CENTER);
    }

}
