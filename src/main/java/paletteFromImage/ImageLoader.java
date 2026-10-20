package paletteFromImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ImageLoader {
    public static BufferedImage load(Path path) throws IOException {
        File file = path.toFile();
        return ImageIO.read(file);
    }
}
