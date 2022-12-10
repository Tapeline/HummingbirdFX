package me.tapeline.hummingbird.ui.editor.tabs;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import jdk.jshell.spi.ExecutionControl;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.view.editor.EditorStage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.File;

public class CodeEditorTab extends AbstractEditorTab {

    public File file;
    public CodeArea area;

    public CodeEditorTab(File file, String content) {
        this.file = file;
        area = new CodeArea();
        area.replaceText(0, 0, content);
        area.setParagraphGraphicFactory(LineNumberFactory.get(area));
        setOnCloseRequest((e) -> {
            FS.writeFile(file, area.getText());
        });
    }

    @Override
    public void save(EditorStage stage) throws Exception {
        FS.writeFile(file, area.getText());
    }
}
