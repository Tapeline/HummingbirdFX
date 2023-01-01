package me.tapeline.hummingbird.ui.highlightedarea;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.autocompletion.AbstractCodeAutocompleter;
import me.tapeline.hummingbird.expansions.colorschemes.TextStyle;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.highlighter.Highlight;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.syntaxchecker.SyntaxTip;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.ui.editor.tabs.codeeditor.CodeEditorTab;
import me.tapeline.hummingbird.utils.Convert;
import org.apache.commons.lang3.StringUtils;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.PlainTextChange;
import org.fxmisc.wellbehaved.event.EventHandlerHelper;
import org.fxmisc.wellbehaved.event.EventPattern;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;
import org.reactfx.Subscription;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HighlightedArea extends InlineCssTextArea {

    public List<AbstractSyntaxHighlighter> highlighters = new ArrayList<>();
    public List<AbstractSyntaxChecker> checkers = new ArrayList<>();
    public List<AbstractCodeAutocompleter> autocompleters = new ArrayList<>();
    private Subscription syntaxLintUpdater;
    public CodeEditorTab tab;
    private ContextMenu autocompletePopup;

    public HighlightedArea(CodeEditorTab tab) {
        super();
        this.tab = tab;
        Subscription cleanupWhenDone = multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(this::computeAndApplyHighlighting);
        setParagraphGraphicFactory(LineNumberFactory.get(this));

        setStyle("-fx-font-size: " + App.cfg.fontSize + "; -fx-font-family: " + App.cfg.fontName + "; " +
                "-fx-background-color: " + Convert.hexColor(Registry.getCurrentTheme().colors().backgroundText) +
                ";");

        EventHandler<? super KeyEvent> tabHandler = EventHandlerHelper
                .on(EventPattern.keyPressed(KeyCode.TAB)).act(event -> {
                    replaceText(getCaretPosition() - 1, getCaretPosition(), StringUtils.repeat(" ",
                            App.cfg.tabSizeInSpaces));
                })
                .create();
        EventHandlerHelper.install(onKeyPressedProperty(), tabHandler);

        Nodes.addInputMap(this, InputMap.sequence(
                InputMap.consume(EventPattern.keyPressed(KeyCode.SPACE, KeyCombination.CONTROL_DOWN),
                        e -> autoCompleteRequest())
        ));

        focusedProperty().addListener(observable -> {
            autocompletePopup.hide();
        });
    }

    public void cleanup() {
        syntaxLintUpdater.unsubscribe();
    }

    public void computeAndApplyHighlighting(List<PlainTextChange> changes) {
        if (tab.isEditable)
            FS.writeFile(tab.file, getText());

        List<SyntaxHighlightingThread> syntaxHighlightingThreads = new ArrayList<>();
        List<SyntaxCheckingThread> syntaxCheckingThreads = new ArrayList<>();
        String text = getText();
        for (AbstractSyntaxHighlighter highlighter : highlighters)
            syntaxHighlightingThreads.add(new SyntaxHighlightingThread(
                    highlighter, text
            ));
        for (AbstractSyntaxChecker checker : checkers)
            syntaxCheckingThreads.add(new SyntaxCheckingThread(
                    checker, text
            ));

        while (true) {
            boolean anyAlive = false;
            for (SyntaxHighlightingThread th : syntaxHighlightingThreads)
                if (th.isAlive()) {
                    anyAlive = true;
                    break;
                }
            for (SyntaxCheckingThread th : syntaxCheckingThreads)
                if (th.isAlive()) {
                    anyAlive = true;
                    break;
                }
            if (!anyAlive) break;
        }

        clearStyle(0, getLength());
        //setStyle(0, 0, Registry.getCurrentTheme().scheme().regular.toCSSStyles());
        for (SyntaxHighlightingThread th : syntaxHighlightingThreads)
            for (Highlight highlight : th.getResult())
                setStyle(highlight.bounds.left, highlight.bounds.right, highlight.style.family(App.cfg.fontName)
                        .toCSSStyles());
        for (SyntaxCheckingThread th : syntaxCheckingThreads)
            for (SyntaxTip tip : th.getResult())
                setStyle(tip.bounds.left, tip.bounds.right, tip.style().family(App.cfg.fontName).toCSSStyles());
    }

    public void autoCompleteRequest() {

    }

}
