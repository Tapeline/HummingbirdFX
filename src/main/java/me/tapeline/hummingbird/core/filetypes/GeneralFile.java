package me.tapeline.hummingbird.core.filetypes;

import java.io.File;

import javafx.scene.control.ContextMenu;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.menus.FileDeleteMenuItem;
import me.tapeline.hummingbird.utils.Utils;
import me.tapeline.hummingbird.view.editor.EditorStage;

public class GeneralFile extends AbstractFileType {
    public GeneralFile() {
        this.icon = Icons.file;
    }

    public boolean appliesToFile(File file) {
        if (file.isDirectory()) return false;
        if (extensions.size() == 0) return true;
        String ext = Utils.getExtension(file);
        for (String e : extensions)
            if (e.equals(ext))
                return true;
        return false;
    }

    public void setupContextActions(EditorStage editor, ContextMenu menu, File contextFile, Project contextProject) {
        menu.getItems().add(new FileDeleteMenuItem(editor.controller, contextFile));
    }

    public String id() {
        return "plain";
    }

    @Override
    public int weight() {
        return 0;
    }

}