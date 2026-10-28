package model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListPalette implements Palette {
    private final List<Color> colors;

    ListPalette(List<Color> colors) {
        this.colors = colors;
    }

    ListPalette() {
        this.colors = new ArrayList<>();
    }

    private void validateIndex(int index) {
        if (index < 0 || index >= colors.size()) {
            throw new IllegalArgumentException("Index " + index + " is out of bounds for size " + colors.size());
        }
    }

    @Override
    public int size() {
        return colors.size();
    }

    @Override
    public Color getColor(int index) {
        validateIndex(index);
        return colors.get(index);
    }

    @Override
    public void setColor(int index, Color color) {
        validateIndex(index);
        colors.set(index, color);
    }

    @Override
    public void deleteColor(int index) {
        validateIndex(index);
        colors.remove(index);
    }

    @Override
    public void addColor(Color color) {
        colors.add(color);
    }
}
