package me.tapeline.hummingbird.ui.filetree;

import java.io.File;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;

public class FileTreeItem extends TreeItem<String> {

    public File file;
    public ContextMenu contextMenu;
    public Project project;

    public FileTreeItem(File file, Project project) {
        super(file.getName());
        this.file = file;
        this.project = project;
        this.contextMenu = new ContextMenu();
        this.reload();
    }

    public void reload() {
        File[] paths = this.file.listFiles();
        if (paths != null) {
            for (File path : paths) {
                this.getChildren().add(new FileTreeItem(path, this.project));
            }
        }

        this.getChildren().sort(new FileComparator());
        AbstractFileType fileType = FS.getTypeForFile(this.file);
        if (fileType != null) {
            fileType.setupContextActions(this.contextMenu, this.file, this.project);
            this.setGraphic(new ImageView(Icons.convertToFxImage(fileType.icon)));
        }

        this.setValue(this.file.getName());
    }

    public boolean containsFile(String name) {
        for (TreeItem<?> item : getChildren()) {
            if (item instanceof FileTreeItem)
                if (item.getValue().equals(name))
                    return true;
        }
        return false;
    }

}
