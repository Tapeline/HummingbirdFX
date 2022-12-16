package me.tapeline.hummingbird.expansions.themes;

import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;
import me.tapeline.hummingbird.resources.Stylesheet;
import me.tapeline.hummingbird.resources.StylesheetManager;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTheme {

    public abstract String name();

    public abstract AbstractColorScheme scheme();

    public abstract AbstractColorSet colors();

    @Nullable
    public abstract String cssPath();

    @Nullable
    public String rawCss() { return null; }

    public void onApply() {
        if (cssPath() != null)
            StylesheetManager.stylesheets.add(new Stylesheet(Stylesheet.Type.FILE, cssPath()));
        if (rawCss() != null)
            StylesheetManager.stylesheets.add(new Stylesheet(Stylesheet.Type.RAW, rawCss()));
        StylesheetManager.buildStylesheet(App.stylesheetFile);
    }

}