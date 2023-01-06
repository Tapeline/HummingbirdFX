package me.tapeline.hummingbird.view.about;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import me.tapeline.hummingbird.configuration.Config;
import me.tapeline.hummingbird.configuration.Configuration;
import me.tapeline.hummingbird.configuration.Configure;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.settings.tabs.SettingsTab;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.view.settings.SettingsStage;

import java.lang.reflect.Field;

public class AboutController {

    @FXML
    public Label heading;

    public void setup() {
        heading.setGraphic(new ImageView(Convert.image(Icons.icon64)));
    }

}
