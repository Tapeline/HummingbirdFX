package me.tapeline.hummingbird.ui.editor.tabs;

import javafx.scene.control.Tab;
import me.tapeline.hummingbird.view.editor.EditorStage;

public abstract class AbstractEditorTab extends Tab {

    public abstract void save(EditorStage stage) throws Exception;

}
