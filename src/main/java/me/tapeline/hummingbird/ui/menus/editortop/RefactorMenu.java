package me.tapeline.hummingbird.ui.menus.editortop;

import javafx.scene.control.Menu;
import me.tapeline.hummingbird.view.editor.EditorStage;

public class RefactorMenu extends Menu {

    public EditorStage stage;

    public RefactorMenu(EditorStage stage) {
        super("Refactor");

        this.stage = stage;
    }

}
