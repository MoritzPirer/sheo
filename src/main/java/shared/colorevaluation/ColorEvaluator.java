package shared.colorevaluation;

import shared.colorconversion.ColorConverter;

import java.awt.*;

public class ColorEvaluator {

    /**
     * calculates the contrast between two colors
     * @return a value between 0 and 21, where higher numbers mean higher contrast
     * @see <a href="https://www.accessibility-developer-guide.com/knowledge/colours-and-contrast/how-to-calculate/#the-formula">...</a>
     */
    public static double calculateContrast(Color a, Color b) {
        double luminanceOfA = calculateRelativeLuminance(a);
        double luminanceOfB = calculateRelativeLuminance(b);

        double higherLuminance = Math.max(luminanceOfA, luminanceOfB);
        double lowerLuminance = Math.min(luminanceOfA, luminanceOfB);

        return (higherLuminance + 0.05) / (lowerLuminance + 0.05);
    }

    /**
     * calculates the relative luminance of the given color, as defined by the below link
     * @see <a href="https://www.w3.org/TR/WCAG20/#relativeluminancedef">...</a>
     */
    private static double calculateRelativeLuminance(Color color) {
        double red = calculateRelativeLuminanceOfChannel(ColorConverter.channelToPercentage(color.getRed()));
        double green = calculateRelativeLuminanceOfChannel(ColorConverter.channelToPercentage(color.getGreen()));
        double blue = calculateRelativeLuminanceOfChannel(ColorConverter.channelToPercentage(color.getBlue()));

        return 0.2126 * red + 0.7152 * green + 0.0722 * blue;
    }

    private static double calculateRelativeLuminanceOfChannel(double value) {
        if (value <= 0.03928) {
            return value / 12.92;
        }

        return Math.pow((value + 0.055) / 1.055, 2.4);
    }
}
