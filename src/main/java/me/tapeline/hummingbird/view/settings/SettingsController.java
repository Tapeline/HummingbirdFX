package me.tapeline.hummingbird.view.settings;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import me.tapeline.hummingbird.Main;
import me.tapeline.hummingbird.configuration.Config;
import me.tapeline.hummingbird.configuration.Configuration;
import me.tapeline.hummingbird.configuration.Configure;
import me.tapeline.hummingbird.ui.settings.tabs.SettingsTab;
import me.tapeline.hummingbird.utils.Utils;

import java.lang.reflect.Field;

public class SettingsController {

    @FXML
    public TabPane settingsTabs;

    @FXML
    public Button cancelButton;

    @FXML
    public Button okButton;

    public Configuration cfg;
    public SettingsStage stage;


    public SettingsController(Configuration cfg, SettingsStage stage) {
        this.cfg = cfg;
        this.stage = stage;
    }

    public void fillInSettings() {
        okButton.setOnAction(actionEvent -> {
            applySettings();
            stage.close();
        });

        cancelButton.setOnAction(actionEvent -> {
            stage.close();
        });

        for (String section : Configure.getSections(cfg)) {
            SettingsTab t = new SettingsTab(section, cfg);
            settingsTabs.getTabs().add(t);
        }
    }

    public void applySettings() {
        try {
            for (Tab t : settingsTabs.getTabs()) {
                SettingsTab tab = (SettingsTab) t;
                Field[] fields = cfg.getClass().getFields();
                for (Field field : fields) {
                    Config configAnnotation = field.getAnnotation(Config.class);
                    if (configAnnotation != null) {
                        String prefix = configAnnotation.section();
                        if (!tab.section.equals(prefix)) continue;
                        if (tab.inputs.containsKey(field.getName())) {
                            Object val = configAnnotation.configurationField().equals("")?
                                    tab.getInput(field.getName()) :
                                    tab.getInput(configAnnotation.configurationField());
                            if (val != null)
                                field.set(cfg, val);
                        }
                    }
                }
            }
        } catch (IllegalAccessException ignored) {}
    }

}
