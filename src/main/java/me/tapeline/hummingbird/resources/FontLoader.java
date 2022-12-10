package me.tapeline.hummingbird.resources;

import java.awt.Font;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import me.tapeline.hummingbird.App;
import me.tapeline.hummingbird.Main;

public class FontLoader {

    public static Exception loadFonts(String folder) {
        try {
            Field[] var1 = Fonts.class.getDeclaredFields();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Field field = var1[var3];
                if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(Font.class)) {
                    InputStream fontStream = Main.class.getResourceAsStream(folder + "/" + field.getName() + ".ttf");
                    if (fontStream == null) {
                        return new Exception(folder + "/" + field.getName() + ".ttf: font not found");
                    }

                    Font f = Font.createFont(0, fontStream);
                    f = f.deriveFont(0, (float)App.cfg.fontSize);
                    field.set(Fonts.class, f);
                }
            }

            return null;
        } catch (Exception var7) {
            return var7;
        }
    }

    public static Exception loadFxFonts(String folder) {
        try {
            Field[] var1 = Fonts.class.getDeclaredFields();
            int var2 = var1.length;

            for (int var3 = 0; var3 < var2; ++var3) {
                Field field = var1[var3];
                if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(javafx.scene.text.Font.class)) {

                    javafx.scene.text.Font f = javafx.scene.text.Font.loadFont(
                            App.class.getResourceAsStream(folder + "/" + field.getName() + ".ttf"),
                            App.cfg.fontSize
                    );
                    field.set(Fonts.class, f);
                }
            }

            return null;
        } catch (Exception var7) {
            return var7;
        }
    }

}
