package paletteFromImage.kMeans;

import paletteFromImage.ImagePaletteExtractor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Treats each pixel of the image as a vector in 3D space (with the axis corresponding to red, green and blue),
 * then runs a K-Means algorithm over the dataset to identify clusters with similar colors to be used as the palette.
 */
public class KMeansPaletteExtractor implements ImagePaletteExtractor {
    private static final int MAX_ITERATIONS = 10;

    @Override
    public Color[] extractFromImage(BufferedImage image, int count) {
        validateInputs(image, count);

        Point[] points = convertToPoints(image);
        KMeans kMeans = new KMeans(points, MAX_ITERATIONS, new RandomKMeansCenterInitializer());
        Point[] result = kMeans.findClusters(count);

        return convertToColors(result);
    }

    private void validateInputs(BufferedImage image, int count) {
        if (image == null) {
            throw new IllegalArgumentException("image must not be null");
        }
        if (image.getHeight() <= 0 || image.getWidth() <= 0) {
            throw new IllegalArgumentException("image has invalid dimensions");
        }
        if (count <= 0) {
            throw new IllegalArgumentException("count must be positive");
        }
        if (count > image.getHeight() * image.getWidth()) {
            throw new IllegalArgumentException("image is too small for selected count");
        }
    }

    /**
     * converts each pixel into a point
     * @param image the image to convert
     * @return an array of Points
     */
    private Point[] convertToPoints(BufferedImage image) {
        Point[] points = new Point[image.getWidth() * image.getHeight()];

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int index = y * image.getWidth() + x;
                points[index] = intToPoint(image.getRGB(x, y));
            }
        }

        return points;
    }

    private Color[] convertToColors(Point[] points) {
        return Arrays.stream(points)
            .map(point -> new Color(point.x(), point.y(), point.z()))
            .toArray(Color[]::new);
    }

    private Point intToPoint(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = (rgb) & 0xFF;

        return new Point(red, green, blue);
    }
}
