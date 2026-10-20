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
    private static final int STEP_SIZE = 4;

    @Override
    public Color[] extractFromImage(BufferedImage image, int count) {
        validateInputs(image, count);

        DataSet points = convertToPoints(image);
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

    private DataSet convertToPoints(BufferedImage image) {
        int effectiveHeight = image.getHeight() / STEP_SIZE;
        int effectiveWidth = image.getWidth() / STEP_SIZE;
        DataSet dataSet = new DataSet(effectiveWidth * effectiveHeight);

        int index = 0;
        for (int y = 0; y < image.getHeight(); y += STEP_SIZE) {
            for (int x = 0; x < image.getWidth(); x += STEP_SIZE) {
                int rgb = image.getRGB(x, y);

                dataSet.setPoint(index, getRed(rgb), getGreen(rgb), getBlue(rgb));
                index++;
            }
        }

        return dataSet;
    }

    private byte getRed(int rgb) {
        return (byte) ((rgb >> 16) & 0xFF);
    }

    private byte getGreen(int rgb) {
        return (byte) ((rgb >> 8) & 0xFF);
    }

    private byte getBlue(int rgb) {
        return (byte) (rgb & 0xFF);
    }

    private Color[] convertToColors(Point[] points) {
        return Arrays.stream(points)
            .map(point -> new Color(point.x(), point.y(), point.z()))
            .toArray(Color[]::new);
    }
}
