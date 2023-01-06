package me.tapeline.hummingbird.view.newproject;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.view.about.AboutController;
import me.tapeline.hummingbird.view.common.HMStage;

public class NewProjectStage extends HMStage {

    public NewProjectController controller;

    public NewProjectStage(App app) {
        super(StageStyle.DECORATED);

        controller = new NewProjectController(this);
        Scene scene = app.loadScene(this, "newproject-view", controller);
        setResizable(false);

        controller.setup();

        setTitle("New Project");
        initModality(Modality.WINDOW_MODAL);
        setScene(scene);
        show();

    }
}
