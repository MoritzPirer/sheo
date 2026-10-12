package paletteFromImage.kMeans;

import java.util.ArrayList;
import java.util.List;

class Center {
    private Point position;
    private final List<Point> children = new ArrayList<>();

    public Center(Point position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Center center = (Center) o;
        return position.equals(center.position);
    }

    public Point getPosition() {
        return position;
    }

    public void add(Point point) {
        children.add(point);
    }

    public void clear() {
        children.clear();
    }

    public void recenter() {
        Point newPosition = new Point(0, 0, 0);

        for (Point child : children) {
            newPosition = Point.sum(newPosition, child);
        }

        position = newPosition.multiply(1.0f / children.size());
    }
}
