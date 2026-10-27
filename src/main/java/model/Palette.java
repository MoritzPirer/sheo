package model;

import shared.random.Random;
import shared.random.RandomnessProvider;

import java.awt.*;
import java.util.List;

public class Palette {
    private final RandomnessProvider random;
    private final List<Color> colors;

    Palette(List<Color> colors) {
        this.colors = colors;
        this.random = new Random();
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= colors.size()) {
            throw new IllegalArgumentException("Index " + index + " is out of bounds for size " + colors.size());
        }
    }

    public int getColorCount() {
        return colors.size();
    }

    public Color getColor(int index) {
        validateIndex(index);
        return colors.get(index);
    }

    public void randomizeColor(int index) {
        validateIndex(index);
        colors.set(index, random.makeColor());
    }

    public void deleteColor(int index) {
        validateIndex(index);
        colors.remove(index);
    }

    public void addColor() {
        colors.add(random.makeColor());
    }
}
