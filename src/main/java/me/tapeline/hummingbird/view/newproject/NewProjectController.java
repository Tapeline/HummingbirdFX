package me.tapeline.hummingbird.view.newproject;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.projecttype.AbstractProjectGenerator;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.wizardfields.Form;
import me.tapeline.hummingbird.ui.wizardfields.PathField;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.view.common.Dialogs;

import javax.swing.text.GapContent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class NewProjectController {

    @FXML
    public Button ok;

    @FXML
    public Button cancel;

    @FXML
    public ListView<AbstractProjectGenerator> projectTypes;

    @FXML
    public TitledPane general;

    @FXML
    public Accordion forms;

    @FXML
    public Label errorLabel;

    public AbstractProjectGenerator selectedGenerator = null;

    public NewProjectStage stage;

    public NewProjectController(NewProjectStage stage) {
        this.stage = stage;
    }

    public void setup() {
        Form generalForm = new Form(
                "general", "General",
                new ArrayList<>(Arrays.asList(
                        new PathField(
                                "Project Directory",
                                "projectDir",
                                App.cfg.projectFolder + "/untitled"
                        )
                )),
                false
        ) {
            @Override
            public void onSubmit() {}
        };
        general.setContent(generalForm);

        for (AbstractProjectGenerator gen : Registry.projectGenerators) {
            projectTypes.getItems().add(gen);
        }

        projectTypes.selectionModelProperty().addListener((o1, t1, t2) -> switchProjectGenerator());
        projectTypes.selectionModelProperty().addListener(observable -> switchProjectGenerator());
        switchProjectGenerator();

        ok.setOnAction(actionEvent -> {
            if (projectTypes.getSelectionModel().getSelectedItem() == null) {
                Dialogs.error("New Project", "No selected generator",
                        "Select a generator to create a project");
            } else {
                selectedGenerator = projectTypes.getSelectionModel().getSelectedItem();
                HashMap<String, Object> params = new HashMap<>();
                for (TitledPane pane : forms.getPanes()) {
                    if (pane.getContent() instanceof Form) {
                        ((Form) pane.getContent()).collectValuesToMap(params);
                    }
                }
                String error = selectedGenerator.validateForms(params);
                if (error == null) {
                    selectedGenerator.onCreate(params);
                    stage.close();
                } else {
                    errorLabel.setText(error);
                    errorLabel.setGraphic(new ImageView(Convert.image(Icons.cross)));
                }
            }
        });

        cancel.setOnAction(actionEvent -> {
            stage.close();
        });
    }

    public void switchProjectGenerator() {
        for (TitledPane t : forms.getPanes().subList(0, forms.getPanes().size()))
            if (t != general)
                forms.getPanes().remove(t);

        selectedGenerator = null;
        if (projectTypes.getSelectionModel().getSelectedItem() == null &&
            projectTypes.getItems().size() > 0) {
            projectTypes.getSelectionModel().select(0);
        }
        if (projectTypes.getSelectionModel().getSelectedItem() != null) {
            selectedGenerator = projectTypes.getSelectionModel().getSelectedItem();
            for (Form form : selectedGenerator.prepareForms())
                forms.getPanes().add(new TitledPane(form.label, form));
        }
    }

}
