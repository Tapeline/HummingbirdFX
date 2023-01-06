package me.tapeline.hummingbird.ui.settings.tabs;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.configuration.Config;
import me.tapeline.hummingbird.configuration.Configuration;
import me.tapeline.hummingbird.utils.Utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SettingsTab extends Tab {

    public Map<String, Object> settings = new HashMap<>();
    public Map<String, Node> inputs = new HashMap<>();
    public String section;

    public Object getInput(String key) {
        Node c = inputs.get(key);
        if (c == null) return null;
        if (c instanceof TextField)
            return ((TextField) c).getText();
        if (c instanceof Spinner<?>)
            return ((Spinner<?>) c).getValue();
        if (c instanceof CheckBox)
            return ((CheckBox) c).isSelected();
        return null;
    }

    public SettingsTab(String section, Configuration cfg) {
        super();
        this.section = section;

        setText(Utils.idToHumanReadableName(section));

        AnchorPane anchorPane = new AnchorPane();

        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(8);

        try {
            Field[] fields = cfg.getClass().getFields();
            for (Field field : fields) {
                Config configAnnotation = field.getAnnotation(Config.class);
                if (configAnnotation != null) {
                    if (configAnnotation.section().equals(section) &&
                            configAnnotation.showInSettings()) {
                        String id = !configAnnotation.configurationField().equals("") ?
                                configAnnotation.configurationField() :
                                field.getName();
                        settings.put(
                                id,
                                field.get(cfg)
                        );
                        Node input = null;
                        if (field.getType().equals(String.class)) {
                            input = new TextField();
                            ((TextField) input).setText(field.get(cfg).toString());
                        }
                        if (field.getType().equals(Integer.class) ||
                                field.getType().equals(int.class)) {
                            input = new Spinner<Integer>(Integer.MIN_VALUE, Integer.MAX_VALUE,
                                    (Integer) field.get(cfg));
                            ((Spinner<?>) input).setEditable(true);
                        }
                        if (field.getType().equals(Double.class) ||
                                field.getType().equals(double.class)) {
                            input = new Spinner<Double>(Double.MIN_VALUE, Double.MAX_VALUE, (Double) field.get(cfg));
                            ((Spinner<?>) input).setEditable(true);
                        }
                        if (field.getType().equals(Boolean.class) ||
                                field.getType().equals(boolean.class)) {
                            input = new CheckBox("");
                            ((CheckBox) input).setSelected(((Boolean) field.get(cfg)));
                        }
                        if (input != null) inputs.put(id, input);
                    }
                }
            }
        } catch (IllegalAccessException e) { return; }

        int line = 0;
        for (String key : inputs.keySet()) {
            content.addRow(
                    line,
                    new Label(Utils.idToHumanReadableName(key)),
                    inputs.get(key)
            );
            line++;
        }

        AnchorPane.setTopAnchor(content, 10D);
        AnchorPane.setLeftAnchor(content, 24D);
        AnchorPane.setRightAnchor(content, 24D);
        AnchorPane.setBottomAnchor(content, 10D);
        anchorPane.getChildren().add(content);

        setContent(anchorPane);
    }

}
