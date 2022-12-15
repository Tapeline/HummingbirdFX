package me.tapeline.hummingbird.expansions.colorschemes;

import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.utils.Convert;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class TextStyle {
    public Color foreground = null;
    public Boolean underline = null;
    public Boolean strikethrough = null;
    public Color background = null;
    public Boolean italic = null;
    public Boolean subscript = null;
    public Boolean superscript = null;
    public Boolean bold = null;
    public String fontFamily = null;
    public Integer fontSize = null;

    public TextStyle() {}

    public TextStyle(TextStyle style) {
        from(style);
    }

    public TextStyle from(TextStyle style) {
        this.background = style.background;
        this.foreground = style.foreground;
        this.fontFamily = style.fontFamily;
        return this;
    }

    public TextStyle fg(Color c) {
        this.foreground = c;
        return this;
    }

    public TextStyle bg(Color c) {
        this.background = c;
        return this;
    }

    public TextStyle family(String f) {
        this.fontFamily = f;
        return this;
    }

    public TextStyle size(Integer i) {
        this.fontSize = i;
        return this;
    }

    public TextStyle bold() {
        if (this.bold == null) {
            this.bold = true;
        }

        this.bold = !this.bold;
        return this;
    }

    public TextStyle italic() {
        if (this.italic == null) {
            this.italic = true;
        }

        this.italic = !this.italic;
        return this;
    }

    public TextStyle under() {
        if (this.underline == null) {
            this.underline = true;
        }

        this.underline = !this.underline;
        return this;
    }

    public TextStyle strike() {
        if (this.strikethrough == null) {
            this.strikethrough = true;
        }

        this.strikethrough = !this.strikethrough;
        return this;
    }

    public TextStyle below() {
        if (this.subscript == null) {
            this.subscript = true;
        }

        this.subscript = !this.subscript;
        return this;
    }

    public TextStyle above() {
        if (this.superscript == null) {
            this.superscript = true;
        }

        this.superscript = !this.superscript;
        return this;
    }

    public SimpleAttributeSet attributeSet() {
        SimpleAttributeSet s = new SimpleAttributeSet();
        if (this.foreground != null) {
            StyleConstants.setForeground(s, this.foreground);
        }

        if (this.background != null) {
            StyleConstants.setBackground(s, this.background);
        }

        if (this.fontFamily != null) {
            StyleConstants.setFontFamily(s, this.fontFamily);
        }

        if (this.fontSize != null) {
            StyleConstants.setFontSize(s, this.fontSize);
        }

        if (this.bold != null) {
            StyleConstants.setBold(s, this.bold);
        }

        if (this.italic != null) {
            StyleConstants.setItalic(s, this.italic);
        }

        if (this.strikethrough != null) {
            StyleConstants.setStrikeThrough(s, this.strikethrough);
        }

        if (this.subscript != null) {
            StyleConstants.setSubscript(s, this.subscript);
        }

        if (this.superscript != null) {
            StyleConstants.setSuperscript(s, this.superscript);
        }

        if (this.underline != null) {
            StyleConstants.setUnderline(s, this.underline);
        }

        return s;
    }

    public String toCSSStyles() {
        List<String> s = new ArrayList<>();
        if (this.foreground != null) {
            s.add("-fx-fill: " + Convert.hexColor(this.foreground));
        }

        if (this.background != null) {
            s.add("-rtfx-background-color: " + Convert.hexColor(this.background));
        }

        if (this.fontFamily != null) {
            s.add("-fx-font-family: " + this.fontFamily);
        } /*else {
            s.add("-fx-font-size: " + App.cfg.fontName);
        }*/

        if (this.fontSize != null) {
            s.add("-fx-font-size: " + this.fontSize);
        } /*else {
            s.add("-fx-font-size: " + App.cfg.fontSize);
        }*/

        /*if (this.bold != null) {
            s.add(s, this.bold);
        }

        if (this.italic != null) {
            s.add(s, this.italic);
        }

        if (this.strikethrough != null) {
            s.add(s, this.strikethrough);
        }

        if (this.subscript != null) {
            s.add(s, this.subscript);
        }

        if (this.superscript != null) {
            s.add(s, this.superscript);
        }

        if (this.underline != null) {
            s.add(s, this.underline);
        }*/
        StringBuilder sb = new StringBuilder();
        for (String str : s)
            sb.append(str).append("; ");
        return sb.toString();
    }

}
