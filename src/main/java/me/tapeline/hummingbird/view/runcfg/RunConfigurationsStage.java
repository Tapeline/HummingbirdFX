package me.tapeline.hummingbird.view.runcfg;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.view.common.HMStage;
import me.tapeline.hummingbird.view.editor.EditorController;
import me.tapeline.hummingbird.view.settings.SettingsController;

public class RunConfigurationsStage extends HMStage {

    public EditorController editorController;
    public RunConfigurationsController controller;

    public RunConfigurationsStage(App app, EditorController editorController) {
        super(StageStyle.DECORATED);

        this.editorController = editorController;
        controller = new RunConfigurationsController(this);
        Scene scene = app.loadScene(this, "runcfg-view", controller);

        controller.loadConfigurations(App.cfg.runConfigurations);
        controller.setupItems();

        setTitle("Run Configurations");
        initModality(Modality.WINDOW_MODAL);
        setScene(scene);
        show();
    }
}
