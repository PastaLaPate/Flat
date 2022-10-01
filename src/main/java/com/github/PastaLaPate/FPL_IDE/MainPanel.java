package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.ui.panels.editor.EditorTabsPanel;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Files.Files;
import com.github.PastaLaPate.FPL_IDE.ui.panels.editor.TopBar;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class MainPanel extends JFrame{

    public EditorTabsPanel editorTabsPanel;
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
            editorTabsPanel = new EditorTabsPanel();
            try {
                File mainFile = new File(Downloader.getPathFolder() + "main.fpl");
                Logger.log(mainFile);
                editorTabsPanel.openFile(mainFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //FILES
            files = new Files();

            // TOP BAR
            topBar = new TopBar(f, files, this);
            setJMenuBar(topBar.createMenuBar(true));

            //LOAD FILE
            ScrollPane sPane = files.init(this);
            files.setListener(topBar::loadFile);
            files.addFile("main.fpl");
            JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, sPane, editorTabsPanel);
            splitPane.setOneTouchExpandable(true);
            splitPane.setDividerLocation(100);
            topBar.setSplitPane(splitPane);

            sPane.setMinimumSize(new Dimension(100, 100));
            sPane.setMaximumSize(new Dimension(500, 300));

            editorTabsPanel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

            setContentPane(splitPane);
            pack();
            makeMaximized();
            setVisible(true);
        });
    }

    public void makeDeufaultSize() {
        setSize(getDefaultSize());
        setLocationRelativeTo(null);
    }

    public void makeMaximized() {
        setSize(getMaxSize());
        setLocationRelativeTo(null);
    }

    public Dimension getDefaultSize() {
        Dimension dimension = getMaxSize();
        dimension = new Dimension(dimension.width / 2, dimension.height / 2);
        return dimension;
    }

    public Dimension getMaxSize() {
        // Gets the screen resolution
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // Gets the width and height
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        //height of the task bar
        Insets scnMax = Toolkit.getDefaultToolkit().getScreenInsets(getGraphicsConfiguration());
        int taskBarSize = scnMax.bottom;
        return new Dimension((int) width, (int) (height - taskBarSize));
    }
}
