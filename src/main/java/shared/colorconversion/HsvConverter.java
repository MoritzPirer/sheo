package shared.colorconversion;

import shared.MathUtility;

import java.awt.*;

public class HsvConverter {

    /**
     * converts the given color into its HSV representation
     * @param color the color to convert
     * @return an array of the form [H, S, V]
     * @see <a href="https://de.wikipedia.org/wiki/HSV-Farbraum#Umrechnung_RGB_in_HSV/HSL">Formula</a>
     */
    public static double[] rgbToHsv(Color color) {
        double redPercentage = (double) color.getRed() / 255;
        double greenPercentage = (double) color.getGreen() / 255;
        double bluePercentage = (double) color.getBlue() / 255;

        double max = MathUtility.max(redPercentage, greenPercentage, bluePercentage);
        double min = MathUtility.min(redPercentage, greenPercentage, bluePercentage);

        double hue = ConversionUtility.getHue(min, max, redPercentage, greenPercentage, bluePercentage);

        double saturation = (max == min) ?
                0 :
                (max - min) / max;

        return new double[]{ hue, saturation, max };
    }

    public static Color hsvToRgb(double hue, double saturation, double value) {
        double redPercentage = rgbToHsvHelper(5, hue, saturation, value);
        double greenPercentage = rgbToHsvHelper(3, hue, saturation, value);
        double bluePercentage = rgbToHsvHelper(1, hue, saturation, value);

        int red = (int) (redPercentage * 255);
        int green = (int) (greenPercentage * 255);
        int blue = (int) (bluePercentage * 255);

        return new Color(red, green, blue);
    }

    private static double rgbToHsvHelper(int n, double hue, double saturation, double value) {
        double k = (n + hue / 60.0) % 6;
        return value - value * saturation * Math.max(0, MathUtility.min(k, 4 - k, 1));
    }
}
