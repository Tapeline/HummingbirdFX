package me.tapeline.hummingbird.expansions.themes;

import com.formdev.flatlaf.FlatDarculaLaf;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;

import java.awt.*;

public class DarculaTheme extends AbstractTheme {
    @Override
    public String name() {
        return "Darcula";
    }

    @Override
    public AbstractColorScheme scheme() {
        AbstractColorScheme scheme = new AbstractColorScheme();
        scheme.regular.fg(new Color(165, 182, 189));
        return scheme;
    }

    @Override
    public AbstractColorSet colors() {
        return new ColorSet();
    }

    @Override
    public void onApply() {
        FlatDarculaLaf.setup();
    }

    public static class ColorSet extends AbstractColorSet {
        public ColorSet() {
            backgroundText = new Color(34, 34, 34);
            backgroundTextHighlight = new Color(50, 50, 50);

        }
    }
}
