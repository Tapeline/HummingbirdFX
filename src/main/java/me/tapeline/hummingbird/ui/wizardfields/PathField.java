package me.tapeline.hummingbird.ui.wizardfields;

import javafx.scene.Node;
import javafx.scene.control.TextField;

public class PathField extends TextField implements FormField<String> {

    private String label;
    private String name;

    public PathField(String label, String name, String initial) {
        super(initial);
        this.label = label;
        this.name = name;
    }

    @Override
    public String getValue() {
        return getText();
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
