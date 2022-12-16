package me.tapeline.hummingbird.ui.menus;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.view.common.Dialogs;

import java.io.File;

public class FileNewMenuItem extends MenuItem {

    public File file;

    public FileNewMenuItem(File file) {
        super("New file");
        this.file = file;
        this.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String s = Dialogs.askString("New File", "New File", "Input new file name", "");
                if (s == null || s.isEmpty() || s.isBlank())
                    return;
                FS.writeFile(file.getAbsolutePath() + "/" + s, "");
            }
        });
    }

}
