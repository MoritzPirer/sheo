package shared.colorconversion;

class ConversionUtility {
    public static double getHue(double min, double max, double redPercentage, double greenPercentage, double bluePercentage) {
        double hue = 0.0;
        double delta = max - min;

        if (delta == 0.0) {
            hue = 0.0;
        } else if (max == redPercentage) {
            hue = 60.0 * (((greenPercentage - bluePercentage) / delta) % 6.0);
        } else if (max == greenPercentage) {
            hue = 60.0 * (((bluePercentage - redPercentage) / delta) + 2.0);
        } else if (max == bluePercentage) {
            hue = 60.0 * (((redPercentage - greenPercentage) / delta) + 4.0);
        }

        if (hue < 0.0) {
            hue += 360.0;
        }

        return hue;
    }
}
