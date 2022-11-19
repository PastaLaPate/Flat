package com.github.PastaLaPate.FPL_IDE.ui;

import javafx.stage.Stage;

public class Dimension {
    public double Height;
    public double Width;

    public Dimension(double height,double width) {
        this.Height = height;
        this.Width = width;
    }

    public void setStageDimension(Stage stage) {
        stage.setHeight(Height);
        stage.setWidth(Width);
        stage.centerOnScreen();
    }
}
