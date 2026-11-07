package shared.colorconversion;

import java.awt.*;

public class ColorUtility {

    public static String colorToHexString(Color color) {
        return ColorStringConverter.colorToHexString(color);
    }

    public static Color hexToColor(String hex) {
        return ColorStringConverter.hexStringToColor(hex);
    }

    public static boolean isHexString(String hex) {
        return ColorStringConverter.isHexString(hex);
    }

    public static int getRed(int color) {
        return (color >> 16) & 0xFF;
    }

    public static int getGreen(int color) {
        return (color >> 8) & 0xFF;
    }

    public static int getBlue(int color) {
        return color & 0xFF;
    }

    /**
     * converts the given color into its HSV representation
     * @param color the color to convert
     * @return an array of the form [H, S, V]
     * @see <a href="https://de.wikipedia.org/wiki/HSV-Farbraum#Umrechnung_RGB_in_HSV/HSL">Formula</a>
     */
    public static double[] rgbToHsv(Color color) {
        return HsvConverter.rgbToHsv(color);
    }

    public static Color hsvToRgb(double hue, double saturation, double value) {
        return HsvConverter.hsvToRgb(hue, saturation, value);
    }

    public static double[] rgbToHsl(Color color) {
        return HslConverter.rgbToHsl(color);
    }

    public static Color hslToRgb(double hue, double saturation, double lightness) {
        return HslConverter.hslToRgb(hue, saturation, lightness);
    }

    public static Color getComplementColor(Color color) {
        double[] hsv = rgbToHsv(color);

        double newHue = (hsv[0] + 180.0) % 360.0;

        return hsvToRgb(newHue, hsv[1], hsv[2]);
    }

    /**
     * finds amount - 1 analogous colors for the given color
     * @param color the color to find analogous colors for
     * @param amount the number of colors to find on either side of color
     * @return an array of (2 * amount) + 1 colors with the given color in the middle
     */
    public static Color[] getAnalogousColors(Color color, int amount) {
        assert amount >= 0;

        double angle = 30.0 / amount;
        Color[] colors = new Color[(2 * amount) + 1];
        colors[amount] = color;

        double[] hsv = rgbToHsv(color);

        for (int i = 1; i <= amount; i++) {
            colors[amount - i] = hsvToRgb(offsetHue(hsv[0], -(angle * amount)), hsv[1], hsv[2]);
            colors[amount + i] = hsvToRgb(offsetHue(hsv[0], (angle * amount)), hsv[1], hsv[2]);
        }

        return colors;
    }

    private static double offsetHue(double hue, double offset) {
        double newHue = (hue + offset) %  360.0;
        if (hue < 0) {
            hue += 360.0;
        }

        return newHue;
    }

    /**
     * overload for getAnalogousColor(Color, int) with amount defaulted to 1
     */
    public static Color[] getAnalogousColors(Color color) {
        return getAnalogousColors(color, 1);
    }
}
