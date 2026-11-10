package model.colorname;

import javafx.util.Pair;
import shared.colorconversion.ColorConverter;

import java.awt.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

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
        int red = ColorConverter.getRed(cellColor) - color.getRed();
        int green = ColorConverter.getGreen(cellColor) - color.getGreen();
        int blue = ColorConverter.getBlue(cellColor) - color.getBlue();

        return Math.sqrt(red * red + green * green + blue * blue);
    }

    @Override
    public List<Pair<Color, String>> getNearest(Color color, int count) {
        System.out.println("getting nearest for " + ColorConverter.colorToHexString(color));
        System.out.println("i know " + colors.length + " colors");

        if (count < 0 || color == null) {
            return null;
        }

        int[] nearestColors = findClosestColors(color, count);
        String[] nearestColorNames = namesOfColors(nearestColors);

        List<Pair<Color, String>> result = new ArrayList<>(count);
        for (int i = 0; i < nearestColors.length; i++) {
            result.add(new Pair<>(new Color(nearestColors[i]), nearestColorNames[i]));
        }

        Pair<Color, String> dummy = new Pair<>(Color.BLACK, "No color found");
        for (int i = 0; i < count - nearestColors.length; i++) {
            result.add(dummy);
        }

        return result;
    }

    private int[] findClosestColors(Color color, int count) {
        if (count == 1) {
            return new int[]{findClosestColor(color)};
        }
        count = Math.min(count, colors.length);

        PriorityQueue<Integer> heap = new PriorityQueue<>(
            count, Comparator.comparingDouble((Integer a) -> distance(color, a)).reversed()
        );

        int rejected = 0;
        for (int knownColor : colors) {
            if (heap.size() < count) {
                heap.add(knownColor);
            }
            else {
                double distance = distance(color, knownColor);

                assert !heap.isEmpty();
                double worstDistanceInHeap = distance(color, heap.peek());

                if (distance < worstDistanceInHeap) {
                    //System.out.println("accepting " + knownColor);
                    heap.poll();
                    heap.add(knownColor);
                }
                else {
                    rejected++;
                }
            }
        }

        System.out.println("rejected: " + rejected);

        int[] result = new int[count];
        int index = result.length - 1;
        while (!heap.isEmpty()) {
            result[index--] = heap.poll();
        }

        for (int r : result) {
            System.out.println("chose " + r + "=" + Integer.toHexString(r).toUpperCase());
        }
        return result;
    }

    private String[] namesOfColors(int[] colors) {
        String[] result = new String[colors.length];

        for (int i = 0; i < colors.length; i++) {
            result[i] = map.get(colors[i]);
        }

        return result;
    }
}
