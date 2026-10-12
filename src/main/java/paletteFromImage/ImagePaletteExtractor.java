package paletteFromImage;

import java.awt.image.BufferedImage;
import java.util.List;
import java.awt.Color;

public interface ImagePaletteExtractor {
    List<Color> extractFromImage(BufferedImage image, int count);
}
