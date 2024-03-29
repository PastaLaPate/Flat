package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes;

import com.github.PastaLaPate.FPL_IDE.ui.Constants;
import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.ui.panels.Partials.TopBar;
import com.github.PastaLaPate.FPL_IDE.util.Downloader;
import com.github.PastaLaPate.FPL_IDE.util.Logger.Logger;
import com.github.PastaLaPate.FPL_IDE.util.SyncPipe;
import javafx.geometry.Insets;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Console extends Panel {

    static PrintWriter stdin;
    private List<String> history;
    private int numInHistory = 1;

    public Console(PanelManager panelManager) throws IOException {
        super(panelManager);
        this.panelName = "Console";
    }

    public static TopBar.RunHandler getRunHandler() {
        return () -> {
            try {
                stdin.println("French_Programming_Language " + Downloader.getPathFolder() + "main.fpl");
                stdin.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Override
    public void initComponents() {
        super.initComponents();
        Process p;
        history = new ArrayList<>();
        history.add("");
        TextArea textArea = new TextArea();
        try {
            String[] command =
                    {
                            "cmd",
                    };
            p = Runtime.getRuntime().exec(command);


            OutputStream out = new OutputStream() {
                String out;
                int off;
                @Override
                public void write(int b) {
                    out = out + "\n" + b;
                }

                @Override
                public void write(byte @NotNull [] b, int off, int len) throws IOException {
                    super.write(b, off, len);
                    b = Arrays.copyOfRange(b, this.off, len);
                    this.off += off;
                    textArea.insertText(textArea.getLength(),  new String(b));
                }
            };

            new Thread(new SyncPipe(p.getErrorStream(), out)).start();
            new Thread(new SyncPipe(p.getInputStream(), out)).start();
            stdin = new PrintWriter(p.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        textArea.setEditable(false);
        textArea.setStyle("-fx-text-fill: white");
        Color color = Constants.ELEMENT1;
        double red = color.getRed()*255;
        double blue = color.getBlue()*255;
        double green = color.getGreen()*255;
        String rgbColor = "rgb(" + red + ", " + blue + " , " + green + ")";
        textArea.setStyle(
                "-fx-control-inner-background: " + rgbColor + ";" );
        textArea.setFont(new Font(Constants.JetBrainsMono.getFamily(), 10));

        TextField commandField = new TextField();
        commandField.setBackground(new Background(new BackgroundFill(Constants.ELEMENT1,CornerRadii.EMPTY, Insets.EMPTY)));
        commandField.setMaxHeight(50.0D);
        commandField.setStyle("-fx-text-fill: white");
        commandField.setFont(Constants.JetBrainsMono);
        commandField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                stdin.println(commandField.getText());
                stdin.flush();
                if (!Objects.equals(history.get(history.size() - 1), commandField.getText())) {
                    history.add(commandField.getText());
                }
                commandField.setText("");
            } else if (event.getCode() == KeyCode.UP) {
                Logger.log("detected");
                if (numInHistory < history.size()) {
                    commandField.setText(history.get(history.size() - numInHistory));
                    commandField.positionCaret(history.get(history.size() - numInHistory).length());
                    numInHistory++;
                }
            }
            switch (event.getCode()) {
                case ENTER:
                    stdin.println(commandField.getText());
                    stdin.flush();
                    if (history.size() > 0 && !Objects.equals(history.get(history.size() - 1), commandField.getText())) {
                        history.add(commandField.getText());
                    }
                    commandField.setText("");
                    break;
                case UP:
                    if (numInHistory >= 0 && numInHistory < history.size()) {
                        commandField.setText(history.get(history.size() - numInHistory));
                        commandField.positionCaret(history.get(history.size() - numInHistory).length());
                        numInHistory++;
                    }
                    break;
                case DOWN:
                    if (numInHistory >= 0 && numInHistory < history.size()) {
                        commandField.setText(history.get(history.size() - numInHistory + 1));
                        commandField.positionCaret(history.get(history.size() - numInHistory + 1).length());
                        numInHistory--;
                    }
                    break;
            }
        });
        setCanTakeAllSize(textArea);
        this.layout.add(textArea, 0, 0);
        this.layout.add(commandField, 0, 1);
    }
}
