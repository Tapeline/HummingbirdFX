package me.tapeline.hummingbird.ui.terminal;

import com.kodedu.terminalfx.TerminalBuilder;
import com.kodedu.terminalfx.TerminalTab;
import com.kodedu.terminalfx.config.TabNameGenerator;
import javafx.event.EventTarget;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.expansions.Registry;
import me.tapeline.hummingbird.expansions.colorschemes.AbstractColorScheme;
import me.tapeline.hummingbird.expansions.themes.AbstractColorSet;
import me.tapeline.hummingbird.expansions.themes.AbstractTheme;
import me.tapeline.hummingbird.utils.Convert;

public class TerminalTabsPane extends TabPane {

    public TerminalBuilder terminalBuilder = new TerminalBuilder();

    public TerminalTabsPane() {
        setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
    }

    public void runInNewTab(String cmd) {
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
        getTabs().add(terminal);
        terminal.onTerminalFxReady(() -> terminal.getTerminal().command(cmd));
    }

}
