package me.tapeline.hummingbird.ui.menus.editortop;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import me.tapeline.hummingbird.view.editor.EditorStage;

public class CodeMenu extends Menu {

    public EditorStage stage;

    public CodeMenu(EditorStage stage) {
        super("Code");

        this.stage = stage;
    }

}
