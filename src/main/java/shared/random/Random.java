package shared.random;


import java.awt.*;

public class Random implements RandomnessProvider {
    private final java.util.Random random = new java.util.Random();

    @Override
    public int nextInt(int from, int to) {
        return random.nextInt(from, to);
    }

    @Override
    public Color makeColor() {
        int r = nextInt(0, 255);
        int g = nextInt(0, 255);
        int b = nextInt(0, 255);

        return new Color(r, g, b);
    }

}
