package me.tapeline.hummingbird.expansions.highlighter;

import java.util.List;
import javax.swing.text.Segment;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;

public abstract class AbstractSyntaxHighlighter {

    public abstract AbstractFileType getApplicableFileType();

    public abstract List<Highlight> highlight(Segment var1);

}
