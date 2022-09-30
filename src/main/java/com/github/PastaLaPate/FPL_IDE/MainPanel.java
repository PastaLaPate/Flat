package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.util.AutoCompletion.Autocompleter;
import com.github.PastaLaPate.FPL_IDE.util.Saver;
import com.github.PastaLaPate.FPL_IDE.util.downloader.Downloader;
import com.github.PastaLaPate.FPL_IDE.util.logger.Logger;
import com.github.PastaLaPate.FPL_IDE.util.panels.Files;
import com.github.PastaLaPate.FPL_IDE.util.panels.TopBar;
import com.github.PastaLaPate.FPL_IDE.util.syntax.Syntax;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class MainPanel extends JFrame{

    private JTextPane tPane;
    private JFrame f;
    private TopBar topBar;

    private static final Point point = new Point();

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
            files = new Files();
            topBar = new TopBar(f, tPane, files, this);
            try {
                tPane.setText(new Saver().getFile(Downloader.getPathFolder() + "main.fpl"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            f.setUndecorated(true);
            f.setResizable(true);
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
                            if (result.length < 2) {
                                Rectangle2D rectangle = ((JTextPane)(e.getSource())).modelToView2D(tPane.getCaretPosition());
                                autocompleter.autocomplete(result[result.length-1], (int) rectangle.getX(), 15 + (int) rectangle.getY(), (Component) e.getSource());
                            } else {
                                Rectangle2D rectangle = ((JTextPane)(e.getSource())).modelToView2D(tPane.getCaretPosition());
                                autocompleter.autocomplete(result[result.length-1], (int) rectangle.getX(), 15 + (int) rectangle.getY(), (Component) e.getSource(), result[result.length-2].toLowerCase());
                            }
                        } catch (BadLocationException ex) {
                            throw new RuntimeException(ex);
                        }
                            //autocompleter.autocomplete(f, result[result.length-1], x, y);
                        //} catch (BadLocationException ex) {
                        //    ex.printStackTrace();
                       // }
                    }
                    new Syntax().generateSyntax(tPane);
                    setTabs(tPane);
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

            setJMenuBar(topBar.createMenuBar(true));
            setContentPane(splitPane);
            getContentPane().setBackground(new Color(23, 23, 23));
            setTitle("FPL_IDE - Main.fpl");
            setVisible(true);
        });
    }

    private void insertChar(String chara) {
        try {
            tPane.getDocument().insertString(tPane.getCaretPosition(),chara, null);
        } catch (BadLocationException ex) {
            Logger.log(ex);
        }
        tPane.setCaretPosition(tPane.getCaretPosition() - 1);
    }

    private void setTabs( final JTextPane textPane)
    {
        FontMetrics fm = textPane.getFontMetrics( textPane.getFont() );
//          int charWidth = fm.charWidth( 'w' );
        int charWidth = fm.charWidth( ' ' );
        int tabWidth = charWidth * 4;
//      int tabWidth = 100;

        TabStop[] tabs = new TabStop[5];

        for (int j = 0; j < tabs.length; j++)
        {
            int tab = j + 1;
            tabs[j] = new TabStop( tab * tabWidth );
        }

        TabSet tabSet = new TabSet(tabs);
        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setTabSet(attributes, tabSet);
        int length = textPane.getDocument().getLength();
        textPane.getStyledDocument().setParagraphAttributes(0, length, attributes, false);
    }
}
