package shared.colorgeneration;

import shared.colorconversion.ColorConverter;

import java.awt.*;

public class ColorGenerator {
    public static Color getComplementColor(Color color) {
        double[] hsv = ColorConverter.rgbToHsv(color);

        double newHue = (hsv[0] + 180.0) % 360.0;

        return ColorConverter.hsvToRgb(newHue, hsv[1], hsv[2]);
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

        double[] hsv = ColorConverter.rgbToHsv(color);

        for (int i = 1; i <= amount; i++) {
            colors[amount - i] = ColorConverter.hsvToRgb(offsetHue(hsv[0], -(angle * amount)), hsv[1], hsv[2]);
            colors[amount + i] = ColorConverter.hsvToRgb(offsetHue(hsv[0], (angle * amount)), hsv[1], hsv[2]);
        }

        return colors;
    }

    /**
     * overload for getAnalogousColor(Color, int) with amount defaulted to 1
     */
    public static Color[] getAnalogousColors(Color color) {
        return getAnalogousColors(color, 1);
    }

    /**
     * offset the given hue by the given amount, staying in the [0-360] range
     */
    private static double offsetHue(double hue, double offset) {
        double newHue = (hue + offset) %  360.0;
        if (newHue < 0) {
            newHue += 360.0;
        }

        return newHue;
    }

    public static Color[] getSplitComplementaryColors(Color color, int amount) {
        Color[] colors = getAnalogousColors(getComplementColor(color), amount);
        colors[amount] = color;

        return colors;
    }

    public static Color[] getSplitComplementaryColors(Color color) {
        return getSplitComplementaryColors(color, 1);
    }

    public static Color[] getTriadicColors(Color color) {
        return getColorNGon(color, 3);
    }

    public static Color[] getSquareColors(Color color) {
        return getColorNGon(color, 4);
    }

    /**
     * Returns the colors on the color wheel that are at the corners of the regular n-gon with one corner at the given color
     * @param color the color to anchor the color wheel
     * @param n how many sides the n-gon should have, i.e. how many colors, including the start color, will the resulting color
     * @return a color palette consisting of n colors evenly spaced on the color wheel
     */
    private static Color[] getColorNGon(Color color, int n) {
        Color[] colors = new Color[n];
        double[] hsv = ColorConverter.rgbToHsv(color);

        double anglePerStep = 360.0 / n;
        for (int step = 0; step < n; step++) {
            double currentHue = offsetHue(hsv[0], anglePerStep * step);
            colors[step] = ColorConverter.hsvToRgb(currentHue, hsv[1], hsv[2]);
        }

        return colors;
    }

    public static Color[] getColorShades(Color color, int count) {
        Color[] colors = new Color[count];
        double[] hsl = ColorConverter.rgbToHsl(color);


        int anchorPosition = (int) Math.min((count - Math.round(hsl[2] * count)), count - 1);

        colors[anchorPosition] = color;

        //double lightnessPerStep = 1.0 / count;
        double lightnessPerStep = (1.0 - hsl[2]) / anchorPosition;

        for (int step = 0; step < count; step++) {
            if (step == anchorPosition) {
                continue;
            }

            double currentLightness = 1.0 - lightnessPerStep * step;
            colors[step] = ColorConverter.hslToRgb(hsl[0], hsl[1], currentLightness);
        }

        return colors;
    }
}
