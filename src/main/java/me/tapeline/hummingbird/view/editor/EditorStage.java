package me.tapeline.hummingbird.view.editor;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.ui.editor.tabs.AbstractEditorTab;
import me.tapeline.hummingbird.view.common.Dialogs;
import me.tapeline.hummingbird.view.common.HMStage;
import org.eclipse.jgit.lib.Repository;

import java.io.File;
import java.io.IOException;

public class EditorStage extends HMStage {

    public Project project;
    public EditorController controller = null;
    public App app;

    public EditorStage(App app, Project project) {
        super(StageStyle.DECORATED);
        this.project = project;
        this.controller = new EditorController(this);
        this.app = app;

        Scene scene = app.loadScene(this, "editor-view", controller);
        controller.initCustomItems(this);
        setOnCloseRequest((e) -> {
            save();
        });

        controller.rebuildFileTree(project, project.root);

        setTitle("Editor - " + project.root.getName());
        setScene(scene);
        show();
    }

    public void save() {
        project.saveConfig();
        for (Tab tab : controller.workspaceTabs.getTabs()) {
            if (tab instanceof AbstractEditorTab) {
                try {
                    ((AbstractEditorTab) tab).save(this);
                } catch (Exception ex) {
                    Dialogs.exception(ex);
                }
            }
        }
    }

    public EditorController getController() {
        return controller;
    }

    public void toggleFullscreen() {
        setFullScreen(!isFullScreen());
    }

}
