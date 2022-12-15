package me.tapeline.hummingbird.ui.highlightedarea;

import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.highlighter.Highlight;

import java.util.ArrayList;
import java.util.List;

public class SyntaxHighlightingThread extends Thread {

    private AbstractSyntaxHighlighter highlighter;
    private List<Highlight> result = new ArrayList<>();
    private String text;

    public SyntaxHighlightingThread(AbstractSyntaxHighlighter highlighter, String text) {
        this.highlighter = highlighter;
        this.text = text;
        this.start();
    }

    @Override
    public void run() {
        result = highlighter.highlight(text);
    }

    public AbstractSyntaxHighlighter getHighlighter() {
        return highlighter;
    }

    public List<Highlight> getResult() {
        return result;
    }

    public String getText() {
        return text;
    }

}
