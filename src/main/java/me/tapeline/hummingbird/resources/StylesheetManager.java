package me.tapeline.hummingbird.resources;

import javafx.scene.Parent;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.filesystem.FS;
import me.tapeline.hummingbird.ui.highlightedarea.SyntaxCheckingThread;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class StylesheetManager {

    public static List<Stylesheet> stylesheets = new ArrayList<>();

    public static void buildStylesheet(String builtSheetPath) {
        try {
            StringBuilder sb = new StringBuilder("/*Do not edit! Built with StylesheetManager*/\n\n\n");
            for (Stylesheet s : stylesheets) {
                if (s.type == Stylesheet.Type.FILE) {
                    String path;
                    if (s.content.startsWith("$pkg$")) {
                        path = App.class.getResource(s.content.substring(5)).toURI().getPath();
                    } else if (s.content.startsWith("$cwd$")) {
                        path = new File(s.content.substring(5)).getAbsolutePath();
                    } else {
                        path = s.content;
                    }
                    String content = FS.readFile(path);
                    sb.append("\n/* Imported from ").append(path).append(" */\n").append(content).append("\n");
                } else if (s.type == Stylesheet.Type.RAW) {
                    sb.append("\n/* Raw import */\n").append(s.content).append("\n");
                }
            }
            FS.writeFile(builtSheetPath, sb.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void applyStylesheet(String builtSheetPath, Parent parent) {
        buildStylesheet(builtSheetPath);
        parent.getStylesheets().add(builtSheetPath);
    }

    public static void forceStylesheet(String builtSheetPath, Parent parent) {
        buildStylesheet(builtSheetPath);
        parent.getStylesheets().clear();
        parent.getStylesheets().add(builtSheetPath);
    }

}
