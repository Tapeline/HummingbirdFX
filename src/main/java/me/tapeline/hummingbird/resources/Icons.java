package me.tapeline.hummingbird.resources;

import java.awt.image.BufferedImage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Icons {

    public static BufferedImage splash;
    public static BufferedImage cog;
    public static BufferedImage file;
    public static BufferedImage iconCross;
    public static BufferedImage folder;
    public static BufferedImage iconCrossHover;
    public static BufferedImage settings;
    public static BufferedImage update;

    public static Image convertToFxImage(BufferedImage image) {
        WritableImage wr = null;
        if (image != null) {
            wr = new WritableImage(image.getWidth(), image.getHeight());
            PixelWriter pw = wr.getPixelWriter();

            for(int x = 0; x < image.getWidth(); ++x) {
                for(int y = 0; y < image.getHeight(); ++y) {
                    pw.setArgb(x, y, image.getRGB(x, y));
                }
            }
        }

        return (new ImageView(wr)).getImage();
    }

}