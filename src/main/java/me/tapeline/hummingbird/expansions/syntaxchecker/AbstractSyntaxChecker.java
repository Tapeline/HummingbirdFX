package me.tapeline.hummingbird.expansions.syntaxchecker;

import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;

import java.util.List;

public abstract class AbstractSyntaxChecker {

    public abstract AbstractFileType getApplicableFileType();

    public abstract List<SyntaxTip> check(String text);

}
