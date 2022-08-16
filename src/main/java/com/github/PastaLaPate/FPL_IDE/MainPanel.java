package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.fpl.Runner;
import com.github.PastaLaPate.FPL_IDE.fpl.Saver;
import com.github.PastaLaPate.FPL_IDE.syntax.Syntax;
import com.github.PastaLaPate.FPL_IDE.syntax.Word;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainPanel extends JFrame{

    private JTextPane tPane;

    public void init() {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                setLocationRelativeTo(null);
                EmptyBorder eb = new EmptyBorder(new Insets(10, 10, 10, 10));
                tPane = new JTextPane();
                tPane.setEditable(true);
                tPane.setSize(300, 200);
                tPane.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {

                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                        new Syntax().generateSyntax(tPane);
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });

                pack();
                setSize(300, 200);
                add(tPane);
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
                setVisible(true);
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
            saver.saveFile(path, tPane.getText());
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
}
