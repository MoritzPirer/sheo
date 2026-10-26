package userinterface;

public record Padding(int top, int bottom, int left, int right) {
    public Padding(int amount) {
        this(amount, amount, amount, amount);
    }

    public Padding(int topAndBottom, int sides) {
        this(topAndBottom, topAndBottom, sides, sides);
    }
}
