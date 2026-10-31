package shared;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class ColorUtilityTest {
    private static final double TOLERANCE = 5;
    /**
     * calculates the squared error of the rgb channels of two colors
     * @return a value between 0 and 441, with a lower value meaning more similar
     */
    private static double squaredError(Color a, Color b) {
        double redError = a.getRed() - b.getRed();
        double greenError = a.getGreen() - b.getGreen();
        double blueError = a.getBlue() - b.getBlue();

        return Math.sqrt(
            redError * redError +
            greenError * greenError +
            blueError * blueError
        );
    }
    @Test
    void testColorToHex() {
        for (int red = 0; red <= 255; red++) {
            for (int green = 0; green <= 255; green++) {
                for (int blue = 0; blue <= 255; blue++) {
                    Color color = new Color(red, green, blue);
                    String hex = ColorUtility.colorToHex(color);
                    String withoutHashtag = hex.substring(1);
                    int value = Integer.parseInt(withoutHashtag, 16);

                    Color reconstructed = new Color(value);

                    assertEquals(color, reconstructed);
                }
            }
        }
    }

    @Test
    void testGetRedGetGreenGetBlue() {
        for (int red = 0; red <= 255; red++) {
            for (int green = 0; green <= 255; green++) {
                for (int blue = 0; blue <= 255; blue++) {
                    Color color = new Color(red, green, blue);
                    int rgb = color.getRGB();

                    assertEquals(red, ColorUtility.getRed(rgb));
                    assertEquals(green, ColorUtility.getGreen(rgb));
                    assertEquals(blue, ColorUtility.getBlue(rgb));
                }
            }
        }
    }

    @Test
    void testToHsvFromHsv() {
        Color[] colors = new Color[]{
                Color.WHITE,
                Color.RED,
                Color.GREEN,
                Color.BLUE,
                Color.YELLOW,
                Color.BLACK,
                Color.GRAY
        };

        for (Color color : colors) {
            double[] hsv = ColorUtility.toHsv(color);
            Color reconstructed = ColorUtility.fromHsv(hsv[0], hsv[1], hsv[2]);

            assertTrue(squaredError(color, reconstructed) <= TOLERANCE);
        }
    }

    @Test
    void testToHslFromHsl() {
        Color[] colors = new Color[]{
            Color.WHITE,
            Color.RED,
            Color.GREEN,
            Color.BLUE,
            Color.YELLOW,
            Color.BLACK,
            Color.GRAY
        };

        for (Color color : colors) {
            double[] hsl = ColorUtility.toHsl(color);
            Color reconstructed = ColorUtility.fromHsl(hsl[0], hsl[1], hsl[2]);

            assertTrue(squaredError(color, reconstructed) <= TOLERANCE, "Color " + color + " converted to " + reconstructed + " with error " + squaredError(color, reconstructed));
        }
    }
}
