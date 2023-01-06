package me.tapeline.hummingbird.ui.wizardfields;

import javafx.scene.Node;
import javafx.scene.control.CheckBox;

public class BooleanField extends CheckBox implements FormField<Boolean> {

    private String label;
    private String name;

    public BooleanField(String label, String name, boolean initial) {
        super();
        setSelected(initial);
        this.label = label;
        this.name = name;
    }

    @Override
    public Boolean getValue() {
        return isSelected();
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Node getGuiNode() {
        return this;
    }

}
