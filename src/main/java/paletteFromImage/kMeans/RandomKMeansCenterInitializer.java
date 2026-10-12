package paletteFromImage.kMeans;

import java.util.Arrays;
import java.util.Random;

/**
 * Selects the Initial points uniformly at random
 */
class RandomKMeansCenterInitializer implements KMeansCenterInitializer {
    private final Random random = new Random();

    @Override
    public Point[] initialize(int numberOfClusters, Point[] points) {
        Point[] centers = new Point[numberOfClusters];
        int index = 0;
        while (index < numberOfClusters) {
            Point point = points[random.nextInt(points.length)];
            if (Arrays.asList(centers).contains(point)) {
                continue;
            }

            centers[index] = point;
            index++;
        }

        return centers;
    }
}
