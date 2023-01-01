package me.tapeline.hummingbird.expansions.autocompletion;

import me.tapeline.hummingbird.expansions.highlighter.Bounds;
import me.tapeline.hummingbird.ui.highlightedarea.HighlightedArea;

public class SimpleSuggestion extends AutocompleteSuggestion {

    private Bounds bounds;
    private String substitution;

    public SimpleSuggestion(Bounds bounds, String substitution) {
        this.bounds = bounds;
        this.substitution = substitution;
    }

    @Override
    public void applyOnTab(HighlightedArea area) {
        area.replaceText(bounds.left, bounds.right, substitution);
    }

    @Override
    public void applyOnEnter(HighlightedArea area) {
        area.insertText(bounds.right, substitution);
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public String getSubstitution() {
        return substitution;
    }

    public void setSubstitution(String substitution) {
        this.substitution = substitution;
    }

}
