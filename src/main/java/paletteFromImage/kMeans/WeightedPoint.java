package paletteFromImage.kMeans;

class WeightedPoint {
    private final Point point;
    private int weight;

    public WeightedPoint(Point point, int weight) {
        this.point = point;
        this.weight = weight;
    }

    public void incrementWeight() {
        this.weight += 1;
    }

    public int getWeight() {
        return weight;
    }

    public Point getPoint() {
        return point;
    }
}
