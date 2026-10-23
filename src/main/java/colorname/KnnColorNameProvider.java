package colorname;

import shared.ColorUtility;

import java.awt.*;
import java.nio.file.Path;

/**
 * Uses K-nearest neighbors to find the best approximation in the dataset
 */
public class KnnColorNameProvider implements ColorNameProvider {

    private final ImmutableIntToStringMap map;
    int[] colors; //known colors stored as an integer where the lower three bytes correspond to red, blue and green.

    public KnnColorNameProvider(Path colorNameFile) {
        map = ColorNameLoader.loadColorNames(colorNameFile);
        colors = map.getKeys();
    }

    @Override
    public String get(Color color) {
        int closestColor = findClosestColor(color);
        String name = map.get(closestColor);

        return name == null ? "" : name;
    }

    private int findClosestColor(Color color) {
        double closestDistance = Double.MAX_VALUE;
        int closestColor = -1;

        for (int knownColor : colors) {
           double distance = distance(color, knownColor);
           if (distance < closestDistance) {
               closestDistance = distance;
               closestColor = knownColor;
           }
        }

        return closestColor;
    }

    private double distance(Color color, int cellColor) {
        int red = ColorUtility.getRed(cellColor) - color.getRed();
        int green = ColorUtility.getGreen(cellColor) - color.getGreen();
        int blue = ColorUtility.getBlue(cellColor) - color.getBlue();

        return Math.sqrt(red * red + green * green + blue * blue);
    }
}
