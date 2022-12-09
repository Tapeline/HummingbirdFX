package me.tapeline.hummingbird.expansions.colorschemes;

import me.tapeline.hummingbird.expansions.Orderable;
import org.fife.ui.rsyntaxtextarea.Token;

import java.awt.*;

public class AbstractColorScheme {

    public TextStyle regular = new TextStyle();
    public TextStyle error = new TextStyle(Token.ERROR_CHAR).under().bg(new Color(255, 0, 0, 30));
    public TextStyle warning = new TextStyle(Token.ERROR_IDENTIFIER).bg(new Color(255, 255, 0, 30));
    public TextStyle codeStyleWarning = new TextStyle(Token.ERROR_STRING_DOUBLE).bg(
            new Color(55, 255, 250, 30));
    public TextStyle deprecated = new TextStyle(Token.ERROR_NUMBER_FORMAT).under().bg(
            new Color(255, 25, 250, 30));

    public TextStyle comment = new TextStyle(Token.COMMENT_EOL).italic().fg(new Color(114, 115, 122));
    public TextStyle commentDoc = new TextStyle(Token.COMMENT_DOCUMENTATION).italic().bold().fg(
            new Color(114, 115, 122));
    public TextStyle keyword = new TextStyle(Token.RESERVED_WORD).bold().fg(new Color(200, 119, 50));
    public TextStyle keyword2 = new TextStyle(Token.RESERVED_WORD_2).fg(new Color(148, 89, 37));
    public TextStyle operator = new TextStyle(Token.OPERATOR).fg(new Color(148, 89, 37));
    public TextStyle string = new TextStyle(Token.LITERAL_STRING_DOUBLE_QUOTE).fg(new Color(82, 128, 89));
    public TextStyle number = new TextStyle(Token.LITERAL_NUMBER_DECIMAL_INT).fg(new Color(76, 131, 184));
    public TextStyle metadata = new TextStyle(Token.ANNOTATION).fg(new Color(183, 180, 42));
    public TextStyle variable = new TextStyle(Token.VARIABLE).fg(new Color(152, 118, 170));

}
