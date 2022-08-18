package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.util.AutoCompletion.Autocompleter;
import com.github.PastaLaPate.FPL_IDE.fpl.Runner;
import com.github.PastaLaPate.FPL_IDE.fpl.Saver;
import com.github.PastaLaPate.FPL_IDE.util.logger.Level;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;
import com.github.PastaLaPate.FPL_IDE.util.panels.About;
import com.github.PastaLaPate.FPL_IDE.util.panels.Files;
import com.github.PastaLaPate.FPL_IDE.util.syntax.Syntax;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;

public class MainPanel extends JFrame{

    private JTextPane tPane;
    private JFrame f;
    private String currentFileName = "main.fpl";

    private Files files;

    public void init() {
        SwingUtilities.invokeLater(() -> {
            //BASE
            ScrollPane scrollPane = new ScrollPane();
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            tPane = new JTextPane();
            tPane.setEditable(true);
            f = this;

            //SYNTAX AND AUTOCOMPLETER
            Autocompleter autocompleter = new Autocompleter();
            tPane.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                //AUTOCOMPLETER
                @Override
                public void keyPressed(KeyEvent e) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < tPane.getCaretPosition(); i++) {
                        stringBuilder.append(tPane.getText().charAt(i));
                    }
                    String string = stringBuilder.toString();
                    String delim = "[ ]+";
                    String[] result = string.split(delim);
                    autocompleter.setListener(completion -> {
                        String lastResult = result[result.length - 1];
                        try {
                            tPane.getDocument().remove(string.length() - lastResult.length(), lastResult.length() + 1);
                            tPane.getDocument().insertString(tPane.getCaretPosition(), completion, null);
                        } catch (BadLocationException ex) {
                            Logger.log(ex);
                        }
                        new Syntax().generateSyntax(tPane);
                    });
                    if (result.length != 0) {
                        try {
                            Rectangle2D rectangle = ((JTextPane)(e.getSource())).modelToView2D(tPane.getCaretPosition());
                            autocompleter.autocomplete(result[result.length-1], (int) rectangle.getX(), 15 + (int) rectangle.getY(), (Component) e.getSource());
                        } catch (BadLocationException ex) {
                            throw new RuntimeException(ex);
                        }
                            //autocompleter.autocomplete(f, result[result.length-1], x, y);
                        //} catch (BadLocationException ex) {
                        //    ex.printStackTrace();
                       // }
                    }
                    new Syntax().generateSyntax(tPane);
                }

                //ADD CHAR
                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyChar() == '{') {
                        insertChar("}");
                        new Syntax().generateSyntax(tPane);
                    } else if (e.getKeyChar() == '(') {
                        insertChar(")");
                        new Syntax().generateSyntax(tPane);
                    } else if (e.getKeyChar() == '\"') {
                        insertChar("\"");
                        new Syntax().generateSyntax(tPane);
                    }
                }
            });
            //STYLE
            tPane.setBackground(new Color(38, 38, 38));
            tPane.setForeground(Color.WHITE);
            pack();
            setSize(900, 600);
            scrollPane.add(tPane);
            tPane.setCaretColor(Color.WHITE);


            //LOAD FILE
            loadFile("main.fpl");
            new Syntax().generateSyntax(tPane);
            addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    int width = getSize().width;
                    int height = getSize().height;
                    tPane.setSize(width, height);
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

            files = new Files();
            ScrollPane sPane = files.init(this);
            files.setListener(this::loadFile);
            files.addFile("main.fpl");
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sPane, scrollPane);
            splitPane.setOneTouchExpandable(true);
            splitPane.setDividerLocation(50);

            sPane.setMinimumSize(new Dimension(100, 25));
            sPane.setMaximumSize(new Dimension(200, 200));

            scrollPane.setMaximumSize(new Dimension(200, Integer.MAX_VALUE));

            setJMenuBar(createMenuBar());
            setContentPane(splitPane);
            getContentPane().setBackground(new Color(38,38,38));
            setTitle("FPL_IDE - Main.fpl");
            setVisible(true);
        });
    }

    private JMenu createEditMenu() {
        JMenu editMenu = new JMenu("Edit");
        JMenuItem copyItem = new JMenuItem("Copy");
        editMenu.add(copyItem);
        JMenuItem pasteItem = new JMenuItem("Paste");
        editMenu.add(pasteItem);
        return editMenu;
    }

    private JMenu createFileMenu() {
        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> {
            Logger.log("Save button clicked", this.getClass(), Level.INFO);
            saveFile();
        });
        fileMenu.add(saveItem);
        JMenuItem saveAs = new JMenuItem("Save as");
        saveAs.addActionListener(e -> {
            Logger.log("Save as button clicked", this.getClass(), Level.INFO);
            saveAsFile();
        });
        fileMenu.add(saveAs);
        JMenuItem loadItem = new JMenuItem("load");
        loadItem.addActionListener(e -> {
            //SAVE FILE
            Logger.log("Load button clicked", this.getClass(), Level.INFO);
            loadFile();
        });
        fileMenu.add(loadItem);
        JMenuItem runItem = new JMenuItem("Run");
        runItem.addActionListener(e -> {
            Logger.log("Run button clicked", this.getClass(), Level.INFO);
            try {
                new Runner().Run();
            } catch (IOException ex) {
                Logger.log(ex);
            }
        });
        fileMenu.add(runItem);
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            try {
                new About();
            } catch (IOException ex) {
                Logger.log(ex);
            }
        });
        fileMenu.add(aboutItem);
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

    private void loadFile(String fileName) {
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

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        return menuBar;
    }

    private void insertChar(String chara) {
        try {
            tPane.getDocument().insertString(tPane.getCaretPosition(),chara, null);
        } catch (BadLocationException ex) {
            Logger.log(ex);
        }
        tPane.setCaretPosition(tPane.getCaretPosition() - 1);
    }
}
