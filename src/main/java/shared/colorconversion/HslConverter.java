package shared.colorconversion;

import java.awt.*;

class HslConverter {
    public static double[] rgbToHsl(Color color) {
        double redPercentage = (double) color.getRed() / 255.0;
        double greenPercentage = (double) color.getGreen() / 255.0;
        double bluePercentage = (double) color.getBlue() / 255.0;

        double max = Math.max(redPercentage, Math.max(greenPercentage, bluePercentage));
        double min = Math.min(redPercentage, Math.min(greenPercentage, bluePercentage));

        double hue = ConversionUtility.getHue(min, max, redPercentage, greenPercentage, bluePercentage);

        double lightness = (max + min) / 2.0;

        double saturation = 0.0;
        if (max != min) {
            saturation = (max - min) / (1.0 - Math.abs(2.0 * lightness - 1.0));
        }

        return new double[]{ hue, saturation, lightness };
    }

    public static Color hslToRgb(double hue, double saturation, double lightness) {
        double r, g, b;

        if (saturation == 0.0) {
            r = g = b = lightness; // grayscale
        } else {
            double q = lightness < 0.5 ?
                    lightness * (1.0 + saturation) :
                    lightness + saturation - lightness * saturation;

            double p = 2.0 * lightness - q;

            double hNorm = hue / 360.0;

            r = hueToChannel(p, q, hNorm + 1.0 / 3.0);
            g = hueToChannel(p, q, hNorm);
            b = hueToChannel(p, q, hNorm - 1.0 / 3.0);
        }

        int red = (int) Math.round(r * 255.0);
        int green = (int) Math.round(g * 255.0);
        int blue = (int) Math.round(b * 255.0);

        return new Color(
                Math.clamp(red, 0, 255),
                Math.clamp(green, 0, 255),
                Math.clamp(blue, 0, 255)
        );
    }

    private static double hueToChannel(double p, double q, double t) {
        if (t < 0.0) t += 1.0;
        if (t > 1.0) t -= 1.0;
        if (t < 1.0 / 6.0) return p + (q - p) * 6.0 * t;
        if (t < 1.0 / 2.0) return q;
        if (t < 2.0 / 3.0) return p + (q - p) * (2.0 / 3.0 - t) * 6.0;
        return p;
    }

}
