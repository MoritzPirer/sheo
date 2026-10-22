package colorname;

import java.util.Arrays;
import java.util.Comparator;

class ImmutableIntToStringMap {
    private final int[] keys;
    private final String[] values;

    public ImmutableIntToStringMap(int[] keys, String[] values) {
        Integer[] indices = new Integer[keys.length];
        for (int i = 0; i < indices.length; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, Comparator.comparingInt(a -> keys[a]));

        this.keys = new int[keys.length];
        this.values = new String[values.length];

        for (int i = 0; i < indices.length; i++) {
            this.keys[i] = keys[indices[i]];
            this.values[i] = values[indices[i]];
        }
    }

    String get(int key) {
        int index = Arrays.binarySearch(keys, key);
        if (index < 0) {
            return null;
        }

        return values[index];
    }

    int[] getKeys() {
        return keys;
    }
}
