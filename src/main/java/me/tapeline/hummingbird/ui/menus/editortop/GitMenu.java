package me.tapeline.hummingbird.ui.menus.editortop;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.view.about.AboutStage;
import me.tapeline.hummingbird.view.common.Dialogs;
import me.tapeline.hummingbird.view.editor.EditorStage;
import me.tapeline.hummingbird.view.settings.SettingsStage;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;

public class GitMenu extends Menu {

    public EditorStage stage;

    public GitMenu(EditorStage stage) {
        super("Git");

        this.stage = stage;

        MenuItem makeRepository = new MenuItem("Make Repository");
        makeRepository.setOnAction(actionEvent -> {
            File gitFolder = new File(stage.project.root.getAbsolutePath() + "/.git");
            boolean doNotCreateNew = gitFolder.exists() &&
                    Dialogs.confirm("Init Repository", "Repository already exists",
                            "Do you wish to recreate it?").orElse(ButtonType.CANCEL) != ButtonType.OK;
            if (!doNotCreateNew) {
                try {
                    Repository repo = FileRepositoryBuilder.create(gitFolder);
                    repo.create();
                } catch (Exception e) {
                    Dialogs.exception(e);
                }
            }
        });
        getItems().add(makeRepository);

        getItems().add(new SeparatorMenuItem());

        MenuItem commit = new MenuItem("Commit");
        commit.setOnAction(actionEvent -> {
            Repository repo = stage.project.getRepo();
            if (repo == null) Dialogs.error("No repository", "No repository", "No repository");

        });
        getItems().add(commit);

    }

}
