package controller;

import model.*;
import controller.listeners.ColorChangeListener;
import controller.listeners.ColorCreationListener;
import controller.listeners.ColorDeletionListener;
import userinterface.UiFactory;
import userinterface.javafxinterface.JavaFxFactory;
import view.ViewManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static void main(String[] args) {
        new Controller().run(args);
    }

    private Palette palette;

    private final java.util.List<ColorDeletionListener> colorDeletionListeners = new ArrayList<>();
    private final java.util.List<ColorCreationListener> colorCreationListeners = new ArrayList<>();
    private final List<ColorChangeListener> colorChangeListeners = new ArrayList<>();

    public void run(String[] args) {
        // 1. initialize frontend
        UiFactory factory = new JavaFxFactory();
        ViewManager viewManager = new ViewManager(factory);

        // 2. Run creation logic
        viewManager.initialize(args);

        // 3. Create palette
        PaletteCreator creator = new RandomPaletteCreator(5);
        palette = creator.create();

        // 4. Run main scene
        viewManager.switchToEditor(this);
    }

    public int getColorCount() {
        return palette.getColorCount();
    }

    public Color getColor(int index) {
        return palette.getColor(index);
    }

    public void addColorChangeListener(ColorChangeListener listener) {
        colorChangeListeners.add(listener);
    }

    public void randomizeColor(int index) {
        Color before = palette.getColor(index);
        palette.randomizeColor(index);
        Color after = palette.getColor(index);

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


    public void addColorDeletionListener(ColorDeletionListener listener) {
        colorDeletionListeners.add(listener);
    }

    public void addColorCreationListener(ColorCreationListener listener) {
        colorCreationListeners.add(listener);
    }

    public void addColor() {
        palette.addColor();
        colorCreationListeners.forEach(listener -> listener.onAdd(palette.getColorCount() - 1));
    }
}
