package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.fpl.Runner;
import com.github.PastaLaPate.FPL_IDE.fpl.Saver;
import com.github.PastaLaPate.FPL_IDE.syntax.Syntax;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainPanel {

    final String[] text = {""};

    public MainPanel() {

    }

    public void init() {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                // create jeditorpane
                JEditorPane jEditorPane = new JEditorPane();

                // make it read-only
                jEditorPane.setEditable(true);

                // create a scrollpane; modify its attributes as desired
                JScrollPane scrollPane = new JScrollPane(jEditorPane);

                StyleSheet styleSheet = new StyleSheet();

                styleSheet.addRule("body {background-color:black;}");
                styleSheet.addRule("span {color: white;}");

                // add an html editor kit
                HTMLEditorKit kit = new HTMLEditorKit();
                kit.setStyleSheet(styleSheet);
                jEditorPane.setEditorKit(kit);
                // create a document, set it on the jeditorpane, then add the html
                Document doc = kit.createDefaultDocument();
                jEditorPane.setDocument(doc);
                try {
                    jEditorPane.setText(new Syntax().getFinalString(new Saver().getFile(new Downloader().getPathFolder() + "main.fpl")));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                jEditorPane.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                        jEditorPane.setText("");
                        System.out.println(e.getKeyCode());
                        if (e.getKeyCode() == 8) {
                            text[0] = removeLastChar(text[0]);
                        } else if (e.getKeyCode() == 10) {
                            text[0] = text[0] + "<br>";
                        } else {
                            text[0] = text[0] + e.getKeyChar();
                        }
                        String finalS = new Syntax().getFinalString(text[0]);
                        jEditorPane.setText(finalS);
                    }
                });

                // now add it all to a frame
                JFrame j = new JFrame("FPL IDE");
                j.getContentPane().add(scrollPane, BorderLayout.CENTER);

                // make it easy to close the application
                j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // display the frame
                j.setSize(new Dimension(300,200));
                j.setJMenuBar(createMenuBar());

                // pack it, if you prefer
                //j.pack();

                // center the jframe, then make it visible
                j.setLocationRelativeTo(null);
                j.setVisible(true);
            }
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
            String path = downloader.getPathFolder() + "main.fpl";
            Saver saver = new Saver();
            saver.saveFile(path, text[0]);
        });
        fileMenu.add(saveItem);
        JMenuItem runItem = new JMenuItem("Run");
        runItem.addActionListener(e -> {
            System.out.println("[FPL_IDE] [MENU_BAR_MANAGER] Run button clicked");
            new Runner().Run();
        });
        fileMenu.add(runItem);
        return fileMenu;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createEditMenu());
        return menuBar;
    }

    private String removeLastChar(String s)
    {
//returns the string after removing the last character
        return s.substring(0, s.length() - 1);
    }
}
