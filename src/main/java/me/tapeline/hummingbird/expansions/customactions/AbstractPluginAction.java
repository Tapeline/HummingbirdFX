package me.tapeline.hummingbird.expansions.customactions;

import me.tapeline.hummingbird.windows.forms.editor.EditorFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public abstract class AbstractPluginAction {

    public abstract String actionName();
    public abstract Icon actionIcon();
    public abstract ActionTarget actionTarget();

    public abstract void action(EditorFrame form);

}
