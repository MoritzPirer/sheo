package paletteFromImage.kMeans;

import java.util.Arrays;

class KMeans {
    private final Point[] points;
    private final int maxIterations;

    private final KMeansCenterInitializer initializer;

    public KMeans(Point[] points, int maxIterations, KMeansCenterInitializer initializer) {
       this.points = points;
       this.maxIterations = maxIterations;
       this.initializer = initializer;
    }

    public Point[] findClusters(int numberOfClusters) {
        Center[] centers = initializeCenters(numberOfClusters, points);

        for (int i = 0; i < maxIterations; i++) {
            assignPointsToCenters(centers);
            adjustCenters(centers);
        }

        return convertToPoints(centers);
    }

    private Center[] initializeCenters(int numberOfClusters, Point[] points) {
        return Arrays.stream(initializer.initialize(numberOfClusters, points))
            .map(Center::new)
            .toArray(Center[]::new);
        /*
        1. select first center uniformly at random
        2. for remaining centers:
            2.1. compute distance between every point and its nearest center
            2.2. pick random point with probability proportional to square of distance
         */
    }

    private void assignPointsToCenters(Center[] centers) {
        for (Center center : centers) {
            center.clear();
        }

        for (Point point : points) {
            Center center = findClosestCenter(point, centers);
            center.add(point);
        }
    }

    private Center findClosestCenter(Point point, Center[] centers) {
        Center candidate = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Center center : centers) {
            double distance = point.calculateDistanceTo(center.getPosition());
            if (distance < minDistance) {
                candidate = center;
                minDistance = distance;
            }
        }

        return candidate;
    }

    private void adjustCenters(Center[] centers) {
        for (Center center : centers) {
            center.recenter();
        }
    }

    private Point[] convertToPoints(Center[] centers) {
        return Arrays.stream(centers)
            .map(Center::getPosition)
            .toArray(Point[]::new);
    }
}
