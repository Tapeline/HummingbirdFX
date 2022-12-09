package me.tapeline.hummingbird.ui.filetree;

import javafx.event.EventType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;

import java.io.File;

public class FileTreeItem extends TreeItem<String> {

    public File file;
    public ContextMenu contextMenu;
    public Project project;

    public FileTreeItem(File file, Project project) {
        super(file.getName());
        this.file = file;
        this.project = project;
        contextMenu = new ContextMenu();
        reload();
    }

    public void reload() {
        if (!file.exists()) {
            getParent().getChildren().remove(this);
            getChildren().clear();
        } else {
            String[] paths = file.list();
            if (paths != null) {
                for (String path : paths)
                    getChildren().add(new FileTreeItem(new File(path), project));
            }
            AbstractFileType fileType = FS.getTypeForFile(file);
            if (fileType != null) {
                setGraphic(new ImageView(Icons.convertToFxImage(fileType.icon)));
                fileType.setupContextActions(contextMenu, file, project);
            }
            setValue(file.getName());
        }
    }
}
