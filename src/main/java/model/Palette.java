package model;

import shared.random.Random;
import shared.random.RandomnessProvider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Palette {
    private final RandomnessProvider random;
    private final List<Color> colors;

    private final List<PaletteChangeListener> paletteChangeListeners = new ArrayList<>();
    private final List<ColorChangeListener> colorChangeListeners = new ArrayList<>();

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

    private void notifyColorChange(int index, Color before, Color after) {
        colorChangeListeners.forEach(c -> c.onChange(index, before, after));
    }

    public void addColorChangeListener(ColorChangeListener listener) {
        colorChangeListeners.add(listener);
    }

    private void notifyPaletteChange(List<Color> before, List<Color> after) {
        paletteChangeListeners.forEach(p -> p.onChange(before, after));
    }

    public void addPaletteChangeListener(PaletteChangeListener paletteChangeListener) {
        paletteChangeListeners.add(paletteChangeListener);
    }

    public int getColorCount() {
        return colors.size();
    }

    public Color getColor(int index) {
        return colors.get(index);
    }

    public void randomizeColor(int index) {
        // TODO validation

        Color before = colors.get(index);
        Color after = random.makeColor();
        colors.set(index, after);

        notifyColorChange(index, before, after);
    }

    public void deleteColor(int index) {
        colors.remove(index);
        paletteChangeListeners.forEach(p -> p.onRemove(index));
    }
}
