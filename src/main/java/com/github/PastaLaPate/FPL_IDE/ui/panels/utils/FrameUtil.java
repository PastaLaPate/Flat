package com.github.PastaLaPate.FPL_IDE.ui.panels.utils;

import javax.swing.*;
import java.awt.*;

public class FrameUtil {
    public static void makeDefaultSize(JFrame f) {
        f.setSize(getDefaultSize(f));
        f.setLocationRelativeTo(null);
    }

    public static void makeMaximized(JFrame j) {
        j.setSize(getMaxSize(j));
        j.setLocationRelativeTo(null);
    }

    private static Dimension getDefaultSize(JFrame frame) {
        Dimension dimension = getMaxSize(frame);
        dimension = new Dimension(dimension.width / 2, dimension.height / 2);
        return dimension;
    }

    public static Dimension getMaxSize(JFrame f) {
        // Gets the screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Gets the width and height
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        //height of the task bar
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(f.getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;
        return new Dimension((int) width, (int) (height - taskBarSize));
    }
}
