package paletteFromImage.kMeans;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class RandomKMeansCenterInitializer implements KMeansCenterInitializer {
    private final Random random = new Random();

    @Override
    public List<Point> initialize(int numberOfClusters, List<Point> points) {
        List<Point> centers = new ArrayList<>(numberOfClusters);

        while (centers.size() < numberOfClusters) {
            Point point = points.get(random.nextInt(points.size()));
            if (centers.contains(point)) {
                continue;
            }

            centers.add(point);
        }

        return centers;
    }
}
