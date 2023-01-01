package me.tapeline.hummingbird.expansions.autocompletion;

import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.ui.highlightedarea.HighlightedArea;

import java.io.File;
import java.util.List;

public abstract class AbstractCodeAutocompleter {

    public abstract boolean appliesToFile(File file);

    public abstract List<AutocompleteSuggestion> autocomplete(HighlightedArea area);

}
