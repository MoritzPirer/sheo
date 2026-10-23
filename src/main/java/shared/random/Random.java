package shared.random;


public class Random implements RandomnessProvider {
    private final java.util.Random random = new java.util.Random();

    @Override
    public int nextInt(int from, int to) {
        return random.nextInt(from, to);
    }
}
