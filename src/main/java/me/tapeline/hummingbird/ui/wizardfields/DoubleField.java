package me.tapeline.hummingbird.ui.wizardfields;

import javafx.scene.Node;
import javafx.scene.control.Spinner;

public class DoubleField extends Spinner<Double> implements FormField<Double> {

    private String label;
    private String name;

    public DoubleField(String label, String name, double min, double max, double initial, double step) {
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
