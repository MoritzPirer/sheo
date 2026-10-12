package paletteFromImage;

import java.awt.image.BufferedImage;
import java.util.List;
import java.awt.Color;

public interface ImagePaletteExtractor {

    /**
     * Creates a palette of count colors to represent the colors found in image. The returned colors may not
     * be present in the image itself (e.g. because it is the middle between two common colors).
     * @param image the image to extract the palette from.
     * @param count how many colors to extract. Must be greater than zero and less than or equal to
     *              the number of pixels in the image
     * @return an array of count colors
     */
    Color[] extractFromImage(BufferedImage image, int count);
}
