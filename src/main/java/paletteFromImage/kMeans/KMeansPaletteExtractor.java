package paletteFromImage.kMeans;

import paletteFromImage.ImagePaletteExtractor;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class KMeansPaletteExtractor implements ImagePaletteExtractor {
    private static final int MAX_ITERATIONS = 10;

    @Override
    public List<Color> extractFromImage(BufferedImage image, int count) {
        validateInputs(image, count);

        List<Point> points = convertToPoints(image);
        KMeans kMeans = new KMeans(points, MAX_ITERATIONS, new RandomKMeansCenterInitializer());
        List<Point> result = kMeans.findClusters(count);
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

    private List<Point> convertToPoints(BufferedImage image) {
        List<Point> points = new ArrayList<>(image.getWidth() * image.getHeight());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                points.add(intToPoint(image.getRGB(x, y)));
            }
        }

        return points;
    }

    private List<Color> convertToColors(List<Point> points) {
        return points.stream()
            .map(point -> new Color(point.x(), point.y(), point.y()))
            .toList();
    }

    private Point intToPoint(int rgb) {
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = (rgb) & 0xFF;

        return new Point(red, green, blue);
    }
}
