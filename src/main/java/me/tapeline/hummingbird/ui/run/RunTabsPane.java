package me.tapeline.hummingbird.ui.run;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;
import me.tapeline.hummingbird.expansions.themes.AbstractColorSet;
import me.tapeline.hummingbird.expansions.themes.AbstractTheme;
import me.tapeline.hummingbird.utils.Convert;

public class RunTabsPane extends TabPane {

    public TerminalBuilder terminalBuilder = new TerminalBuilder();

    public RunTabsPane() {
        setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    public void runInNewTab(String name, String cmd) {
        TerminalTab terminal = terminalBuilder.newTerminal();
        AbstractTheme theme = Registry.getCurrentTheme();
        AbstractColorSet colors = theme.colors();
        AbstractColorScheme scheme = theme.scheme();
        terminal.getTerminalConfig().setBackgroundColor(Convert.color(colors.backgroundText));
        terminal.getTerminalConfig().setFontFamily(App.cfg.fontName);
        terminal.getTerminalConfig().setFontSize(App.cfg.fontSize);
        terminal.getTerminalConfig().setForegroundColor(Convert.color(scheme.regular.foreground));
        terminal.getTerminalConfig().setCursorBlink(true);
        terminal.getTerminalConfig().setScrollWhellMoveMultiplier(App.cfg.scrollMultiplier);
        terminal.setText(name);
        getTabs().add(terminal);
        terminal.setOnCloseRequest(event -> {});
        terminal.onTerminalFxReady(() -> terminal.getTerminal().command(cmd + "\n"));
        getSelectionModel().select(terminal);
    }

    public void destroyCurrent() {
        Tab sel = getSelectionModel().getSelectedItem();
        if (sel instanceof TerminalTab) {
            ((TerminalTab) sel).getProcess().destroy();
            ((TerminalTab) sel).destroy();
            getTabs().remove(sel);
        }
    }

    public void destroyAll() {
        for (Tab sel : getTabs().subList(0, getTabs().size()))
            if (sel instanceof TerminalTab) {
                ((TerminalTab) sel).getProcess().destroy();
                ((TerminalTab) sel).destroy();
                getTabs().remove(sel);
            }
    }
}
