package me.tapeline.hummingbird.expansions.highlighter;

import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;

import javax.swing.*;
import javax.swing.text.Segment;
import java.util.List;

public abstract class AbstractSyntaxHighlighter {

    public abstract AbstractFileType getApplicableFileType();

    public abstract List<Highlight> highlight(Segment segment);

}
