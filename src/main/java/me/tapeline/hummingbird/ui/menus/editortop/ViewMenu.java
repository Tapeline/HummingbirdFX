package me.tapeline.hummingbird.ui.menus.editortop;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import me.tapeline.hummingbird.view.editor.EditorStage;

public class ViewMenu extends Menu {

    public EditorStage stage;

    public ViewMenu(EditorStage stage) {
        super("View");

        this.stage = stage;

        MenuItem fullscreen = new MenuItem("Toggle Fullscreen");
        fullscreen.setOnAction(actionEvent -> {
            stage.toggleFullscreen();
        });
        getItems().add(fullscreen);

    }

}
