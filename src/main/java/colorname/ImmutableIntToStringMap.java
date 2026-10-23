package colorname;

import java.util.Arrays;
import java.util.Comparator;

/**
 * used instead of a regular Map from Integer to String to reduce object overhead
 */
class ImmutableIntToStringMap {
    private final int[] keys;
    private final String[] values;

    /**
     * create a new map with keys[i] mapping to values[i]. Caller must ensure keys are unique
     */
    public ImmutableIntToStringMap(int[] keys, String[] values) {
        if (keys.length != values.length) {
            throw new IllegalArgumentException("mismatch between keys and values");
        }
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

    /**
     * returns the value associated with the int, or null if the key does not exist
     */
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
