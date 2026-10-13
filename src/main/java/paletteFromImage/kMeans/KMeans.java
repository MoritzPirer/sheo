package paletteFromImage.kMeans;

import java.util.Arrays;

class KMeans {
    private final DataSet points;
    private final int maxIterations;

    private final KMeansCenterInitializer initializer;

    public KMeans(DataSet points, int maxIterations, KMeansCenterInitializer initializer) {
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

    private Center[] initializeCenters(int numberOfClusters, DataSet points) {
        return Arrays.stream(initializer.initialize(numberOfClusters, points))
            .map(Center::new)
            .toArray(Center[]::new);
    }

    private void assignPointsToCenters(Center[] centers) {
        for (Center center : centers) {
            center.clear();
        }

        for (int i = 0; i < points.size(); i++) {
            Center center = findClosestCenter(i, centers);
            center.addChild(points.getX(i), points.getY(i), points.getZ(i));
        }
    }

    private Center findClosestCenter(int pointIndex, Center[] centers) {
        Center candidate = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Center center : centers) {
            double distance = center.getPosition().calculateDistanceTo(
                points.getX(pointIndex),
                points.getY(pointIndex),
                points.getZ(pointIndex)
            );

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
