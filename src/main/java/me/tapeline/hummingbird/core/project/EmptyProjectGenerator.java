package me.tapeline.hummingbird.core.project;

import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.expansions.projecttype.AbstractProjectGenerator;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.wizardfields.Form;
import me.tapeline.hummingbird.view.editor.EditorStage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EmptyProjectGenerator extends AbstractProjectGenerator {
    @Override
    public String name() {
        return "emptyProjectGenerator";
    }

    @Override
    public String label() {
        return "Empty Project";
    }

    @Override
    public BufferedImage icon() {
        return Icons.folder;
    }

    @Override
    public List<Form> prepareForms() {
        return new ArrayList<>();
    }

    public String validateForms(HashMap<String, Object> formValues) {
        File projectRoot = new File(formValues.get("general.projectDir").toString());
        if (projectRoot.exists())
            return "Project root already exists";
        return null;
    }

    @Override
    public void onCreate(HashMap<String, Object> formValues) {
        File projectRoot = new File(formValues.get("general.projectDir").toString());
        projectRoot.mkdirs();
        EditorStage editorStage = new EditorStage(App.instance, new Project(projectRoot));
        App.instance.openedWindows.add(editorStage);
    }

}
