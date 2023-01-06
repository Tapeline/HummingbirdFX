package me.tapeline.hummingbird.ui.menus.editortop;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.view.editor.EditorStage;
import me.tapeline.hummingbird.view.settings.SettingsStage;

public class ToolsMenu extends Menu {

    public EditorStage stage;

    public ToolsMenu(EditorStage stage) {
        super("Tools");

        this.stage = stage;

        MenuItem settings = new MenuItem("Settings");
        settings.setGraphic(new ImageView(Convert.image(Icons.settings)));
        settings.setOnAction(actionEvent -> {
            SettingsStage settingsStage = new SettingsStage(stage.app);
        });
        getItems().add(settings);

        MenuItem plugins = new MenuItem("Plugins");
        plugins.setOnAction(actionEvent -> {

        });
        getItems().add(plugins);

        getItems().add(new SeparatorMenuItem());

    }

}
