package paletteFromImage.kMeans;

record Point(int x, int y, int z){

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point) o;
        return x == point.x && y == point.y && z == point.z;
    }

    @Override
    public String toString() {
        return "Point(" + "x=" + x + ", y=" + y + ", z=" + z + ')';
    }

    public static Point sum(Point a, Point b) {
        return new Point(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public Point multiply(float factor) {
        return new Point(
            (int) (x * factor),
            (int) (y * factor),
            (int) (z * factor)
        );
    }

    double distance(Point other) {
        int delta_x = x - other.x;
        int delta_y = y - other.y;
        int delta_z = z - other.z;

        return Math.sqrt(
            delta_x * delta_x
            + delta_y * delta_y
            + delta_z * delta_z
        );
    }
}