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
import me.tapeline.hummingbird.expansions.filetype.AbstractFileType;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.filesystem.project.Project;
import me.tapeline.hummingbird.resources.Icons;
import me.tapeline.hummingbird.ui.editor.tabs.AbstractEditorTab;
import me.tapeline.hummingbird.ui.editor.tabs.PlainEditorTab;
import me.tapeline.hummingbird.ui.filetree.FileTreeCell;
import me.tapeline.hummingbird.ui.filetree.FileTreeItem;
import me.tapeline.hummingbird.ui.terminal.TerminalTabsPane;
import me.tapeline.hummingbird.view.common.Dialogs;
import org.controlsfx.control.BreadCrumbBar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
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
                AbstractFileType fileType = FS.getTypeForFile(file);
                if (fileType != null) {
                    if (fileType.doCustomOpen(file))
                        fileType.customOpen(editor, file);
                    else {
                        String content = FS.readFile(file);
                        openTab(new PlainEditorTab(file, content), file.getName(), fileType.icon);
                    }
                } else {
                    Dialogs.error("Opening",
                            "Cannot open such file",
                            "Cannot open this file in Hummingbird");
                }
            }
        }
    }

}
