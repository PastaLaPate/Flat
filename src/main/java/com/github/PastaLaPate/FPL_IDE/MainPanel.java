package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.util.AutoCompletion.Autocompleter;
import com.github.PastaLaPate.FPL_IDE.fpl.Runner;
import com.github.PastaLaPate.FPL_IDE.fpl.Saver;
import com.github.PastaLaPate.FPL_IDE.util.panels.About;
import com.github.PastaLaPate.FPL_IDE.util.syntax.Syntax;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class MainPanel extends JFrame{

    private JTextPane tPane;

    public void init() {
        SwingUtilities.invokeLater(() -> {
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setBackground(new Color(38, 38, 38));
            getContentPane().add(scrollPane);
            getContentPane().setBackground(new Color(38,38,38));
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            tPane = new JTextPane();
            tPane.setEditable(true);
            tPane.setSize(300, 200);
            JFrame f = this;
            Autocompleter autocompleter = new Autocompleter();
            tPane.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    String string = tPane.getText();
                    String delim = "[ ]+";
                    String[] result = string.split(delim);
                    autocompleter.setListener(completion -> {
                        StringBuilder finalS = new StringBuilder();
                        int i = 0;
                        for (String s : result) {
                            if (i != result.length - 1) {
                                finalS.append(" ").append(s);
                                i++;
                            }
                        }
                        finalS.append(" ").append(completion);
                        tPane.setText(finalS.toString());
                        new Syntax().generateSyntax(tPane);
                    });
                    autocompleter.autocomplete(f, result[result.length-1], tPane.getWidth() - 100, tPane.getHeight() - 45);
                    new Syntax().generateSyntax(tPane);
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyChar() == '{') {
                        tPane.setText(tPane.getText() + "}");
                        tPane.setCaretPosition(tPane.getText().length() - 1);
                        new Syntax().generateSyntax(tPane);
                    } else if (e.getKeyChar() == '(') {
                        tPane.setText(tPane.getText() + ")");
                        tPane.setCaretPosition(tPane.getText().length() - 1);
                        new Syntax().generateSyntax(tPane);
                    } else if (e.getKeyChar() == '\"') {
                        tPane.setText(tPane.getText() + "\"");
                        tPane.setCaretPosition(tPane.getText().length() - 1);
                        new Syntax().generateSyntax(tPane);
                    }
                }
            });
            tPane.setBackground(new Color(38, 38, 38));
            tPane.setForeground(Color.WHITE);
            pack();
            setSize(900, 600);
            scrollPane.add(tPane);
            addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    tPane.setSize(getSize());
                }

                @Override
                public void componentMoved(ComponentEvent e) {

                }

                @Override
                public void componentShown(ComponentEvent e) {

                }

                @Override
                public void componentHidden(ComponentEvent e) {

                }
            });
            setJMenuBar(createMenuBar());
            setContentPane(scrollPane);
            getContentPane().setBackground(new Color(38,38,38));
            setTitle("FPL_IDE - Main.fpl");
            setVisible(true);
        });
    }

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        //JMenuItem cutItem = new JMenuItem("Cut");
        //editMenu.add(cutItem);
        JMenuItem copyItem = new JMenuItem("Copy");
        editMenu.add(copyItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        editMenu.add(pasteItem);
        return editMenu;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        //JMenuItem newItem = new JMenuItem("New");
        //fileMenu.add(newItem);
        //JMenuItem openItem = new JMenuItem("Open");
        //fileMenu.add(openItem);
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> {
            System.out.println("[FPL_IDE] [MENU_BAR_MANAGER] Save button clicked");
            Downloader downloader = new Downloader();
            String path;
            try {
                path = Downloader.getPathFolder() + "main.fpl";
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Saver saver = new Saver();
            saver.saveFile(path, tPane.getText());
        });
        fileMenu.add(saveItem);
        JMenuItem runItem = new JMenuItem("Run");
        runItem.addActionListener(e -> {
            System.out.println("[FPL_IDE] [MENU_BAR_MANAGER] Run button clicked");
            try {
                new Runner().Run();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        fileMenu.add(runItem);
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            try {
                new About();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        fileMenu.add(aboutItem);
        return fileMenu;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        return menuBar;
    }
}
