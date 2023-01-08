package me.tapeline.hummingbird.view.welcome;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.projectcard.ProjectCard;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.view.common.Dialogs;
import me.tapeline.hummingbird.view.editor.EditorStage;
import me.tapeline.hummingbird.view.newproject.NewProjectStage;
import me.tapeline.hummingbird.view.settings.SettingsStage;

import java.io.File;

public class WelcomeController {

    @FXML
    public VBox lastProjects;

    @FXML
    public Button newProjectButton;

    @FXML
    public Button openProjectButton;

    @FXML
    public Button settingsButton;

    @FXML
    public Button exitButton;


    public WelcomeStage stage;
    public App app;

    public WelcomeController(App app, WelcomeStage stage) {
        this.stage = stage;
        this.app = app;
    }

    public void setup() {
        for (String path : App.cfg.lastOpened)
            lastProjects.getChildren().add(new ProjectCard(app, stage, new File(path)));

        exitButton.setOnAction(actionEvent -> {
            stage.close();
        });

        settingsButton.setOnAction(actionEvent -> {
            SettingsStage settingsStage = new SettingsStage(app);
        });

        openProjectButton.setOnAction(actionEvent -> {
            File file = Dialogs.askFolder(stage);
            if (file != null) {
                EditorStage editorStage = new EditorStage(App.instance, new Project(file));
                App.instance.openedWindows.add(editorStage);
            }
        });

        newProjectButton.setOnAction(actionEvent -> {
            NewProjectStage newProjectStage = new NewProjectStage(app);
        });
    }

}
