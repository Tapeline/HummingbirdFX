package me.tapeline.hummingbird.ui.editor.tabs;

import javafx.scene.Node;
import javafx.scene.control.Tab;

public class EditorTab extends Tab {

    public EditorTab() {
    }

    public EditorTab(String s) {
        super(s);
    }

    public EditorTab(String s, Node node) {
        super(s, node);
    }
}
