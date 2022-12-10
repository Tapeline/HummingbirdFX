package me.tapeline.hummingbird.resources;

import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.URL;
import javax.imageio.ImageIO;
import me.tapeline.hummingbird.Main;

public class IconLoader {

    public static Exception loadIcons(String folder) {
        try {
            Field[] var1 = Icons.class.getDeclaredFields();
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
                Field field = var1[var3];
                if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(BufferedImage.class)) {
                    URL url = Main.class.getResource(folder + "/" + field.getName() + ".png");
                    if (url == null) {
                        return new Exception(folder + "/" + field.getName() + ".png: icon not found");
                    }

                    field.set(Icons.class, ImageIO.read(url));
                }
            }

            return null;
        } catch (Exception var6) {
            return var6;
        }
    }

}