package com.github.PastaLaPate.FPL_IDE.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Constants {
    public static final Color BACKGROUND = Color.rgb(39, 39, 39);
    public static final Color ELEMENT1 = Color.rgb(47, 47, 47);
    //public static final Color ELEMENT2 = new Color(54, 54, 54);
    public static final Color TEXT = Color.rgb(200, 200, 200);

    public static final Font JetBrainsMono =  Font.loadFont(URLDecoder.decode(Objects.requireNonNull(Constants.class.getClassLoader().getResource("fonts/EditorFont.ttf")).toExternalForm(), StandardCharsets.UTF_8), 20);
}