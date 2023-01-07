package me.tapeline.hummingbird.expansions.filetype;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.scene.control.ContextMenu;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.view.editor.EditorStage;

public abstract class AbstractFileType {

    public List<String> extensions = new ArrayList();
    public BufferedImage icon;

    public AbstractFileType(String... ext) {
        this.extensions.addAll(Arrays.asList(ext));
        this.icon = Icons.file;
    }

    public AbstractFileType(BufferedImage icon, String... ext) {
        this.extensions.addAll(Arrays.asList(ext));
        this.icon = icon;
    }

    public abstract boolean appliesToFile(File var1);

    public abstract void setupContextActions(EditorStage editor, ContextMenu menu, File contextFile, Project contextProject);

    public boolean canOpen(File file) {
        return true;
    }

    public boolean doCustomOpen(File file) {
        return false;
    }

    public void customOpen(EditorStage frame, File file) {}

    public abstract String id();

    public abstract int weight();


}
