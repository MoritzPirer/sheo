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

    public static Color hexToColor(String hex) {
        assert isHexCode(hex);

        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        if (hex.isEmpty()) {
            return new Color(0, 0, 0);
        }

        int rgb = Integer.parseInt(hex, 16);
        return new Color(rgb);
    }

    public static boolean isHexCode(String hex) {
        if (hex.length() > HEX_CODE_LENGTH + 1 || hex.length() < HEX_CODE_LENGTH) {
            return false;
        }
        if (hex.length() == HEX_CODE_LENGTH + 1) {
            if (hex.startsWith("#")) {
                hex = hex.substring(1);
            }
            else {
                return false;
            }
        }


        for (char c : hex.toCharArray()) {
            if (!Character.isDigit(c) && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')) {
                return false;
            }
        }

        return true;
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

        double hue = getHue(min, max, redPercentage, greenPercentage, bluePercentage);

        double saturation = (max == min) ?
            0 :
            (max - min) / max;


        return new double[]{ hue, saturation, max };
    }

    public static double[] toHsl(Color color) {
        double redPercentage = (double) color.getRed() / 255.0;
        double greenPercentage = (double) color.getGreen() / 255.0;
        double bluePercentage = (double) color.getBlue() / 255.0;

        double max = Math.max(redPercentage, Math.max(greenPercentage, bluePercentage));
        double min = Math.min(redPercentage, Math.min(greenPercentage, bluePercentage));

        double hue = getHue(min, max, redPercentage, greenPercentage, bluePercentage);

        double lightness = (max + min) / 2.0;

        double saturation = 0.0;
        if (max != min) {
            saturation = (max - min) / (1.0 - Math.abs(2.0 * lightness - 1.0));
        }

        return new double[]{ hue, saturation, lightness };
    }

    private static double getHue(double min, double max, double redPercentage, double greenPercentage, double bluePercentage) {
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

    public static Color fromHsl(double hue, double saturation, double lightness) {
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
