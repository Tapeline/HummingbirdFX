package me.tapeline.hummingbird.ui.highlightedarea;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.colorschemes.TextStyle;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.highlighter.Highlight;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.syntaxchecker.SyntaxTip;
import me.tapeline.hummingbird.utils.Convert;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.InlineCssTextArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.PlainTextChange;
import org.reactfx.Subscription;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HighlightedArea extends InlineCssTextArea {

    public List<AbstractSyntaxHighlighter> highlighters = new ArrayList<>();
    public List<AbstractSyntaxChecker> checkers = new ArrayList<>();
    private Subscription syntaxLintUpdater;

    public HighlightedArea() {
        super();
        Subscription cleanupWhenDone = multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe(this::computeAndApplyHighlighting);
        setParagraphGraphicFactory(LineNumberFactory.get(this));

        setStyle("-fx-font-size: " + App.cfg.fontSize + "; -fx-font-family: " + App.cfg.fontName + "; " +
                "-fx-background-color: " + Convert.hexColor(Registry.getCurrentTheme().colors().backgroundText) +
                ";");
    }

    public void cleanup() {
        syntaxLintUpdater.unsubscribe();
    }

    public void computeAndApplyHighlighting(List<PlainTextChange> changes) {
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

}
