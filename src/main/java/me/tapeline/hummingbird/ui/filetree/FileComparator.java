package me.tapeline.hummingbird.ui.filetree;

import javafx.scene.control.TreeItem;

import java.util.Comparator;

public class FileComparator implements Comparator<TreeItem<String>> {

    @Override
    public int compare(TreeItem<String> a, TreeItem<String> b) {
        if (a instanceof FileTreeItem o1 && b instanceof FileTreeItem o2) {
            if (o1.file.isDirectory() && !o2.file.isDirectory())
                return -1;
            if (!o1.file.isDirectory() && o2.file.isDirectory())
                return 1;
            else
                return o1.file.getName().compareTo(o2.file.getName());
        }
        return 0;
    }

}
