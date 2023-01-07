package me.tapeline.hummingbird.filesystem.project;

import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.filesystem.project.fsmodel.FileSystemModel;
import me.tapeline.hummingbird.view.common.Dialogs;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Project {

    public FileSystemModel fileSystem;
    public File root;
    public HashMap<String, Object> config = new HashMap<>();

    public Project(File f, HashMap<String, Object> config) {
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

        File configFile = new File(f.getAbsolutePath() + "/.ide/project.yml");
        File configRoot = new File(f.getAbsolutePath() + "/.ide");
        Yaml yaml = new Yaml();
        if (configFile.exists()) {
            this.config.putAll(yaml.load(FS.readFile(configFile)));
        } else {
            configRoot.mkdirs();
        }
        this.config.putAll(config);
        saveConfig();
    }

    public Project(File f) {
        this(f, App.cfg.defaultProjectConfigTemplate);
    }

    public void saveConfig() {
        Yaml yaml = new Yaml();
        FS.writeFile(new File(root.getAbsolutePath() + "/.ide/project.yml"), yaml.dump(config));
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

    public Repository getRepo() {
        File gitFolder = new File(root.getAbsolutePath() + "/.git");
        if (gitFolder.exists()) {
            try {
                return new FileRepositoryBuilder().setGitDir(gitFolder).build();
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

}