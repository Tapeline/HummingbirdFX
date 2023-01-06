package me.tapeline.hummingbird.ui.wizardfields;

import javafx.scene.Node;
import javafx.scene.control.Spinner;

public class IntegerField extends Spinner<Integer> implements FormField<Integer> {

    private String label;
    private String name;

    public IntegerField(String label, String name, int min, int max, int initial, int step) {
        super(min, max, initial, step);
        this.label = label;
        this.name = name;
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
