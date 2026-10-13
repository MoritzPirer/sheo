package paletteFromImage.kMeans;

interface KMeansCenterInitializer {
    /**
     * Select a subset of numberOfClusters distinct points from points to be used as the
     * initial points of a K-Means algorithm
     * @param numberOfClusters how many clusters to pick
     * @param points the points to choose from
     * @return the points to start the K-Means algorithm with
     */
    Point[] initialize(int numberOfClusters, DataSet points);
}
