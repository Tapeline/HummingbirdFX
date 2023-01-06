package me.tapeline.hummingbird.view.about;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.view.common.HMStage;
import me.tapeline.hummingbird.view.settings.SettingsController;

public class AboutStage extends HMStage {

    public AboutController controller;

    public AboutStage(App app) {
        super(StageStyle.DECORATED);

        controller = new AboutController();
        Scene scene = app.loadScene(this, "about-view", controller);
        setResizable(false);

        controller.setup();

        setTitle("About");
        initModality(Modality.WINDOW_MODAL);
        setScene(scene);
        show();

    }
}
