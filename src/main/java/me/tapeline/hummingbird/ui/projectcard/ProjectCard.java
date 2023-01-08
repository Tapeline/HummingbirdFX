package me.tapeline.hummingbird.ui.projectcard;

import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.utils.Utils;
import me.tapeline.hummingbird.view.common.Dialogs;
import me.tapeline.hummingbird.view.editor.EditorStage;
import me.tapeline.hummingbird.view.welcome.WelcomeStage;

import java.awt.*;
import java.io.File;

public class ProjectCard extends VBox {

    public File file;

    public ProjectCard(App app, WelcomeStage stage, File file) {
        this.file = file;
        Label name = new Label(file.getName());
        name.setStyle("-fx-font-size: 20px; -fx-fill: white;");
        Label desc = new Label(file.getName());
        desc.setStyle("-fx-font-size: 14px; -fx-fill: white;");

        setStyle("-fx-background-color: " + Convert.hexColor(
                Color.getHSBColor(
                        Utils.getHueByText(name.getText()),
                        0.5F,
                        0.5F
                )
        ));

        getChildren().add(name);
        getChildren().add(desc);

        setMaxHeight(48);
        setMinHeight(48);
        setPrefHeight(48);

        setOnMouseClicked(event -> {
            if (file.exists()) {
                app.acquireWakelock();
                stage.close();
                EditorStage editorStage = new EditorStage(App.instance, new Project(file));
                App.instance.openedWindows.add(editorStage);
                app.releaseWakelock();
            } else Dialogs.error(
                    "Project not found",
                    "Project root not found",
                    "Check if project exists"
            );
        });
    }

}
