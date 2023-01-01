package me.tapeline.hummingbird.view.editor;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Callback;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.expansions.syntaxchecker.AbstractSyntaxChecker;
import me.tapeline.hummingbird.expansions.syntaxchecker.AutoFixAction;
import me.tapeline.hummingbird.expansions.syntaxchecker.SyntaxTip;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.editor.tabs.AbstractEditorTab;
import me.tapeline.hummingbird.ui.editor.tabs.PlainEditorTab;
import me.tapeline.hummingbird.ui.editor.tabs.codeeditor.CodeEditorTab;
import me.tapeline.hummingbird.ui.filetree.FileTreeCell;
import me.tapeline.hummingbird.ui.filetree.FileTreeItem;
import me.tapeline.hummingbird.ui.terminal.TerminalTabsPane;
import me.tapeline.hummingbird.utils.Convert;
import me.tapeline.hummingbird.view.common.Dialogs;
import org.apache.commons.io.FileUtils;
import org.controlsfx.control.BreadCrumbBar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

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


    public BreadCrumbBar<String> pathCrumb;

    public boolean termHidden = false;
    public double lastMaxTerminalTabsHeight;
    public double lastTerminalTabsHeight;

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

        TerminalTabsPane ttp = new TerminalTabsPane();
        terminalTab.setContent(ttp);
        ttp.runInNewTab("ls\n");

        hideTermButton.setOnAction(actionEvent -> {
            termHidden = !termHidden;
            /*if (termHidden) {
                lastMaxTerminalTabsHeight = terminalTabs.getMaxHeight();
                lastTerminalTabsHeight = terminalSplitter.getDividerPositions()[0];
                terminalTabs.setMaxHeight(24);
            } else {
                terminalTabs.setMaxHeight(lastMaxTerminalTabsHeight);
                terminalSplitter.setDividerPosition(0, lastTerminalTabsHeight);
            }*/
            if (termHidden) {
                lastMaxTerminalTabsHeight = terminalTabsContainer.getMaxHeight();
                lastTerminalTabsHeight = terminalSplitter.getDividerPositions()[0];
                terminalTabsContainer.setMaxHeight(24);
            } else {
                terminalTabsContainer.setMaxHeight(lastMaxTerminalTabsHeight);
                terminalSplitter.setDividerPosition(0, lastTerminalTabsHeight);
            }
        });

        fileTree.setOnMouseClicked(event -> {
            //rebuildFileTree(editor.project, editor.project.root);
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
                        //openTab(new CodeEditorTab(tip.file, FS.readFile(tip.file), false),
                                //"Quick Fix Go-To", Icons.cog);
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
