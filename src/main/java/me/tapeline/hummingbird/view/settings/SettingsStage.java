package me.tapeline.hummingbird.view.settings;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.ui.editor.tabs.AbstractEditorTab;
import me.tapeline.hummingbird.view.common.Dialogs;
import me.tapeline.hummingbird.view.common.HMStage;

public class SettingsStage extends HMStage {

    public SettingsController controller;

    public SettingsStage(App app) {
        super(StageStyle.DECORATED);

        controller = new SettingsController(App.cfg, this);
        Scene scene = app.loadScene(this, "settings-view", controller);

        controller.fillInSettings();

        setTitle("Settings");
        initModality(Modality.WINDOW_MODAL);
        setScene(scene);
        show();

    }
}
