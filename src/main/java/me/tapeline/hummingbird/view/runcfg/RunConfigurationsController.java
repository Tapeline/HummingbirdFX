package me.tapeline.hummingbird.view.runcfg;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.expansions.runcfg.RunConfiguration;

import java.util.List;

public class RunConfigurationsController {

    @FXML
    public Button addButton;

    @FXML
    public Button removeButton;

    @FXML
    public TextField confName;

    @FXML
    public TextArea confCmd;

    @FXML
    public Button okButton;

    @FXML
    public Button cancelButton;

    @FXML
    public ListView confs;

    public RunConfigurationsStage stage;

    public RunConfigurationsController(RunConfigurationsStage stage) {
        this.stage = stage;
    }

    public void loadConfigurations(List<RunConfiguration> configurations) {
        for (RunConfiguration configuration : configurations) {
            confs.getItems().add(configuration.name);
        }
    }

}
