package me.tapeline.hummingbird.expansions.syntaxchecker;

import java.io.File;

public abstract class AutoFixAction {

    public abstract String getLabel();
    public abstract void fix(File file);

    public String toString() {
        return getLabel();
    }

}
