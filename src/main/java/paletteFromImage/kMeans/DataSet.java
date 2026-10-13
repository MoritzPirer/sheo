package paletteFromImage.kMeans;

import dataStructures.DynamicByteArray;

class DataSet {
    private final DynamicByteArray x;
    private final DynamicByteArray y;
    private final DynamicByteArray z;

    public DataSet() {
        x = new DynamicByteArray();
        y = new DynamicByteArray();
        z = new DynamicByteArray();
    }

    public DataSet(int count) {
        this.x = new DynamicByteArray(count);
        this.y = new DynamicByteArray(count);
        this.z = new DynamicByteArray(count);
    }

    public void setPoint(int index, byte x, byte y, byte z) {
        this.x.set(index, x);
        this.y.set(index, y);
        this.z.set(index, z);
    }

    public void addPoint(byte x, byte y, byte z) {
        this.x.add(x);
        this.y.add(y);
        this.z.add(z);
    }

    public byte getX(int index) {
        return x.get(index);
    }

    public byte getY(int index) {
        return y.get(index);
    }

    public byte getZ(int index) {
        return z.get(index);
    }

    public int size() {
        return x.size();
    }

    public void clear() {
        x.clear();
        y.clear();
        z.clear();
    }

    public static int adjustByteValue(byte value) {
        if (value < 0) {
            return 256 + value;
        }
        return value;
    }
}
