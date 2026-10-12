package paletteFromImage;

import paletteFromImage.kMeans.KMeansPaletteExtractor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

public class TempMain {
    public static void main(String[] args) {
        Path path = Path.of("/Users/moritzpirer/OneDrive/__Personal/Miscellaneous/397388.jpg");
        try {
            BufferedImage image = ImageLoader.load(path);
            ImagePaletteExtractor extractor = new KMeansPaletteExtractor();
            List<Color> result = extractor.extractFromImage(image, 5);
            for (Color color : result) {
                int value = color.getRGB() & 0xFFFFFF;
                System.out.println(Integer.toHexString(value));
            }
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.gc();

        //Scanner scanner = new Scanner(System.in);
        //scanner.nextLine();
    }
}
