package me.tapeline.hummingbird.utils;

import javafx.stage.Window;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.configuration.Configure;

public class AppExitWatcher extends Thread {

    public App app;

    public AppExitWatcher(App app) {
        this.app = app;
        this.start();
    }

    public void run() {
        while (true) {
            long start = System.currentTimeMillis();

            boolean stop = true;
            for (Window frame : app.openedWindows) {
                if (frame.isShowing()) {
                    stop = false;
                    break;
                }
            }

            if (stop) {
                Configure.saveYaml(App.configPath, App.cfg);
                break;
            }

            while (start + 2000 > System.currentTimeMillis());
        }
        System.exit(0);
    }

}
