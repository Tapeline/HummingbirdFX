package me.tapeline.hummingbird.expansions.customactions;

import javax.swing.Icon;
import me.tapeline.hummingbird.view.editor.EditorStage;

public abstract class AbstractPluginAction {

    public abstract String actionName();

    public abstract Icon actionIcon();

    public abstract ActionTarget actionTarget();

    public abstract void action(EditorStage stage);

}
