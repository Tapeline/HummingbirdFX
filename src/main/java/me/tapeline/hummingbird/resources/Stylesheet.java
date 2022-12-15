package me.tapeline.hummingbird.resources;

public class Stylesheet {

    public enum Type {
        RAW,
        FILE
    }

    public Type type;
    public String content;

    public Stylesheet(Type type, String content) {
        this.type = type;
        this.content = content;
    }

}
