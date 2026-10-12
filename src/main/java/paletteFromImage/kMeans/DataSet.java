package paletteFromImage.kMeans;

class DataSet {
    private int[] x;
    private int[] y;
    private int[] z;

    public DataSet(int count) {
        x = new int[count];
        y = new int[count];
        z = new int[count];
    }

    public void setPoint(int index, int x, int y, int z) {
        this.x[index] = x;
        this.y[index] = y;
        this.z[index] = z;
    }

    public int getX(int index) {
        return x[index];
    }

    public int getY(int index) {
        return y[index];
    }

    public int getZ(int index) {
        return z[index];
    }
}
