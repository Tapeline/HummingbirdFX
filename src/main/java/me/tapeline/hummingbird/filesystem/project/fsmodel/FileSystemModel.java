package me.tapeline.hummingbird.filesystem.project.fsmodel;

import java.io.File;

public class FileSystemModel {

    public Element root;

    public FileSystemModel(File root) {
        this.root = new Element(root);
    }

    public void reload() {
        root.reload();
    }

}
