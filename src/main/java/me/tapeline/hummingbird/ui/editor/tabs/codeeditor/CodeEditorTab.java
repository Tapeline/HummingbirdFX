package me.tapeline.hummingbird.ui.editor.tabs.codeeditor;

import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.autocompletion.AbstractCodeAutocompleter;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.ui.editor.tabs.AbstractEditorTab;
import me.tapeline.hummingbird.ui.editor.tabs.AssignableToFileTab;
import me.tapeline.hummingbird.ui.highlightedarea.HighlightedArea;
import me.tapeline.hummingbird.view.common.Dialogs;
import me.tapeline.hummingbird.view.editor.EditorStage;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.wellbehaved.event.EventPattern;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeEditorTab extends AbstractEditorTab implements AssignableToFileTab {

    private final EditorStage editor;
    public File file;
    public HighlightedArea area;
    public VirtualizedScrollPane<HighlightedArea> scrollPane;
    public boolean isEditable;

    public CodeEditorTab(EditorStage stage, File file, String content) {
        this(stage, file, content, true);
    }

    public CodeEditorTab(EditorStage stage, File file, String content, boolean isEditable) {
        this.editor = stage;
        this.file = file;
        this.isEditable = isEditable;
        area = new HighlightedArea(this);
        area.focusedProperty().addListener((observable, oldValue, newValue) -> {
            reloadRequest();
        });

        AbstractFileType fileType = FS.getTypeForFile(file);
        if (fileType != null) {
            for (AbstractSyntaxHighlighter h : Registry.syntaxHighlighters)
                if (h.getApplicableFileType().appliesToFile(file))
                    area.highlighters.add(h);
            for (AbstractSyntaxChecker c : Registry.syntaxCheckers)
                if (c.getApplicableFileType().appliesToFile(file))
                    area.checkers.add(c);
            for (AbstractCodeAutocompleter c : Registry.codeAutocompleters)
                if (c.getApplicableFileType().appliesToFile(file))
                    area.autocompleters.add(c);
        }
        area.replaceText(0, 0, content);
        area.setEditable(isEditable);
        //area.setContextMenu(new DefaultContextMenu());

        Pattern whiteSpace = Pattern.compile( "^\\s+" );
        area.addEventHandler(KeyEvent.KEY_PRESSED, KE -> {
            // TODO: fix
            /*if (KE.getCode() == KeyCode.ENTER) {
                int caretPosition = area.getCaretPosition();
                int currentParagraph = area.getCurrentParagraph();
                if (currentParagraph > 0) {
                    Matcher m0 = whiteSpace.matcher(
                            area.getParagraph(currentParagraph - 1).getSegments().get(0)
                    );
                    if (m0.find()) Platform.runLater(
                            () -> area.insertText(caretPosition, m0.group())
                    );
                }
            }*/
        });

        setOnCloseRequest((e) -> {
            if (isEditable)
                FS.writeFile(file, area.getText());
        });

        scrollPane = new VirtualizedScrollPane<>(area, ScrollPane.ScrollBarPolicy.AS_NEEDED,
                ScrollPane.ScrollBarPolicy.ALWAYS);

        Nodes.addInputMap(area, InputMap.sequence(
                InputMap.consume(EventPattern.keyPressed(KeyCode.S, KeyCombination.CONTROL_DOWN),
                        e -> editor.save())
        ));

        setContent(scrollPane);
    }

    @Override
    public void save(EditorStage stage) throws Exception {
        if (isEditable)
            FS.writeFile(file, area.getText());
    }

    @Override
    public File getFile() {
        return file;
    }

    public void reloadRequest() {
        String newContent = FS.readFile(file);
        if (!newContent.equals(area.getText())) {
            if (Dialogs.confirm("Update detected", "Update detected",
                    "Do you want to keep all remote changes to file?").orElse(ButtonType.CANCEL)
                    .equals(ButtonType.OK)) {
                area.replaceText(newContent);
            } else {
                FS.writeFile(file, area.getText());
            }
        }
    }
}
