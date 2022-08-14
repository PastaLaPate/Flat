package com.github.PastaLaPate.FPL_IDE;

import com.github.PastaLaPate.FPL_IDE.syntax.Syntax;

import javax.swing.*;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainPanel {
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

                // add an html editor kit
                HTMLEditorKit kit = new HTMLEditorKit();
                jEditorPane.setEditorKit(kit);

                // add some styles to the html
                StyleSheet styleSheet = kit.getStyleSheet();
                styleSheet.addRule("body {color:#000; font-family:times; margin: 4px; }");
                styleSheet.addRule("h1 {color: blue;}");
                styleSheet.addRule("h2 {color: #ff0000;}");
                styleSheet.addRule("pre {font : 10px monaco; color : black; background-color : #fafafa; }");

                // create some simple html as a string
                String htmlString = "<html>\n"
                        + "<body>\n"
                        + "<h1>Welcome!</h1>\n"
                        + "<h2>This is an H2 header</h2>\n"
                        + "<p>This is some sample text</p>\n"
                        + "<p><a href=\"http://devdaily.com/blog/\">devdaily blog</a></p>\n"
                        + "</body>\n";

                // create a document, set it on the jeditorpane, then add the html
                Document doc = kit.createDefaultDocument();
                jEditorPane.setDocument(doc);
                jEditorPane.setText(htmlString);
                jEditorPane.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        String finalS = new Syntax().getFinalString(jEditorPane.getText());
                        jEditorPane.setText(finalS);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                });

                // now add it all to a frame
                JFrame j = new JFrame("HtmlEditorKit Test");
                j.getContentPane().add(scrollPane, BorderLayout.CENTER);

                // make it easy to close the application
                j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // display the frame
                j.setSize(new Dimension(300,200));

                // pack it, if you prefer
                //j.pack();

                // center the jframe, then make it visible
                j.setLocationRelativeTo(null);
                j.setVisible(true);
            }
        });
    }
}
