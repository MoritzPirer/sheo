package controller;

import javafx.util.Pair;
import model.*;
import controller.listeners.ColorChangeListener;
import controller.listeners.ColorCreationListener;
import controller.listeners.ColorDeletionListener;
import model.colorname.ColorNameProvider;
import shared.random.Random;
import shared.random.RandomnessProvider;
import userinterface.UiFactory;
import userinterface.javafxinterface.JavaFxFactory;
import view.ViewManager;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class Controller {
    public static final int MAX_COLOR_COUNT = 10;

    private Palette palette;
    private final RandomnessProvider random = new Random();

    private final List<ColorDeletionListener> colorDeletionListeners = new ArrayList<>();
    private final List<ColorCreationListener> colorCreationListeners = new ArrayList<>();
    private final List<ColorChangeListener> colorChangeListeners = new ArrayList<>();
    private ViewManager viewManager;

    private final ColorNameProvider colorNameProvider;

    public Controller(ColorNameProvider colorNameProvider) {
        this.colorNameProvider = colorNameProvider;
    }

    public void run(String[] args) {
        // 1. initialize frontend
        UiFactory factory = new JavaFxFactory();
        viewManager = new ViewManager(factory);

        // 2. Run creation logic
        viewManager.initialize(args);

        // 3. Create palette (eventually given from creation logic
        PaletteCreator creator = new RandomPaletteCreator(5);
        palette = creator.create();

        // 4. Run main scene
        viewManager.switchToEditor(this);
    }

    // only for testing
    public void setPalette(Palette palette) {
        this.palette = palette;
    }

    public int getColorCount() {
        return palette.size();
    }

    public Color getColor(int index) {
        return palette.getColor(index);
    }

    public void addColorChangeListener(ColorChangeListener listener) {
        colorChangeListeners.add(listener);
    }

    public void addColorDeletionListener(ColorDeletionListener listener) {
        colorDeletionListeners.add(listener);
    }

    public void addColorCreationListener(ColorCreationListener listener) {
        colorCreationListeners.add(listener);
    }

    public void randomizeColor(int index) {
        setColor(index, random.makeColor());
    }

    public void setColor(int index, Color after) {
        Color before = palette.getColor(index);
        palette.setColor(index, after);

        colorChangeListeners.stream()
            .filter(c -> c.getIndex() == index)
            .forEach(c -> c.onChange(before, after));
    }

    public void deleteColor(int index) {
        palette.deleteColor(index);
        colorDeletionListeners.forEach(p -> p.onRemove(index));

        // Notify listeners of the removed color
        colorChangeListeners.stream()
            .filter(c -> c.getIndex() == index)
            .forEach(c -> c.onIndexShift(-1));
        colorChangeListeners.removeIf(c -> c.getIndex() == index);

        // Notify listeners whose index is now outdated
        colorChangeListeners.stream()
            .filter(c -> c.getIndex() > index)
            .forEach(c -> c.onIndexShift(c.getIndex() - 1));
    }

    public void addColor() {
        if (palette.size() < MAX_COLOR_COUNT) {
            palette.addColor(random.makeColor());
            colorCreationListeners.forEach(listener -> listener.onAdd(palette.size() - 1));
        }
    }

    public void openInspector() {
        //TODO
    }

    public void openEditor(int colorIndex) {
        viewManager.openEditor(colorIndex);
    }

    public String getColorName(Color color) {
        return colorNameProvider.get(color);
    }

    public List<Pair<Color, String>> getSimilarColors(Color color, int count) {
        return colorNameProvider.getNearest(color, count);
    }
}
