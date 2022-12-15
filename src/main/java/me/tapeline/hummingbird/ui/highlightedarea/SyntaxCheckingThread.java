package me.tapeline.hummingbird.ui.highlightedarea;

import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.highlighter.Highlight;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.syntaxchecker.SyntaxTip;

import java.util.ArrayList;
import java.util.List;

public class SyntaxCheckingThread extends Thread {

    private AbstractSyntaxChecker checker;
    private List<SyntaxTip> result = new ArrayList<>();
    private String text;

    public SyntaxCheckingThread(AbstractSyntaxChecker highlighter, String text) {
        this.checker = highlighter;
        this.text = text;
        this.start();
    }

    @Override
    public void run() {
        result = checker.check(text);
    }

    public AbstractSyntaxChecker getChecker() {
        return checker;
    }

    public List<SyntaxTip> getResult() {
        return result;
    }

    public String getText() {
        return text;
    }

}
