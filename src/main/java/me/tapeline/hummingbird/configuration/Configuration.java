package me.tapeline.hummingbird.configuration;

import java.util.ArrayList;
import java.util.List;
import me.tapeline.hummingbird.expansions.runcfg.RunConfiguration;

public class Configuration {

    @Config
    public String pluginFolder = "plugins";

    @Config
    public String projectFolder = "projects";

    @Config(section = "debugOptions")
    public boolean allowDebug = false;

    @Config(section = "appearance")
    public String theme = "Darcula";

    @Config(section = "appearance")
    public String fontName = "Monospaced";

    @Config(section = "appearance")
    public int fontSize = 13;

    @Config(section = "codeEditing")
    public int tabSizeInSpaces = 4;

    @Config(section = "terminal")
    public double scrollMultiplier = 1.8;

    @Config(showInSettings = false)
    public List<String> enabledPlugins = new ArrayList();

    @Config(showInSettings = false)
    public List<String> lastOpened = new ArrayList();

    @Config(showInSettings = false)
    public List<RunConfiguration> runConfigurations = new ArrayList();

}
