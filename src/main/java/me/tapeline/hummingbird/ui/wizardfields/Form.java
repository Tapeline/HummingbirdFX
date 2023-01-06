package me.tapeline.hummingbird.ui.wizardfields;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.List;

public abstract class Form extends AnchorPane {

    public String name;
    public String label;
    public HashMap<String, FormField<?>> fields = new HashMap<>();
    public HashMap<String, Object> values = new HashMap<>();

    public Form(String name, String label, List<FormField<?>> model, boolean submittable) {
        this.name = name;
        this.label = label;
        for (FormField<?> ff : model) fields.put(ff.getName(), ff);

        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(8);

        int line = 0;
        for (FormField<?> ff : model)
            content.addRow(line++, new Label(ff.getLabel()), ff.getGuiNode());

        AnchorPane.setTopAnchor(content, 10D);
        AnchorPane.setLeftAnchor(content, 24D);
        AnchorPane.setRightAnchor(content, 24D);
        AnchorPane.setBottomAnchor(content, 48D);

        if (submittable) {
            Button submit = new Button("Save & Submit");
            submit.setOnAction(actionEvent -> {
                parseValues();
                onSubmit();
            });
            AnchorPane.setBottomAnchor(submit, 16D);
            AnchorPane.setRightAnchor(submit, 16D);
            getChildren().add(submit);
        }
        getChildren().add(new ScrollPane(content));
    }

    public void parseValues() {
        for (String key : fields.keySet())
            values.put(key, fields.get(key).getValue());
    }

    public abstract void onSubmit();

    public void collectValuesToMap(HashMap<String, Object> collector) {
        parseValues();
        for (String key : values.keySet())
            collector.put(name + "." + key, values.get(key));
    }

}
