package shared.colorconversion;

import java.awt.*;

public class ColorConverter {

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

    /**
     * converts the value of an rgb channel [0-255] to a percentage value [0-1]
     */
    public static double channelToPercentage(int channel) {
        return channel / 255.0;
    }

    /**
     * converts a percentage [0-1] to the corresponding value in an rgb channel [0-255]
     */
    public static int percentageToChannel(int percentage) {
        return percentage * 255;
    }
}
