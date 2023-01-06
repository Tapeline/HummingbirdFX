package me.tapeline.hummingbird.expansions.projecttype;

import me.tapeline.hummingbird.ui.wizardfields.Form;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractProjectGenerator {

    public abstract String name();
    public abstract String label();
    public abstract BufferedImage icon();
    public abstract List<Form> prepareForms();
    public abstract void onCreate(HashMap<String, Object> formValues);
    public abstract String validateForms(HashMap<String, Object> formValues);
    public String toString() {
        return label();
    }

}