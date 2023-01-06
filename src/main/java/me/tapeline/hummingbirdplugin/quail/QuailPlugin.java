package me.tapeline.hummingbirdplugin.quail;

import me.tapeline.hummingbird.expansions.Plugin;
import me.tapeline.hummingbird.expansions.autocompletion.AbstractCodeAutocompleter;
import me.tapeline.hummingbird.expansions.customactions.AbstractPluginAction;
import me.tapeline.hummingbird.expansions.customactions.AbstractPluginShortcut;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.highlighter.AbstractSyntaxHighlighter;
import me.tapeline.hummingbird.expansions.projecttype.AbstractProjectGenerator;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.themes.AbstractTheme;

import java.util.Arrays;
import java.util.List;

public class QuailPlugin extends Plugin {
    @Override
    public String name() {
        return "Quail";
    }

    @Override
    public String author() {
        return "Tapeline";
    }

    @Override
    public float version() {
        return 0;
    }

    @Override
    public List<AbstractProjectGenerator> providedProjectGenerators() {
        return null;
    }

    @Override
    public List<AbstractSyntaxHighlighter> providedSyntaxes() {
        return Arrays.asList(
                new QuailSyntaxHighlighter()
        );
    }

    @Override
    public List<AbstractSyntaxChecker> providedSyntaxCheckers() {
        return Arrays.asList(
                new QuailSyntaxChecker()
        );
    }

    @Override
    public List<AbstractFileType> providedFileTypes() {
        return Arrays.asList(
            new QuailFileType()
        );
    }

    @Override
    public List<AbstractTheme> providedThemes() {
        return null;
    }

    @Override
    public List<AbstractPluginAction> providedActions() {
        return null;
    }

    @Override
    public List<AbstractPluginShortcut> providedShortcuts() {
        return null;
    }

    @Override
    public List<AbstractCodeAutocompleter> providedAutocompleters() {
        return null;
    }
}
