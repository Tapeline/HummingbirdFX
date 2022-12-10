package me.tapeline.hummingbird.expansions.colorschemes;

import java.awt.Color;
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

    public TextStyle() {
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
}
