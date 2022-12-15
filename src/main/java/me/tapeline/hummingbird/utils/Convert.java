package me.tapeline.hummingbird.utils;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import me.tapeline.hummingbird.resources.Icons;

import java.awt.image.BufferedImage;

public class Convert {

    public static Color color(java.awt.Color awtColor) {
        int r = awtColor.getRed();
        int g = awtColor.getGreen();
        int b = awtColor.getBlue();
        int a = awtColor.getAlpha();
        double opacity = a / 255.0 ;
        return Color.rgb(r, g, b, opacity);
    }

    public static Image image(BufferedImage img) {
        return Icons.convertToFxImage(img);
    }

    public static String hexColor(java.awt.Color color) {
        return String.format("#%02X%02X%02X%02X",
                color.getRed(),
                color.getGreen(),
                color.getBlue(),
                color.getAlpha());
    }

}
