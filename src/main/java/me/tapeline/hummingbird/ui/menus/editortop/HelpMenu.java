package me.tapeline.hummingbird.ui.menus.editortop;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import me.tapeline.hummingbird.view.about.AboutStage;
import me.tapeline.hummingbird.view.editor.EditorStage;

public class HelpMenu extends Menu {

    public EditorStage stage;

    public HelpMenu(EditorStage stage) {
        super("Help");

        this.stage = stage;

        MenuItem about = new MenuItem("About");
        about.setOnAction(actionEvent -> {
            AboutStage aboutStage = new AboutStage(stage.app);
        });
        getItems().add(about);

    }

}
