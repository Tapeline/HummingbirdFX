package me.tapeline.hummingbird.expansions.syntaxchecker;

import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.colorschemes.TextStyle;
import me.tapeline.hummingbird.expansions.highlighter.Bounds;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SyntaxTip {

    public enum TipType {
        ERROR,
        WARNING,
        CODE_STYLE,
        DEPRECATION,
        TODO
    }

    public String tip;
    public Bounds bounds;
    public TipType type;
    public File file = null;
    public List<AutoFixAction> actionList = new ArrayList<>();

    public SyntaxTip(Bounds bounds, String tip, TipType type) {
        this.bounds = bounds;
        this.type = type;
        this.tip = tip;
    }

    public void addFix(AutoFixAction action) {
        actionList.add(action);
    }

    public List<AutoFixAction> getFixes() {
        return actionList;
    }

    public TextStyle style() {
        switch (type) {
            case ERROR:
                return Registry.currentTheme.scheme().error;
            case WARNING:
                return Registry.currentTheme.scheme().warning;
            case DEPRECATION:
                return Registry.currentTheme.scheme().deprecated;
            case CODE_STYLE:
                return Registry.currentTheme.scheme().codeStyleWarning;
        }
        return Registry.currentTheme.scheme().warning;
    }

    public String toString() {
        return (type == SyntaxTip.TipType.ERROR? "X  " :
                type == SyntaxTip.TipType.WARNING? "⚠  " :
                        type == SyntaxTip.TipType.CODE_STYLE? "⚠✎ " :
                                type == SyntaxTip.TipType.DEPRECATION? "⚠\uD83D\uDD6E " :
                                        "⚠ "
        ) + tip;
    }
}
