package com.github.PastaLaPate.FPL_IDE.ui.panels.Pages.includes;

import com.github.PastaLaPate.FPL_IDE.ui.Constants;
import com.github.PastaLaPate.FPL_IDE.ui.Panel;
import com.github.PastaLaPate.FPL_IDE.ui.PanelManager;
import com.github.PastaLaPate.FPL_IDE.util.Saver;
import com.github.PastaLaPate.FPL_IDE.util.Syntax.SyntaxHighLighter;
import com.github.PastaLaPate.FPL_IDE.util.autoCompleter.AutoCompleter;
import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Editor extends Panel {

    private final SyntaxHighLighter syntaxHighLighter = new SyntaxHighLighter();
    private final File file;
    private AutoCompleter completer;

    CodeArea area;

    public Editor(PanelManager panelManager, File file) {
        super(panelManager);
        this.panelName = file.getName();
        this.file = file;
        Saver saver = new Saver();
        try {
            area.replaceText(saver.getFile(file.getPath()));
            Platform.runLater(this::highlight);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initComponents() {
        area = new CodeArea();
        completer = new AutoCompleter(area);
        IntFunction<Node> factory = LineNumberFactory.get(area);
        area.setParagraphGraphicFactory(factory);
        Color color = Constants.BACKGROUND;
        setCanTakeAllSize(area);
        AtomicReference<String> previousChar = new AtomicReference<>();
        area.setOnKeyTyped(event -> {
            if (Objects.equals(event.getCharacter(), "{")) {
                area.insertText(area.getCaretPosition(), "}");
                area.moveTo(area.getCaretPosition() - 1);
            } else if (Objects.equals(event.getCharacter(), "(")) {
                area.insertText(area.getCaretPosition(), ")");
                area.moveTo(area.getCaretPosition() - 1);
            }
            previousChar.set(event.getCharacter());
            Bounds caretBounds = area.getCaretBounds().orElse(null);
            if (caretBounds != null) {
                double x = caretBounds.getMinX();
                double y = caretBounds.getMinY() + caretBounds.getHeight();
                new ContextMenu(new MenuItem("test"), new MenuItem("test2")).show(area, x, y);
            }
        });

        final Pattern whiteSpace = Pattern.compile( "^\\s+" );
        area.addEventHandler( KeyEvent.KEY_PRESSED, KE ->
        {
            if ( KE.getCode() == KeyCode.ENTER ) {
                int caretPosition = area.getCaretPosition();
                int currentParagraph = area.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher( area.getParagraph( currentParagraph-1 ).getSegments().get( 0 ) );
                if ( m0.find() ) Platform.runLater( () -> area.insertText( caretPosition, m0.group() ) );
            }
        });

        Platform.runLater(this::highlight);
        area.getStyleClass().add("codeArea");
        area.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        layout.add(area, 0, 0);
    }

    public String getText() {
        return area.getText();
    }

    public File getFile() {
        return file;
    }

    private void highlight() {
        syntaxHighLighter.generateSyntax(area);
    }
}
