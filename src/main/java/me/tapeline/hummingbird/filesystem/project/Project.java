package me.tapeline.hummingbird.filesystem.project;

import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.filesystem.project.fsmodel.FileSystemModel;

import java.io.File;
import java.io.IOException;

public class Project {

    public FileSystemModel fileSystem;
    public File root;

    public Project(File f) {
        root = f;
        fileSystem = new FileSystemModel(root);
        boolean hasProject = false;
        for (String path : App.cfg.lastOpened) {
            if (path.equals(f.getAbsolutePath())) {
                hasProject = true;
                break;
            }
        }
        if (!hasProject) App.cfg.lastOpened.add(f.getAbsolutePath());
    }

    public void resolveFiles() {
        fileSystem.reload();
    }

    public File getRelativeToProjectRoot(File f) {
        String p = f.getAbsolutePath().replace(root.getAbsolutePath(), "");
        if (p.startsWith("/")) p = p.substring(1);
        p = root.getName() + "/" + p;
        return new File(p);
    }

}