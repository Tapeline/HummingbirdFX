package me.tapeline.hummingbirdplugin.quail;

import me.tapeline.hummingbird.core.filetypes.GeneralFile;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.ui.editor.tabs.CodeEditorTab;
import me.tapeline.hummingbird.utils.Utils;
import me.tapeline.hummingbird.view.editor.EditorStage;

import javax.swing.*;
import java.io.File;

public class QuailFileType extends GeneralFile {

    @Override
    public boolean appliesToFile(File file) {
        return Utils.getExtension(file).equals("q");
    }

    @Override
    public boolean doCustomOpen(File file) {
        return true;
    }

    @Override
    public void customOpen(EditorStage frame, File file) {
        frame.getController().openTab(new CodeEditorTab(file, FS.readFile(file)), file.getName());
    }

    @Override
    public String id() {
        return "q";
    }

}
