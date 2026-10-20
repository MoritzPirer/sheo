package paletteFromImage.kMeans;

class Center {
    private Point position;
    private final DataSet children = new DataSet();

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

    public void addChild(byte x, byte y, byte z) {
        children.addPoint(x, y, z);
    }

    public void clear() {
        children.clear();
    }

    public void recenter() {
        int x = 0, y = 0, z = 0;

        if (children.size() == 0) {
            return; // avoids centers to move to (0,0,0)
        }

        for (int i = 0; i < children.size(); i++) {
            x += children.getXasInt(i);
            y += children.getYasInt(i);
            z += children.getZasInt(i);
        }

        position = new Point(x, y, z).multiply(1.0f / children.size());
    }
}
