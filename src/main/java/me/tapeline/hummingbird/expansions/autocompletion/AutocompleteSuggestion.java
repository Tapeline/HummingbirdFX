package me.tapeline.hummingbird.expansions.autocompletion;

import me.tapeline.hummingbird.ui.highlightedarea.HighlightedArea;

public abstract class AutocompleteSuggestion {

    public abstract void applyOnTab(HighlightedArea area);
    public abstract void applyOnEnter(HighlightedArea area);

}
