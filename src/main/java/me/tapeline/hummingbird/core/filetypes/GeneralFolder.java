package me.tapeline.hummingbird.core.filetypes;

import javafx.scene.control.ContextMenu;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.menus.FileDeleteMenuItem;
import me.tapeline.hummingbird.ui.menus.FileNewMenuItem;

import java.io.File;

public class GeneralFolder extends AbstractFileType {

    public GeneralFolder() {
        icon = Icons.folder;
    }

    @Override
    public boolean appliesToFile(File file) {
        return file.isDirectory();
    }

    @Override
    public void setupContextActions(ContextMenu menu, File contextFile, Project contextProject) {
        menu.getItems().add(new FileNewMenuItem(contextFile));
        menu.getItems().add(new FileDeleteMenuItem(contextFile));
    }

    public boolean canOpen(File file) {
        return false;
    }

    @Override
    public boolean doCustomOpen(File file) {
        return true;
    }

    @Override
    public String id() {
        return "folder";
    }

    @Override
    public int weight() {
        return 0;
    }

}
