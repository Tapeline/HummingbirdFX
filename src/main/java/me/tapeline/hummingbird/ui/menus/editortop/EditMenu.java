package me.tapeline.hummingbird.ui.menus.editortop;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.view.editor.EditorStage;
import me.tapeline.hummingbird.view.settings.SettingsStage;

public class EditMenu extends Menu {

    public EditorStage stage;

    public EditMenu(EditorStage stage) {
        super("Edit");

        this.stage = stage;

        MenuItem find = new MenuItem("Find");
        find.setOnAction(actionEvent -> {

        });
        getItems().add(find);

        MenuItem replace = new MenuItem("Replace");
        replace.setGraphic(new ImageView(Convert.image(Icons.edit)));
        replace.setOnAction(actionEvent -> {

        });
        getItems().add(replace);

        getItems().add(new SeparatorMenuItem());

        MenuItem duplicate = new MenuItem("Duplicate Line");
        duplicate.setOnAction(actionEvent -> {

        });
        getItems().add(duplicate);


        MenuItem toggleCase = new MenuItem("Toggle Case");
        toggleCase.setOnAction(actionEvent -> {

        });
        getItems().add(toggleCase);

    }

}
