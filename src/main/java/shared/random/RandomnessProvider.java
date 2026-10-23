package shared.random;

/**
 * Used everywhere the program requires randomness. Allows for deterministic testing.
 */
public interface RandomnessProvider {
    int nextInt(int from, int to);

    default int nextInt(int to) {
        return nextInt(0, to);
    }
}
