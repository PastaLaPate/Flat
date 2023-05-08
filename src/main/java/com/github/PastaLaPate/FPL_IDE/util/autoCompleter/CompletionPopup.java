package com.github.PastaLaPate.FPL_IDE.util.autoCompleter;

import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import org.fxmisc.richtext.CodeArea;

import java.util.List;

public class CompletionPopup {

    private final CodeArea area;

    private final ContextMenu menu =  new ContextMenu();

    public CompletionPopup(CodeArea area) {
        this.area = area;
        menu.setStyle("-fx-background-color: #272727FF; -fx-text-fill: white; -fx-border-width: 2px; -fx-border-color: #2F2F2FFF;");
        area.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                menu.hide();
            }
        });
    }

    public void showPopup(List<Completion> completions) {
        menu.getItems().clear();

        completions.forEach(completion -> {
            MenuItem item = new MenuItem(completion.completion);
            item.setOnAction(e -> {
                String[] words = area.getText().split(" ");
                String lastWord = words[words.length - 1];
                area.appendText(completion.completion.substring(lastWord.length()));
            });
            menu.getItems().add(item);
        });

        Bounds caretBounds = area.getCaretBounds().orElse(null);
        if (caretBounds != null) {
            double x = caretBounds.getMinX();
            double y = caretBounds.getMinY() + caretBounds.getHeight();
            menu.show(area, x, y);
        }
    }
}
