package me.tapeline.hummingbird.ui.menus.editortop;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.view.common.Dialogs;
import me.tapeline.hummingbird.view.editor.EditorStage;
import me.tapeline.hummingbird.view.runcfg.RunConfigurationsStage;

public class RunMenu extends Menu {

    public EditorStage stage;

    public RunMenu(EditorStage stage) {
        super("Run");

        this.stage = stage;

        MenuItem run = new MenuItem("Run");
        run.setGraphic(new ImageView(Convert.image(Icons.start)));
        run.setOnAction(actionEvent -> {
            stage.controller.runCurrentConfiguration();
        });
        getItems().add(run);

        MenuItem stop = new MenuItem("Stop");
        stop.setGraphic(new ImageView(Convert.image(Icons.stop)));
        stop.setOnAction(actionEvent -> {
            stage.controller.runTabsPane.destroyCurrent();
        });
        getItems().add(stop);

        MenuItem stopAll = new MenuItem("Stop All");
        stopAll.setOnAction(actionEvent -> {
            stage.controller.runTabsPane.destroyAll();
        });
        getItems().add(stopAll);

        getItems().add(new SeparatorMenuItem());

        MenuItem editConfigurations = new MenuItem("Edit Configurations");
        editConfigurations.setGraphic(new ImageView(Convert.image(Icons.edit)));
        editConfigurations.setOnAction(actionEvent -> {
            RunConfigurationsStage runConfigurationsStage = new RunConfigurationsStage(stage.app, stage.controller);
        });
        getItems().add(editConfigurations);

        MenuItem reloadConfigurations = new MenuItem("Reload Configurations");
        reloadConfigurations.setGraphic(new ImageView(Convert.image(Icons.refresh)));
        reloadConfigurations.setOnAction(actionEvent -> {
            stage.controller.refreshRunConfigurations();
        });
        getItems().add(reloadConfigurations);
    }

}
