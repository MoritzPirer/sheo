package userinterface;

public record CornerRadius(double radius, boolean isPercent) {
    public CornerRadius(double radius, boolean isPercent) {
        this.radius = radius;
        this.isPercent = isPercent;

        if (isPercent && (radius < 0 || radius > 1)) {
            throw new IllegalArgumentException("Radius must be between 0 and 1 when given as percentage");
        }
    }
    public CornerRadius(double radius) {
        this(radius, false);
    }
}
