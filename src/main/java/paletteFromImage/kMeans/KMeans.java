package paletteFromImage.kMeans;

import java.util.List;

class KMeans {
    private final List<Point> points;
    private final int maxIterations;

    private final KMeansCenterInitializer initializer;

    public KMeans(List<Point> points, int maxIterations, KMeansCenterInitializer initializer) {
       this.points = points;
       this.maxIterations = maxIterations;
       this.initializer = initializer;
    }

    public List<Point> findClusters(int numberOfClusters) {
        List<Center> centers = initializeCenters(numberOfClusters, points);

        for (int i = 0; i < maxIterations; i++) {
            assignPointsToCenters(centers);
            adjustCenters(centers);

            System.out.println("Iteration: " + i);
            System.out.println(centers);
        }

        return convertToPoints(centers);
    }

    private List<Center> initializeCenters(int numberOfClusters, List<Point> points) {
        return initializer.initialize(numberOfClusters, points)
            .stream()
            .map(Center::new)
            .toList();
        /*
        1. select first center uniformly at random
        2. for remaining centers:
            2.1. compute distance between every point and its nearest center
            2.2. pick random point with probablity proporitonal to square of distance
         */
    }

    private void assignPointsToCenters(List<Center> centers) {
        for (Center center : centers) {
            center.clear();
        }

        for (Point point : points) {
            Center center = findClosestCenter(point, centers);
            center.add(point);
        }
    }

    private Center findClosestCenter(Point point, List<Center> centers) {
        Center candidate = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Center center : centers) {
            double distance = point.distance(center.getPosition());
            if (distance < minDistance) {
                candidate = center;
                minDistance = distance;
            }
        }

        return candidate;
    }

    private void adjustCenters(List<Center> centers) {
        for (Center center : centers) {
            center.recenter();
        }
    }

    private List<Point> convertToPoints(List<Center> centers) {
        return centers.stream()
            .map(Center::getPosition)
            .toList();
    }
}
