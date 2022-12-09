package me.tapeline.hummingbird.ui.filetree;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;

import java.io.File;

public class FileTreeCell extends TreeCell<String> {

    public File file;
    public ContextMenu contextMenu;
    public Project project;

    public FileTreeCell(File file, Project project) {
        this.file = file;
        this.project = project;
        contextMenu = new ContextMenu();
        reload();
    }

    public void reload() {
        if (!file.exists()) {
            getChildren().clear();
            getTreeItem().getChildren().clear();
            getTreeItem().getParent().getChildren().remove(getTreeItem());
        } else {
            String[] paths = file.list();
            if (paths != null) {
                for (String path : paths)
                    getChildren().add(new FileTreeCell(new File(path), project));
            }
            AbstractFileType fileType = FS.getTypeForFile(file);
            if (fileType != null) {
                setGraphic(new ImageView(Icons.convertToFxImage(fileType.icon)));
                fileType.setupContextActions(contextMenu, file, project);
                setContextMenu(contextMenu);
            }
            setText(file.getName());
        }
    }
}
