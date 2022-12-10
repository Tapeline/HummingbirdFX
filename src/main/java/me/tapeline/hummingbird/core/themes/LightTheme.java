package me.tapeline.hummingbird.core.themes;

import java.awt.Color;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;
import me.tapeline.hummingbird.expansions.themes.AbstractColorSet;
import me.tapeline.hummingbird.expansions.themes.AbstractTheme;

public class LightTheme extends AbstractTheme {

    public String name() {
        return "Light";
    }

    public AbstractColorScheme scheme() {
        AbstractColorScheme scheme = new AbstractColorScheme();
        scheme.regular.fg(new Color(43, 43, 43));
        return scheme;
    }

    public AbstractColorSet colors() {
        return new ColorSet();
    }

    public String cssPath() {
        return null;
    }

    public static class ColorSet extends AbstractColorSet {
        public ColorSet() {
            backgroundText = new Color(215, 215, 215);
            backgroundTextHighlight = new Color(199, 199, 199);
        }
    }

}
