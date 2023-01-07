package me.tapeline.hummingbird.ui.menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.view.common.Dialogs;
import me.tapeline.hummingbird.view.editor.EditorController;

import java.io.File;

public class FileNewFolderMenuItem extends MenuItem {

    public File file;

    public FileNewFolderMenuItem(EditorController controller, File file) {
        super("New folder");
        this.file = file;
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = Dialogs.askString("New Folder", "New Folder",
                        "Input new folder name", "");
                if (s == null || s.isEmpty() || s.isBlank())
                    return;
                new File(file.getAbsolutePath() + "/" + s).mkdirs();
                controller.updateFileTree();
            }
        });
    }

}
