package me.tapeline.hummingbird.core.themes;

import java.awt.Color;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;
import me.tapeline.hummingbird.expansions.themes.AbstractColorSet;
import me.tapeline.hummingbird.expansions.themes.AbstractTheme;

public class DarculaTheme extends AbstractTheme {

    public String name() {
        return "Darcula";
    }

    public AbstractColorScheme scheme() {
        AbstractColorScheme scheme = new AbstractColorScheme();
        scheme.regular.fg(new Color(165, 182, 189));
        return scheme;
    }

    public AbstractColorSet colors() {
        return new ColorSet();
    }

    public String cssPath() {
        return "$style/darcula.css";
    }

    public static class ColorSet extends AbstractColorSet {
        public ColorSet() {
            backgroundText = new Color(34, 34, 34);
            backgroundTextHighlight = new Color(50, 50, 50);
        }
    }

}