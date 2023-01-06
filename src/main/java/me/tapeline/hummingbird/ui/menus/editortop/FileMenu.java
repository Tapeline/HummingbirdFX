package me.tapeline.hummingbird.ui.menus.editortop;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.view.common.Dialogs;
import me.tapeline.hummingbird.view.editor.EditorStage;
import me.tapeline.hummingbird.view.newproject.NewProjectStage;
import me.tapeline.hummingbird.view.settings.SettingsStage;

import java.io.File;

public class FileMenu extends Menu {

    public EditorStage stage;

    public FileMenu(EditorStage stage) {
        super("File");

        this.stage = stage;

        MenuItem newProject = new MenuItem("New Project");
        newProject.setGraphic(new ImageView(Convert.image(Icons.folderNew)));
        newProject.setOnAction(actionEvent -> {
            NewProjectStage newProjectStage = new NewProjectStage(stage.app);
        });
        getItems().add(newProject);

        MenuItem openProject = new MenuItem("Open Project");
        openProject.setGraphic(new ImageView(Convert.image(Icons.folderOpen)));
        openProject.setOnAction(actionEvent -> {
            File file = Dialogs.askFolder(stage);
            if (file != null) {
                EditorStage editorStage = new EditorStage(App.instance, new Project(file));
                App.instance.openedWindows.add(editorStage);
            }
        });
        getItems().add(openProject);

        MenuItem closeProject = new MenuItem("Close Project");
        closeProject.setOnAction(actionEvent -> {
            stage.save();
            stage.close();
        });
        getItems().add(closeProject);

        MenuItem saveProject = new MenuItem("Save Project");
        saveProject.setOnAction(actionEvent -> {
            stage.save();
        });
        getItems().add(saveProject);

        getItems().add(new SeparatorMenuItem());

        MenuItem projectSettings = new MenuItem("Project Settings");
        projectSettings.setOnAction(actionEvent -> {

        });
        getItems().add(projectSettings);

        getItems().add(new SeparatorMenuItem());

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

        MenuItem exit = new MenuItem("Exit");
        exit.setGraphic(new ImageView(Convert.image(Icons.cross)));
        exit.setOnAction(actionEvent -> {
            for (Window window : stage.app.openedWindows) {
                if (window instanceof EditorStage)
                    ((EditorStage) window).save();
                if (window instanceof Stage)
                    ((Stage) window).close();
            }
        });
        getItems().add(exit);
    }

}
