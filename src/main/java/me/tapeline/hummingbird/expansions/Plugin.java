package me.tapeline.hummingbird.expansions;

import java.util.List;

import me.tapeline.hummingbird.expansions.autocompletion.AbstractCodeAutocompleter;
import me.tapeline.hummingbird.expansions.customactions.AbstractPluginAction;
import me.tapeline.hummingbird.expansions.customactions.AbstractPluginShortcut;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.projecttype.AbstractProjectType;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.themes.AbstractTheme;

public abstract class Plugin {

    public abstract String name();

    public abstract String author();

    public abstract float version();

    public abstract List<AbstractProjectType> providedProjectTypes();

    public abstract List<AbstractSyntaxHighlighter> providedSyntaxes();

    public abstract List<AbstractSyntaxChecker> providedSyntaxCheckers();

    public abstract List<AbstractFileType> providedFileTypes();

    public abstract List<AbstractTheme> providedThemes();

    public abstract List<AbstractPluginAction> providedActions();

    public abstract List<AbstractPluginShortcut> providedShortcuts();

    public abstract List<AbstractCodeAutocompleter> providedAutocompleters();

    public void onEnable() {}

}
