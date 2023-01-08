package me.tapeline.hummingbird.expansions.autocompletion;

import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.ui.highlightedarea.HighlightedArea;

import java.io.File;
import java.util.List;

public abstract class AbstractCodeAutocompleter {

    public abstract AbstractFileType getApplicableFileType();

    public abstract List<AutocompleteSuggestion> autocomplete(Project project, HighlightedArea area);

}
