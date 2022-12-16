package me.tapeline.hummingbird.ui.filetree;

import com.almasb.fxgl.app.GameApplication;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.resources.Icons;

import java.io.File;
import java.util.Objects;

public class FileTreeCell extends TreeCell<String> {

    protected void updateItem(String s, boolean empty) {
        super.updateItem(s, empty);

        FileTreeItem item = (FileTreeItem) getTreeItem();
        if (item != null) {
            if (!item.file.exists())
                item.getParent().getChildren().remove(item);
            if (item.file.listFiles() != null && item.getChildren().size() == 0) {
                for (File file : Objects.requireNonNull(item.file.listFiles())) {
                    item.getChildren().add(new FileTreeItem(file, item.project));
                }
            } else if (item.file.listFiles() == null ||
                       Objects.requireNonNull(item.file.listFiles()).length == 0) {
                item.getChildren().clear();
            } else if (item.file.listFiles() != null) {
                for (File file : Objects.requireNonNull(item.file.listFiles())) {
                    if (!item.containsFile(file.getName()))
                        item.getChildren().add(new FileTreeItem(file, item.project));
                }
            }
            item.getChildren().sort(new FileComparator());
        }

        if (empty) {
            this.setText(null);
            this.setGraphic(null);
        } else if (this.getTreeItem() instanceof FileTreeItem) {
            AbstractFileType fileType = FS.getTypeForFile(((FileTreeItem)this.getTreeItem()).file);
            if (fileType != null) {
                ((FileTreeItem) this.getTreeItem()).contextMenu.getItems().clear();
                fileType.setupContextActions(((FileTreeItem) this.getTreeItem()).contextMenu,
                        ((FileTreeItem) this.getTreeItem()).file, ((FileTreeItem) this.getTreeItem()).project);
                this.setGraphic(new ImageView(Icons.convertToFxImage(fileType.icon)));
            }

            this.setText(((FileTreeItem)this.getTreeItem()).file.getName());
            this.setContextMenu(((FileTreeItem)this.getTreeItem()).contextMenu);
        }
    }

}