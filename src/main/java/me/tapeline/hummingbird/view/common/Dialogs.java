package me.tapeline.hummingbird.view.common;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import me.tapeline.hummingbird.App;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public class Dialogs {

    public static void info(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        App.applyCurrentThemeToDialog(alert.getDialogPane());
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void error(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        App.applyCurrentThemeToDialog(alert.getDialogPane());
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void warn(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        App.applyCurrentThemeToDialog(alert.getDialogPane());
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static Optional<ButtonType> confirm(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        App.applyCurrentThemeToDialog(alert.getDialogPane());
        alert.setHeaderText(header);
        alert.setTitle(title);
        alert.setContentText(content);
        return alert.showAndWait();
    }

    public static void exception(Exception ex) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        App.applyCurrentThemeToDialog(alert.getDialogPane());
        alert.setTitle("Exception");
        alert.setHeaderText("An unhandled exception occurred");
        alert.setContentText(ex.getLocalizedMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("Stack traceback:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }




}
