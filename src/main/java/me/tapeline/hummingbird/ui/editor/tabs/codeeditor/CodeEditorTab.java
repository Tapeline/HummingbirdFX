package me.tapeline.hummingbird.ui.editor.tabs.codeeditor;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.ui.editor.tabs.AbstractEditorTab;
import me.tapeline.hummingbird.ui.highlightedarea.HighlightedArea;
import me.tapeline.hummingbird.view.editor.EditorStage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.GenericStyledArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeEditorTab extends AbstractEditorTab {

    public File file;
    public HighlightedArea area;

    public CodeEditorTab(File file, String content) {
        this.file = file;
        area = new HighlightedArea();
        AbstractFileType fileType = FS.getTypeForFile(file);
        if (fileType != null) {
            for (AbstractSyntaxHighlighter h : Registry.syntaxHighlighters)
                if (h.getApplicableFileType().appliesToFile(file))
                    area.highlighters.add(h);
            for (AbstractSyntaxChecker c : Registry.syntaxCheckers)
                if (c.getApplicableFileType().appliesToFile(file))
                    area.checkers.add(c);
        }
        area.replaceText(0, 0, content);
        //area.setContextMenu(new DefaultContextMenu());

        Pattern whiteSpace = Pattern.compile( "^\\s+" );
        area.addEventHandler(KeyEvent.KEY_PRESSED, KE -> {
            if ( KE.getCode() == KeyCode.ENTER ) {
                int caretPosition = area.getCaretPosition();
                int currentParagraph = area.getCurrentParagraph();
                Matcher m0 = whiteSpace.matcher(
                        area.getParagraph( currentParagraph-1 ).getSegments().get( 0 )
                );
                if (m0.find()) Platform.runLater(
                        () -> area.insertText(caretPosition, m0.group())
                );
            }
        });

        setOnCloseRequest((e) -> {
            FS.writeFile(file, area.getText());
        });

        setContent(area);
    }

    @Override
    public void save(EditorStage stage) throws Exception {
        FS.writeFile(file, area.getText());
    }
}
