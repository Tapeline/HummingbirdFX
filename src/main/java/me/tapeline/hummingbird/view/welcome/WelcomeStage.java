package me.tapeline.hummingbird.view.welcome;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.view.about.AboutController;
import me.tapeline.hummingbird.view.common.HMStage;

public class WelcomeStage extends HMStage {

    public WelcomeController controller;

    public WelcomeStage(App app) {
        super(StageStyle.DECORATED);

        controller = new WelcomeController(app, this);
        Scene scene = app.loadScene(this, "welcome-view", controller);
        setResizable(false);

        controller.setup();

        setTitle("Welcome");
        setScene(scene);
        show();

    }
}
