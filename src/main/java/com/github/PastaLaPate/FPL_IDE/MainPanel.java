package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.ui.panels.TextEditor;
import com.github.PastaLaPate.FPL_IDE.util.AutoCompletion.Autocompleter;
import com.github.PastaLaPate.FPL_IDE.util.Saver;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Files;
import com.github.PastaLaPate.FPL_IDE.ui.panels.TopBar;
import com.github.PastaLaPate.FPL_IDE.util.syntax.Syntax;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class MainPanel extends JFrame{

    private TextEditor tPane;
    private JFrame f;
    private TopBar topBar;

    private static final Point point = new Point();

    private Files files;

    public void init() {
        SwingUtilities.invokeLater(() -> {
            //BASE
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            f = this;
            setUndecorated(true);
            setResizable(true);
            getContentPane().setBackground(Constants.BACKGROUND);
            setTitle("FPL_IDE - Main.fpl");
            f.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e) {
                    point.x = e.getX();
                    point.y = e.getY();
                }
            });
            f.addMouseMotionListener(new MouseMotionAdapter() {
                public void mouseDragged(MouseEvent e) {
                    Point p = f.getLocation();
                    f.setLocation(p.x + e.getX() - point.x, p.y + e.getY() - point.y);
                }
            });

            //CONTENT
            //TEXT EDITOR
            tPane = new TextEditor();
            try {
                tPane.loadFile(Downloader.getPathFolder() + "main.fpl");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //FILES
            files = new Files();
            ScrollPane scrollPane = new ScrollPane();

            // TOP BAR
            topBar = new TopBar(f, tPane, files, this);
            setJMenuBar(topBar.createMenuBar(true));

            //LOAD FILE
            ScrollPane sPane = files.init(this);
            files.setListener(topBar::loadFile);
            files.addFile("main.fpl");
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sPane, scrollPane);
            splitPane.setOneTouchExpandable(true);
            splitPane.setDividerLocation(100);
            topBar.setSplitPane(splitPane);

            sPane.setMinimumSize(new Dimension(100, 100));
            sPane.setMaximumSize(new Dimension(500, 300));

            scrollPane.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

            setContentPane(splitPane);
            makeMaximized();
            scrollPane.add(tPane);
            setVisible(true);
        });
    }

    public void makeMaximized() {
        // Gets the screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Gets the width and height
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        //height of the task bar
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;
        setSize((int)width, (int)height - taskBarSize);
        pack();
    }
}
