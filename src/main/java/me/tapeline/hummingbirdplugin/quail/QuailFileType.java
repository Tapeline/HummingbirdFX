package me.tapeline.hummingbirdplugin.quail;

import javafx.scene.control.ContextMenu;
import me.tapeline.hummingbird.core.filetypes.GeneralFile;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.utils.Utils;
import me.tapeline.hummingbird.view.editor.EditorStage;

import java.io.File;

public class QuailFileType extends GeneralFile {

    @Override
    public boolean appliesToFile(File file) {
        return Utils.getExtension(file).equals("q");
    }

    @Override
    public void setupContextActions(EditorStage editor, ContextMenu menu, File contextFile, Project contextProject) {
        super.setupContextActions(editor, menu, contextFile, contextProject);
    }

    @Override
    public String id() {
        return "q";
    }

    @Override
    public int weight() {
        return 999;
    }

}
