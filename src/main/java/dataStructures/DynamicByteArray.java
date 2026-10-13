package dataStructures;

public class DynamicByteArray {
    private static final int INITIAL_CAPACITY = 16;

    byte[] array;
    int size = 0;

    public DynamicByteArray() {
         this.array = new byte[INITIAL_CAPACITY];
         size = 0;
    }

    public DynamicByteArray(int initialSize) {
        this.array = new byte[initialSize];
        size = initialSize;
    }
    public void set(int index, byte value) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index + " is out of bounds for size " + size);
        }

        array[index] = value;
    }

    public byte get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index " + index + " is out of bounds for size " + size);
        }

        return array[index];
    }

    public int size() {
        return size;
    }

    public void add(byte value) {
        if (size == array.length - 1) {
            grow();
        }

        array[size++] = value;
    }

    private void grow() {
        int new_size = array.length << 1;
        byte[] new_array = new byte[new_size];
        System.arraycopy(array, 0, new_array, 0, array.length);
        array = new_array;
    }

    public void clear() {
        size = 0;
    }
}
