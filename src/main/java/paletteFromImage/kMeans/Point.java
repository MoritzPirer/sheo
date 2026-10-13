package paletteFromImage.kMeans;

/**
 * represents a point in 3-dimensional space
 */
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

    public Point multiply(float factor) {
        return new Point(
            (int) (x * factor),
            (int) (y * factor),
            (int) (z * factor)
        );
    }

    double calculateDistanceTo(int x, int y, int z) {
        int delta_x = x - this.x;
        int delta_y = y - this.y;
        int delta_z = z - this.z;

        return Math.sqrt(
            delta_x * delta_x
            + delta_y * delta_y
            + delta_z * delta_z
        );
    }
}