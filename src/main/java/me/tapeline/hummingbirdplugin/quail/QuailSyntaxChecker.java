package me.tapeline.hummingbirdplugin.quail;

import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;
import me.tapeline.hummingbird.expansions.colorschemes.TextStyle;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.highlighter.Bounds;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.syntaxchecker.AutoFixAction;
import me.tapeline.hummingbird.expansions.syntaxchecker.SyntaxTip;
import me.tapeline.hummingbird.filesystem.FS;

import java.io.File;
import java.util.*;

public class QuailSyntaxChecker extends AbstractSyntaxChecker {

    private int start = 0;
    private int current = 0;
    private int line = 1;
    private String source;
    private List<SyntaxTip> tokens = new ArrayList<>();
    private AbstractColorScheme colors;

    private Map<String, TextStyle> keywords;

    @Override
    public AbstractFileType getApplicableFileType() {
        return new QuailFileType();
    }

    @Override
    public List<SyntaxTip> check(String text) {
        source = text.toString();
        colors = Registry.currentTheme.scheme();

        tokens = new ArrayList<>();

        start = 0;
        current = 0;
        line = 1;

        keywords = new HashMap<>();
        List<String> kw = Arrays.asList(
                "func", "string", "bool", "num", "container", "list", "metacontainer",
                "object", "function", "method", "staticmethod", "class", "anonymous",
                "require", "void", "anyof", "local", "final", "static", "do", "does", "end",
                "then", "has", "with", "as", "through", "if", "elseif", "else", "try", "catch",
                "while", "loop", "stop when", "every", "on", "when", "override", "gets", "sets",
                "is type of", "instanceof", "and", "or", "in", "is same type as", "of", "filter",
                "in power of", "plus", "minus", "divided by", "multiplied by", "is greater than",
                "is less than", "is greater or equal to", "is less or equal to", "is", "step",
                "should have", "should be", "should now be", "should now be set", "should be set", "'s",
                "not", "negate", "notnull", "exists", "assert", "use", "throw", "using", "deploy",
                "strike", "return", "breakpoint", "break", "continue", "memory"
        );
        for (String k : kw)
            keywords.put(k, colors.keyword);

        while (!isAtEnd()) {
            start = current;
            scanToken();
        }

        return tokens;
    }

    @Override
    public String name() {
        return "Quail Lint (QEP A)";
    }

    private boolean isAtEnd() {
        return current >= source.length();
    }

    private char advance() {
        return source.charAt(current++);
    }

    private char peek() {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext() {
        if (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private void addToken(String s, SyntaxTip.TipType type) {
        tokens.add(new SyntaxTip(new Bounds(start, current), s, type));
    }

    private void addToken(String s, SyntaxTip.TipType type, AutoFixAction... actions) {
        SyntaxTip tip = new SyntaxTip(new Bounds(start, current), s, type);
        tip.getFixes().addAll(Arrays.asList(actions));
        tokens.add(tip);
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    private boolean isAlpha(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                c == '_';
    }

    private boolean isAlphaNumeric(char c) {
        return isAlpha(c) || isDigit(c);
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(':
            case ')':
            case '{':
            case '}':
            case '[':
            case ']':
            case ':':
            case '+':
            case '-':
            case '/':
            case '*':
            case '=':
            case '>':
            case '<':
            case ',':
            case ';':
            case '.': {
                //addToken(colors.operator);
                break;
            }

            case '#': {
                boolean isDoc = false;
                if (peekNext() == '~')
                    isDoc = true;
                while (peek() != '\n' && !isAtEnd()) advance();
                //addToken(isDoc? colors.commentDoc : colors.comment);
                if (isDoc) addToken(source.substring(start, current), SyntaxTip.TipType.TODO);
                break;
            }
            case '@': {
                scanId(true);
                break;
            }
            case '"': scanString(); break;
            default: {
                if (isDigit(c)) {
                    scanNumber();
                } else if (isAlpha(c)) {
                    scanId(false);
                }
            }
        }
    }

    private void scanString() {
        if (!isAtEnd())
            while (advance() != '"')
                if (isAtEnd()) break;
        //addToken(colors.string);
    }

    private void scanNumber() {
        while (isDigit(peek())) advance();

        if (peek() == '.' && isDigit(peekNext())) {
            advance();
            while (isDigit(peek())) advance();
        }

        //addToken(colors.number);
    }

    private void scanId(boolean metadata) {
        if (metadata) {
            while (isAlphaNumeric(peek())) advance();
            String text = source.substring(start, current);
            if (!isGoodId(text))
                addToken("Writing _ in IDs is a bad idea [QEP A1]", SyntaxTip.TipType.CODE_STYLE,
                        new IdUnderscoreRemover(start, current));
            return;
        }

        while (isAlphaNumeric(peek())) advance();

        String text = source.substring(start, current);
        TextStyle type = keywords.get(text);
        if (type == null) type = colors.regular;
        if (!isGoodId(text))
            addToken("Writing _ in IDs is a bad idea [QEP A1]", SyntaxTip.TipType.CODE_STYLE,
                    new IdUnderscoreRemover(start, current));
    }

    private boolean isGoodId(String s) {
        return !s.contains("_");
    }

    static class IdUnderscoreRemover extends AutoFixAction {

        public int start;
        public int end;

        public IdUnderscoreRemover(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public String getLabel() {
            return "Remove underscores";
        }

        @Override
        public void fix(File file) {
            String content = FS.readFile(file);
            if (content != null) {
                String pre = content.substring(start);
                String post = content.substring(end);
                String subject = content.substring(start, end);
                subject = subject.replaceAll("_", "");
                FS.writeFile(file, pre + subject + post);
            }
        }
    }
}
