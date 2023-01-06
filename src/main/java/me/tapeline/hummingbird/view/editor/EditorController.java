package me.tapeline.hummingbird.view.editor;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.runcfg.RunConfiguration;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.syntaxchecker.AutoFixAction;
import me.tapeline.hummingbird.expansions.syntaxchecker.SyntaxTip;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.editor.tabs.AbstractEditorTab;
import me.tapeline.hummingbird.ui.editor.tabs.AssignableToFileTab;
import me.tapeline.hummingbird.ui.editor.tabs.codeeditor.CodeEditorTab;
import me.tapeline.hummingbird.ui.filetree.FileTreeCell;
import me.tapeline.hummingbird.ui.filetree.FileTreeItem;
import me.tapeline.hummingbird.ui.menus.editortop.*;
import me.tapeline.hummingbird.ui.run.RunTabsPane;
import me.tapeline.hummingbird.ui.terminal.TerminalTabsPane;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.view.about.AboutStage;
import me.tapeline.hummingbird.view.common.Dialogs;
import me.tapeline.hummingbird.view.runcfg.RunConfigurationsStage;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.BreadCrumbBar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collection;
import java.util.List;

public class EditorController {

    public EditorStage editor;

    @FXML
    public VBox mainPane;

    @FXML
    public MenuBar menuBar;

    @FXML
    public Pane pathBar;

    @FXML
    public ToolBar toolBar;

    @FXML
    public BorderPane workspaceBorderPane;

    @FXML
    public SplitPane terminalSplitter;

    @FXML
    public SplitPane workspaceSplitter;

    @FXML
    public TabPane projectTabs;

    @FXML
    public TabPane terminalTabs;

    @FXML
    public TabPane detailsTabs;

    @FXML
    public TabPane workspaceTabs;

    @FXML
    public Pane statusBar;

    @FXML
    public TreeView<String> fileTree;

    @FXML
    public FlowPane buttonBar;

    @FXML
    public Button hideTermButton;

    @FXML
    public AnchorPane terminalTabsContainer;

    @FXML
    public Tab terminalTab;

    @FXML
    public Tab problemsTab;

    @FXML
    public Tab todoTab;

    @FXML
    public Tab gitTab;

    @FXML
    public Tab runTab;

    @FXML
    public ComboBox<AbstractSyntaxChecker> inspectorChooser;

    @FXML
    public Button inspectButton;

    @FXML
    public ListView<SyntaxTip> projectInspectorList;

    @FXML
    public Button inspectorGoToButton;

    @FXML
    public Button inspectorFixButton;

    @FXML
    public Label inspectorProblem;

    @FXML
    public Label inspectorProblemHeader;

    @FXML
    public Button toolbarRunButton;

    @FXML
    public Button toolbarStopButton;

    @FXML
    public Button toolbarEditButton;

    @FXML
    public ComboBox<RunConfiguration> runCfgCombo;

    @FXML
    public Button aboutButton;

    public BreadCrumbBar<String> pathCrumb;

    public boolean termHidden = false;
    public double lastMaxTerminalTabsHeight;
    public double lastTerminalTabsHeight;
    public Tab lastTerminalSelected = null;
    public Tab currentTerminalSelected = null;

    public boolean projectHidden = false;
    public double lastMaxProjectTabsSplitter;
    public double lastProjectTabsSplitter;
    public Tab lastProjectSelected;
    public Tab currentProjectSelected;

    public boolean detailsHidden = false;
    public double lastMaxDetailsTabsSplitter;
    public double lastDetailsTabsSplitter;
    public Tab lastDetailsSelected;
    public Tab currentDetailsSelected;

    public TerminalTabsPane terminalTabsPane;
    public RunTabsPane runTabsPane;

    public EditorController(EditorStage editor) {
        this.editor = editor;
    }

    public void openTab(AbstractEditorTab editorTab, String name) {
        openTab(editorTab, name, null);
    }

    public void openTab(AbstractEditorTab editorTab, String name, BufferedImage icon) {
        workspaceTabs.getTabs().add(editorTab);
        editorTab.setText(name);
        workspaceTabs.getSelectionModel().select(editorTab);
        if (icon != null)
            editorTab.setGraphic(new ImageView(Icons.convertToFxImage(icon)));
    }

    public void initCustomItems(EditorStage stage) {
        pathCrumb = new BreadCrumbBar<>();
        pathBar.getChildren().add(pathCrumb);
        
        menuBar.getMenus().clear();
        menuBar.getMenus().add(new FileMenu(stage));
        menuBar.getMenus().add(new EditMenu(stage));
        menuBar.getMenus().add(new ViewMenu(stage));
        menuBar.getMenus().add(new CodeMenu(stage));
        menuBar.getMenus().add(new RefactorMenu(stage));
        menuBar.getMenus().add(new RunMenu(stage));
        menuBar.getMenus().add(new GitMenu(stage));
        menuBar.getMenus().add(new ToolsMenu(stage));
        menuBar.getMenus().add(new HelpMenu(stage));

        aboutButton.setOnAction(actionEvent -> {
            AboutStage aboutStage = new AboutStage(editor.app);
        });

        terminalTabsPane = new TerminalTabsPane();
        terminalTab.setContent(terminalTabsPane);
        terminalTabsPane.runInNewTab("ls");

        runTabsPane = new RunTabsPane();
        runTab.setContent(runTabsPane);

        toolbarRunButton.setGraphic(new ImageView(Convert.image(Icons.start)));
        toolbarStopButton.setGraphic(new ImageView(Convert.image(Icons.stop)));
        toolbarEditButton.setGraphic(new ImageView(Convert.image(Icons.edit)));

        toolbarEditButton.setOnAction(actionEvent -> {
            RunConfigurationsStage dialog = new RunConfigurationsStage(editor.app, this);
        });

        hideTermButton.setOnAction(actionEvent -> {
            termHidden = !termHidden;
            if (termHidden) {
                lastMaxTerminalTabsHeight = terminalTabsContainer.getMaxHeight();
                lastTerminalTabsHeight = terminalSplitter.getDividerPositions()[0];
                terminalTabsContainer.setMaxHeight(24);
            } else {
                terminalTabsContainer.setMaxHeight(lastMaxTerminalTabsHeight);
                terminalSplitter.setDividerPosition(0, lastTerminalTabsHeight);
            }
        });

        terminalTabs.getSelectionModel().selectedItemProperty().addListener((observableValue, oldTab, newTab) -> {
            if (!termHidden) return;
            termHidden = false;
            terminalTabsContainer.setMaxHeight(lastMaxTerminalTabsHeight);
            terminalSplitter.setDividerPosition(0, lastTerminalTabsHeight);
        });
        terminalTabs.setOnMouseClicked(observable -> {
            currentTerminalSelected = terminalTabs.getSelectionModel().getSelectedItem();
            if (lastTerminalSelected == currentTerminalSelected) {
                termHidden = !termHidden;
                if (termHidden) {
                    lastMaxTerminalTabsHeight = terminalTabsContainer.getMaxHeight();
                    lastTerminalTabsHeight = terminalSplitter.getDividerPositions()[0];
                    terminalTabsContainer.setMaxHeight(24);
                } else {
                    terminalTabsContainer.setMaxHeight(lastMaxTerminalTabsHeight);
                    terminalSplitter.setDividerPosition(0, lastTerminalTabsHeight);
                }
            }
            lastTerminalSelected = currentTerminalSelected;
        });

        projectTabs.getSelectionModel().selectedItemProperty().addListener((observableValue, tab, t1) -> {
            if (!projectHidden) return;
            projectHidden = false;
            projectTabs.setMaxWidth(lastMaxProjectTabsSplitter);
            workspaceSplitter.setDividerPosition(0, lastProjectTabsSplitter);
        });
        projectTabs.setOnMouseClicked(event -> {
            currentProjectSelected = projectTabs.getSelectionModel().getSelectedItem();
            if (lastProjectSelected == currentProjectSelected) {
                projectHidden = !projectHidden;
                if (projectHidden) {
                    lastMaxProjectTabsSplitter = projectTabs.getTabMaxWidth();
                    lastProjectTabsSplitter = workspaceSplitter.getDividerPositions()[0];
                    projectTabs.setMaxWidth(24);
                } else {
                    projectTabs.setMaxWidth(lastMaxProjectTabsSplitter);
                    workspaceSplitter.setDividerPosition(0, lastProjectTabsSplitter);
                }
            }
            lastProjectSelected = currentProjectSelected;
        });

        detailsTabs.getSelectionModel().selectedItemProperty().addListener((observableValue, tab, t1) -> {
            if (!detailsHidden) return;
            detailsHidden = false;
            detailsTabs.setMaxWidth(lastMaxDetailsTabsSplitter);
            workspaceSplitter.setDividerPosition(1, lastProjectTabsSplitter);
        });
        detailsTabs.setOnMouseClicked(event -> {
            currentDetailsSelected = detailsTabs.getSelectionModel().getSelectedItem();
            if (lastDetailsSelected == currentDetailsSelected) {
                detailsHidden = !detailsHidden;
                if (detailsHidden) {
                    lastMaxDetailsTabsSplitter = detailsTabs.getTabMaxWidth();
                    lastDetailsTabsSplitter = workspaceSplitter.getDividerPositions()[1];
                    detailsTabs.setMaxWidth(24);
                } else {
                    detailsTabs.setMaxWidth(lastMaxDetailsTabsSplitter);
                    workspaceSplitter.setDividerPosition(1, lastDetailsTabsSplitter);
                }
            }
            lastDetailsSelected = currentDetailsSelected;
        });

        for (AbstractSyntaxChecker checker : Registry.syntaxCheckers) {
            inspectorChooser.getItems().add(checker);
        }

        inspectButton.setOnAction(actionEvent -> {
            if (inspectorChooser.getSelectionModel().getSelectedItem() != null) {
                projectInspectorList.getItems().clear();
                AbstractSyntaxChecker checker = inspectorChooser.getSelectionModel().getSelectedItem();
                Collection<File> projectFiles = FileUtils.listFiles(
                        editor.project.root,
                        null,
                        true
                );
                for (File file : projectFiles) {
                    String content = FS.readFile(file);
                    if (content != null) {
                        List<SyntaxTip> tips = checker.check(content);
                        for (SyntaxTip tip : tips)
                            tip.file = file;
                        projectInspectorList.getItems().addAll(tips);
                    }
                }
            }
        });

        projectInspectorList.setOnMouseClicked(event -> {
            if (projectInspectorList.getSelectionModel().getSelectedItem() != null) {
                SyntaxTip tip = projectInspectorList.getSelectionModel().getSelectedItem();
                inspectorProblem.setText(tip.tip);
                inspectorProblemHeader.setStyle("-fx-background-color: " +
                        Convert.hexColor(tip.style().background));
                inspectorFixButton.setOnAction(actionEvent -> {
                    AutoFixAction fix = Dialogs.askChoice("Quick Fix",
                            "Select a fix to apply", "", tip.getFixes());
                    if (fix != null) {
                        fix.fix(tip.file);
                        projectInspectorList.getSelectionModel().clearSelection();
                        projectInspectorList.getItems().remove(tip);
                        inspectorProblemHeader.setStyle("");
                        inspectorProblem.setText("No selected problem");
                        inspectorFixButton.setOnAction(actionEvent1 -> {
                            Dialogs.error("Error", "No selected problem",
                                    "Select a problem to fix");
                        });
                        inspectorGoToButton.setOnAction(actionEvent1 -> {
                            Dialogs.error("Error", "No selected problem",
                                    "Select a problem to go to");
                        });
                    }
                });
                inspectorGoToButton.setOnAction(actionEvent -> {
                    if (tip.file != null) {
                        openFile(tip.file);
                    }
                });
            } else {
                inspectorProblemHeader.setStyle("");
                inspectorProblem.setText("No selected problem");
                inspectorFixButton.setOnAction(actionEvent1 -> {
                    Dialogs.error("Error", "No selected problem",
                            "Select a problem to fix");
                });
                inspectorGoToButton.setOnAction(actionEvent1 -> {
                    Dialogs.error("Error", "No selected problem",
                            "Select a problem to go to");
                });
            }
        });

        refreshRunConfigurations();

        RunConfiguration selected = runCfgCombo.getSelectionModel().getSelectedItem();
        toolbarRunButton.setDisable(selected == null);
        toolbarStopButton.setDisable(selected == null);
        runCfgCombo.getSelectionModel().selectedItemProperty().addListener((observableValue, runConfiguration, t1) -> {
            RunConfiguration sel = runCfgCombo.getSelectionModel().getSelectedItem();
            toolbarRunButton.setDisable(sel == null);
            toolbarStopButton.setDisable(sel == null);
        });

        toolbarRunButton.setOnAction(actionEvent -> runCurrentConfiguration());
        toolbarStopButton.setOnAction(actionEvent -> runTabsPane.destroyCurrent());
    }

    public void refreshRunConfigurations() {
        runCfgCombo.getItems().clear();
        runCfgCombo.getItems().addAll(App.cfg.runConfigurations);
    }

    public void runCurrentConfiguration() {
        RunConfiguration selected = runCfgCombo.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        String cmd = selected.command;
        cmd = cmd.replaceAll("%\\{projectFolder}%", editor.project.root.getAbsolutePath());
        Tab selectedTab = workspaceTabs.getSelectionModel().getSelectedItem();
        if (selectedTab instanceof AssignableToFileTab)
            cmd = cmd.replaceAll("%\\{currentFile}%",
                    ((AssignableToFileTab) selectedTab).getFile().getAbsolutePath());
        for (String key : App.cfg.mapRepresentation.keySet())
            cmd = cmd.replaceAll("%\\{cfg:" + key + "}%", App.cfg.mapRepresentation.get(key).toString());

        while (cmd.contains("%{input:str}%"))
            cmd = cmd.replaceFirst("%\\{input:str}%", Dialogs.askString(
                    "Run Preparations",
                    "Input",
                    "Run configuration requires a user inputted string",
                    ""
            ));

        while (cmd.contains("%{input:file}%")) {
            File file = Dialogs.askFile(editor);
            if (file != null)
                cmd = cmd.replaceFirst("%\\{input:file}%", file.getAbsolutePath());
        }

        while (cmd.contains("%{input:folder}%")) {
            File file = Dialogs.askFile(editor);
            if (file != null)
                cmd = cmd.replaceFirst("%\\{input:folder}%", file.getAbsolutePath());
        }

        terminalTabs.getSelectionModel().select(runTab);
        runTabsPane.runInNewTab(selected.name, cmd);
    }

    public void rebuildFileTree(Project project, File file) {
        FileTreeItem rootFileItem = new FileTreeItem(file, project);
        fileTree.setRoot(rootFileItem);
        pathCrumb.setSelectedCrumb(rootFileItem);
        fileTree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> stringTreeView) {
                return new FileTreeCell();
            }
        });
        fileTree.addEventHandler(MouseEvent.MOUSE_CLICKED, this::handleFileTreeClick);
    }

    public void handleFileTreeClick(MouseEvent event) {
        if (!fileTree.getSelectionModel().isEmpty()) {
            if (event.getClickCount() == 2) {
                pathCrumb.setSelectedCrumb(fileTree.getSelectionModel().getSelectedItem());
                File file = ((FileTreeItem) fileTree.getSelectionModel().getSelectedItem()).file;
                openFile(file);
            }
        }
    }

    public boolean fileIsAlreadyOpened(File file) {
        for (Tab tab : workspaceTabs.getTabs())
            if (tab instanceof CodeEditorTab)
                if (((CodeEditorTab) tab).file.equals(file))
                    return true;
        return false;
    }

    public void openFile(File file) {
        openFile(file, 0);
    }

    public void openFile(File file, int selectedLine) {
        // TODO: scrolling to line
        if (fileIsAlreadyOpened(file)) {
            for (Tab tab : workspaceTabs.getTabs()) {
                if (tab instanceof CodeEditorTab) {
                    if (((CodeEditorTab) tab).file.equals(file)) {
                        workspaceTabs.getSelectionModel().select(tab);
                        //((CodeEditorTab) tab).area.line
                    }
                }
            }
        } else {
            AbstractFileType fileType = FS.getTypeForFile(file);
            if (fileType != null) {
                if (fileType.doCustomOpen(file))
                    fileType.customOpen(editor, file);
                else {
                    String content = FS.readFile(file);
                    openTab(new CodeEditorTab(editor, file, content), file.getName(), fileType.icon);
                }
            } else {
                Dialogs.error("Opening",
                        "Cannot open such file",
                        "Cannot open this file in Hummingbird");
            }
        }
    }

}
