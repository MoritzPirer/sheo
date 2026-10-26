package model;

import shared.random.Random;
import shared.random.RandomnessProvider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Palette {
    private final RandomnessProvider random;
    private final List<Color> colors;

    private Palette(List<Color> colors) {
        this.colors = colors;
        this.random = new Random();
    }

    public static Palette createRandom(int count) {
        List<Color> colors = new ArrayList<>(count);
        RandomnessProvider random = new Random();

        for (int i = 0; i < count; i++) {
            int r = random.nextInt(255);
            int g = random.nextInt(255);
            int b = random.nextInt(255);

            colors.add(new Color(r, g, b));
        }

        return new Palette(colors);
    }

    public int getColorCount() {
        return colors.size();
    }

    public Color getColor(int index) {
        return colors.get(index);
    }

    public void randomizeColor(int index) {
        if (index < 0 || index >= colors.size()) {
            throw new IllegalArgumentException("Index " + index + " is out of bounds for size " + colors.size());
        }

        colors.set(index, random.makeColor());
    }

    public void deleteColor(int index) {
        colors.remove(index);
    }

    public void addColor() {
        colors.add(random.makeColor());
    }
}
