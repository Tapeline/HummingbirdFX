package me.tapeline.hummingbird.view.runcfg;

import javafx.fxml.FXML;
import javafx.scene.Parent;
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
    public ListView<RunConfiguration> confs;

    public RunConfiguration nowEditing = null;
    public List<RunConfiguration> configurationList;
    public RunConfigurationsStage stage;

    public RunConfigurationsController(RunConfigurationsStage stage) {
        this.stage = stage;
    }

    public void loadConfigurations(List<RunConfiguration> configurations) {
        configurationList = configurations;
        for (RunConfiguration configuration : configurations) {
            confs.getItems().add(configuration);
        }
    }

    public void setupItems() {
        addButton.setOnAction(actionEvent -> {
            confs.getItems().add(new RunConfiguration());
        });

        removeButton.setOnAction(actionEvent -> {
            if (confs.getSelectionModel().getSelectedItem() != null) {
                confs.getItems().remove(confs.getSelectionModel().getSelectedItem());
                confName.setEditable(false);
                confCmd.setEditable(false);
                nowEditing = null;
            }
        });

        confs.getSelectionModel().selectedItemProperty().addListener(observable -> {
            if (confs.getSelectionModel().getSelectedItem() != null) {
                RunConfiguration item = confs.getSelectionModel().getSelectedItem();
                confName.setText(item.name);
                confName.setEditable(true);
                confCmd.setText(item.command);
                confCmd.setEditable(true);
                nowEditing = item;
            } else nowEditing = null;
        });

        confName.textProperty().addListener(observable -> {
            if (nowEditing != null)
                nowEditing.name = confName.getText();
            confs.refresh();
        });

        confCmd.textProperty().addListener(observable -> {
            if (nowEditing != null)
                nowEditing.command = confCmd.getText();
        });

        okButton.setOnAction(actionEvent -> {
            configurationList.clear();
            configurationList.addAll(confs.getItems());
            stage.close();
            stage.editorController.refreshRunConfigurations();
        });

        cancelButton.setOnAction(actionEvent -> {
            stage.close();
        });
    }


}
