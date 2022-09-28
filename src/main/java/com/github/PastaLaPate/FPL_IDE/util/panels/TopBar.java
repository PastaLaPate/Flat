package com.github.PastaLaPate.FPL_IDE.util.panels;

import com.github.PastaLaPate.FPL_IDE.Constants;
import com.github.PastaLaPate.FPL_IDE.fpl.Runner;
import com.github.PastaLaPate.FPL_IDE.fpl.Saver;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;
import com.github.PastaLaPate.FPL_IDE.util.logger.Level;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;
import com.github.PastaLaPate.FPL_IDE.util.syntax.Syntax;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class TopBar {

    private String currentFileName = "main.fpl";
    private final JFrame f;
    private final JTextPane tPane;
    private final Files files;
    private JSplitPane splitPane;
    public TopBar(JFrame f, JTextPane tPane, Files files) {
        this.f = f;
        this.tPane = tPane;
        this.files = files;
    }

    public void setSplitPane(JSplitPane splitPane) {
        this.splitPane = splitPane;
    }

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        JMenuItem copyItem = new JMenuItem("Copy");
        makeElement1(copyItem);
        editMenu.add(copyItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        makeElement1(pasteItem);
        editMenu.add(pasteItem);
        editMenu.setForeground(Constants.TEXT);
        removeBorder(editMenu);
        return editMenu;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> {
            Logger.log("Save button clicked", this.getClass(), Level.INFO);
            saveFile();
        });
        makeElement1(saveItem);
        fileMenu.add(saveItem);
        JMenuItem saveAs = new JMenuItem("Save as");
        saveAs.addActionListener(e -> {
            Logger.log("Save as button clicked", this.getClass(), Level.INFO);
            saveAsFile();
        });
        makeElement1(saveAs);
        fileMenu.add(saveAs);
        JMenuItem loadItem = new JMenuItem("load");
        loadItem.addActionListener(e -> {
            //SAVE FILE
            Logger.log("Load button clicked", this.getClass(), Level.INFO);
            loadFile();
        });
        makeElement1(loadItem);
        fileMenu.add(loadItem);
        JMenuItem runItem = new JMenuItem("Run");
        runItem.addActionListener(e -> {
            Logger.log("Run button clicked", this.getClass(), Level.INFO);
            saveFile();
            try {
                new Runner().Run();
            } catch (IOException ex) {
                Logger.log(ex);
            }
        });
        makeElement1(runItem);
        fileMenu.add(runItem);
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            try {
                new About();
            } catch (IOException ex) {
                Logger.log(ex);
            }
        });
        makeElement1(aboutItem);
        fileMenu.add(aboutItem);
        fileMenu.setForeground(Constants.TEXT);
        removeBorder(fileMenu);
        return fileMenu;
    }

    private void saveAsFile() {
        FileDialog fileDialog = new FileDialog(f, "Select File", FileDialog.SAVE);
        try {
            fileDialog.setDirectory(Downloader.getPathFolder());
            fileDialog.setFilenameFilter((dir, name) -> name.toLowerCase().endsWith(".fpl"));
        } catch (IOException ex) {
            Logger.log(ex);
        }
        fileDialog.setVisible(true);

        String file = fileDialog.getFile();
        File file1;
        if (file != null) {
            file1 = new File(file);
            Saver saver = new Saver();
            saver.saveFile(file1.getPath(), tPane.getText());
        }
    }

    private void saveFile() {
        String path = "";
        try {
            path = Downloader.getPathFolder() + currentFileName;
        } catch (IOException ex) {
            Logger.log(ex);
        }
        Saver saver = new Saver();
        saver.saveFile(path, tPane.getText());
    }

    private void loadFile() {
        String path = "";
        try {
            path = Downloader.getPathFolder() + currentFileName;
        } catch (IOException ex) {
            Logger.log(ex);
        }
        Saver saver = new Saver();
        saver.saveFile(path, tPane.getText());

        //LOAD NEW FILE
        FileDialog fileDialog = new FileDialog(f, "Select file", FileDialog.LOAD);
        fileDialog.setFile("*.fpl");
        try {
            fileDialog.setDirectory(Downloader.getPathFolder());
        } catch (IOException ex) {
            Logger.log(ex);
        }
        fileDialog.setVisible(true);
        String file = fileDialog.getFile();
        File file1;
        Logger.log(fileDialog.getDirectory());
        Logger.log(file);
        if (file != null) {
            file1 = new File(file);
            try {
                String r = saver.getFile(fileDialog.getDirectory() + file1.getPath());
                tPane.setText(r);
                currentFileName = file1.getPath();
                files.addFile(file1.getPath());
                new Syntax().generateSyntax(tPane);
            } catch (IOException ex) {
                Logger.log(ex);
            }
        }
    }

    public void loadFile(String fileName) {
        String path = "";
        try {
            path = Downloader.getPathFolder() + currentFileName;
        } catch (IOException ex) {
            Logger.log(ex);
        }
        Saver saver = new Saver();
        saver.saveFile(path, tPane.getText());

        //LOAD NEW FILE
        File file1;
        try {
            file1 = new File(Downloader.getPathFolder() + fileName);
            String r = saver.getFile(file1.getPath());
            tPane.setText(r);
            currentFileName = fileName;
            new Syntax().generateSyntax(tPane);
        } catch (IOException e) {
            Logger.log(e);
        }

    }

    public void makeElement1(JMenuItem menuItem) {
        menuItem.setForeground(Constants.TEXT);
        menuItem.setBackground(Constants.ELEMENT1);
        removeBorder(menuItem);
    }

    public void removeBorder(JComponent component) {
        Border emptyBorder = BorderFactory.createEmptyBorder();
        component.setBorder(emptyBorder);
    }

    public JMenuBar createMenuBar(boolean withJMenu) {
        JMenuBar menuBar = new JMenuBar();
        URL closeIconURL;
        JButton closeButton;
        JButton maximizeButton;
        JButton minimizeButton;
        try {
            closeIconURL = new URL("https://www.freeiconspng.com/uploads/close-icon-48.png");
            Image image = ImageIO.read(closeIconURL);
            closeButton = new JButton(new ImageIcon(image.getScaledInstance(15, 15, 0)));
            closeButton.setContentAreaFilled(false);
            closeButton.setFocusPainted(false);
            closeButton.setBorderPainted(false);
            closeButton.addActionListener(e -> {
                Logger.log("CLOSING APP", this.getClass(), Level.INFO);
                System.exit(0);
            });
            URL maximizeIconURL = new URL("https://cdn2.iconfinder.com/data/icons/material-line/1024/maximize-256.png");
            Image image1 = ImageIO.read(maximizeIconURL);
            maximizeButton = new JButton(new ImageIcon(image1.getScaledInstance(15,15, 0)));
            maximizeButton.setContentAreaFilled(false);
            maximizeButton.setFocusPainted(false);
            maximizeButton.setBorderPainted(false);
            maximizeButton.addActionListener(e -> {
                Logger.log("Maximizing app", this.getClass(), Level.INFO);
                if (f.getExtendedState() == 0) {
                    f.setExtendedState(JFrame.MAXIMIZED_BOTH);
                } else {
                    f.setExtendedState(0);
                    f.setState(Frame.NORMAL);
                }
                splitPane.updateUI();
            });
            URL minimizeIconURL = new URL("https://cdn0.iconfinder.com/data/icons/basic-ui-set-2/100/Essential_Icons-77-512.png");
            Image image2 = ImageIO.read(minimizeIconURL);
            minimizeButton = new JButton(new ImageIcon(image2.getScaledInstance(15,15,0)));
            minimizeButton.setContentAreaFilled(false);
            minimizeButton.setFocusPainted(false);
            minimizeButton.setBorderPainted(false);
            minimizeButton.addActionListener(e -> {
                Logger.log("Minimizing app", this.getClass(), Level.INFO);
                f.setState(Frame.ICONIFIED);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        menuBar.setBackground(new Color(58, 58, 58));
        if (withJMenu) {
            menuBar.add(createFileMenu());
            menuBar.add(createEditMenu());
        }
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(minimizeButton);
        menuBar.add(maximizeButton);
        menuBar.add(closeButton);
        return menuBar;
    }
}
