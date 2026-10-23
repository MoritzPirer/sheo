package shared.random;

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
}
