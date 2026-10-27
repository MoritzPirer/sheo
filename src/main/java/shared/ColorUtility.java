package shared;

import java.awt.*;

public class ColorUtility {
    private static final int HEX_CODE_LENGTH = 6;

    public static String colorToHex(Color color) {
        int value = color.getRGB() & 0xFFFFFF;

        StringBuilder builder = new StringBuilder("#");

        String hex = Integer.toHexString(value);

        // add leading zeros if needed
        int paddingNeeded = HEX_CODE_LENGTH - hex.length();
        builder.repeat("0", paddingNeeded);

        return builder.append(hex).toString();
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
    public static double[] toHsv(Color color) {
        double redPercentage = (double) color.getRed() / 255;
        double greenPercentage = (double) color.getGreen() / 255;
        double bluePercentage = (double) color.getBlue() / 255;

        double max = max(redPercentage, greenPercentage, bluePercentage);
        double min = min(redPercentage, greenPercentage, bluePercentage);

        double hue = 0;
        if (max == redPercentage) {
            hue = 60.0 * (greenPercentage - bluePercentage) / (max - min);
        }
        else if (max == greenPercentage) {
            hue = 60.0 * (2 + (bluePercentage - greenPercentage) / (max - min));
        }
        else if (max == bluePercentage) {
            hue = 60.0 * (4 + (redPercentage - greenPercentage) / (max - min));
        }
        if (hue < 0.0) {
            hue += 360.0;
        }

        double saturation = (max == min) ?
            0 :
            (max - min) / max;


        return new double[]{ hue, saturation, max };
    }

    private static double max(double... options) {
        if (options.length == 0) return -1;

        double candidate = options[0];
        for (double option : options) {
            if (option > candidate) {
                candidate = option;
            }
        }

        return candidate;
    }

    private static double min(double... options) {
        if (options.length == 0) return -1;

        double candidate = options[0];
        for (double option : options) {
            if (option < candidate) {
                candidate = option;
            }
        }

        return candidate;
    }

    public static Color fromHsv(double hue, double saturation, double value) {
        double redPercentage = rgbToHsvHelper(5, hue, saturation, value);
        double greenPercentage = rgbToHsvHelper(3, hue, saturation, value);
        double bluePercentage = rgbToHsvHelper(1, hue, saturation, value);

        int red = (int) (redPercentage * 255);
        int green = (int) (greenPercentage * 255);
        int blue = (int) (bluePercentage * 255);

        try {
            return new Color(red, green, blue);
        }
        catch (Exception e) {
            System.out.println("Error converting hsv to color: " + e.getMessage());
            System.out.println("hue: " + hue + " saturation: " + saturation + " value: " + value);
            System.out.println("red: " + red + " green: " + green + " blue: " + blue);
            throw new RuntimeException(e);
        }

    }

    private static double rgbToHsvHelper(int n, double hue, double saturation, double value) {
        double k = (n + hue / 60.0) % 6;
        return value - value * saturation * max(0, min(k, 4 - k, 1));
    }
}
