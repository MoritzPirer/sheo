package paletteFromImage;

import paletteFromImage.kMeans.KMeansPaletteExtractor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TempMain {
    public static void main(String[] args) {
        File file = new File("/Users/moritzpirer/OneDrive/__Personal/Miscellaneous/Your Name Wallpaper.jpg");

        try {
            BufferedImage image = ImageIO.read(file);
            ImagePaletteExtractor extractor = new KMeansPaletteExtractor();
            Color[] result = extractor.extractFromImage(image, 5);

            for (Color color : result) {
                int value = color.getRGB() & 0xFFFFFF;
                String ansi = "\u001B[48;2;%d;%d;%dm".formatted(color.getRed(), color.getGreen(), color.getBlue());
                System.out.println(ansi + Integer.toHexString(value) +"\u001B[0m");
            }
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
