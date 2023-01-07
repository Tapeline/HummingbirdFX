package me.tapeline.hummingbird.ui.editor.tabs;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.resources.Fonts;
import me.tapeline.hummingbird.view.editor.EditorStage;

import java.io.File;

public class PlainEditorTab extends AbstractEditorTab implements AssignableToFileTab {

    public File file;
    public TextArea area;

    public PlainEditorTab(File file, String content) {
        this.file = file;
        TextArea textArea = new TextArea();
        textArea.setText(content);
        setContent(textArea);
        textArea.setFont(Font.font(App.cfg.fontName, App.cfg.fontSize));
        this.area = textArea;
        setOnCloseRequest((e) -> {
            FS.writeFile(file, textArea.getText());
        });
    }

    @Override
    public void save(EditorStage stage) throws Exception {
        FS.writeFile(file, area.getText());
    }

    @Override
    public void scrollTo(int ch) {
        area.positionCaret(ch);
    }

    @Override
    public File getFile() {
        return file;
    }
}
