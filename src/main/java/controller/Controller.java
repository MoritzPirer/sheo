package controller;

import model.*;
import model.listeners.ColorChangeListener;
import model.listeners.ColorDeletionListener;
import userinterface.UiFactory;
import userinterface.javafxinterface.JavaFxFactory;
import view.ViewManager;

import java.awt.*;

public class Controller {
    public static void main(String[] args) {
        new Controller().run(args);
    }

    private Palette palette;
    private ViewManager viewManager;

    public void run(String[] args) {
        // 1. initialize frontend
        UiFactory factory = new JavaFxFactory();
        viewManager = new ViewManager(factory, args);

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

    public void randomizeColor(int index) {
        palette.randomizeColor(index);
    }

    public void deleteColor(int index) {
        palette.deleteColor(index);
    }

    public void addColorChangeListener(ColorChangeListener listener) {
        //TODO move listeners into controller
        palette.addColorChangeListener(listener);
    }

    public void addPaletteChangeListener(ColorDeletionListener listener) {
        palette.addColorDeletionListener(listener);
    }
}
