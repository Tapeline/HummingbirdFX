package me.tapeline.hummingbird.ui.wizardfields;

import javafx.scene.Node;

public interface FormField<T> {

    T getValue();
    String getLabel();
    String getName();
    Node getGuiNode();

}
