package me.tapeline.hummingbird.expansions.themes;

import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTheme {

    public abstract String name();

    public abstract AbstractColorScheme scheme();

    public abstract AbstractColorSet colors();

    @Nullable
    public abstract String cssPath();

    public void onApply() {}

}