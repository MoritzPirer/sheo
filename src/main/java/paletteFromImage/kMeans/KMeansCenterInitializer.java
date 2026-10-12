package paletteFromImage.kMeans;

import java.util.List;

interface KMeansCenterInitializer {
    List<Point> initialize(int numberOfClusters, List<Point> points);
}
