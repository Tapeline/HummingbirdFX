package me.tapeline.hummingbird.expansions.customactions;

import javafx.scene.control.Button;
import me.tapeline.hummingbird.ui.editor.tabs.AbstractEditorTab;
import me.tapeline.hummingbird.view.editor.EditorStage;

import java.awt.image.BufferedImage;

public abstract class AbstractPluginShortcut {

    public abstract String actionName();
    public abstract BufferedImage actionIcon();
    public abstract void updateButton(EditorStage stage, AbstractEditorTab currentTab, Button button);
    public abstract void action(EditorStage stage, AbstractEditorTab currentTab);

}
