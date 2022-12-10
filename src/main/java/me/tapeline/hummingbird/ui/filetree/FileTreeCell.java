package me.tapeline.hummingbird.ui.filetree;

import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.image.ImageView;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.resources.Icons;

public class FileTreeCell extends TreeCell<String> {

    protected void updateItem(String s, boolean empty) {
        super.updateItem(s, empty);
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