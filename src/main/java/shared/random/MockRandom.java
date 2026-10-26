package shared.random;

import java.awt.*;

/**
 * for use in testing
 */
public class MockRandom implements RandomnessProvider {
    int lastInt = 0;
    @Override
    public int nextInt(int from, int to) {
        if (to <= from) {
            throw new IllegalArgumentException("illegal bounds");
        }
        lastInt++;
        return (lastInt % (to - from)) + from;
    }

    @Override
    public Color makeColor() {
        return new Color(++lastInt, ++lastInt, ++lastInt);
    }
}
