package controller;

import controller.listeners.ColorChangeListener;
import controller.listeners.ColorCreationListener;
import controller.listeners.ColorDeletionListener;
import model.ListPalette;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    private Controller setupSingleColorController() {
        Controller controller = new Controller();
        List<Color> list = new ArrayList<>();
        list.add(new Color(0, 0, 0));

        controller.setPalette(new ListPalette(list));
        return controller;
    }

    @Test
    public void whenColorIsRandomized_changeListenersAreNotified() {
        Controller controller = setupSingleColorController();

        final boolean[] wasNotified = {false};
        controller.addColorChangeListener(new ColorChangeListener() {
            @Override
            public int getIndex() { return 0; }

            @Override
            public void onChange(Color before, Color after) { wasNotified[0] = true; }

            @Override
            public void onIndexShift(int newIndex) { }
        });

        controller.randomizeColor(0);

        assertTrue(wasNotified[0]);
    }

    @Test
    public void whenColorIsDeleted_changeListenersAreNotified() {
        Controller controller = setupSingleColorController();

        final boolean[] deletedWasNotified = {false};
        final boolean[] shiftedWasNotified = {false};

        controller.addColorChangeListener(new ColorChangeListener() {
            @Override
            public int getIndex() { return 0; }

            @Override
            public void onChange(Color before, Color after) {}

            @Override
            public void onIndexShift(int newIndex) {
                if (newIndex == - 1) {
                    deletedWasNotified[0] = true;
                }
            }
        });
        controller.addColorChangeListener(new ColorChangeListener() {
            @Override
            public int getIndex() { return 1; }

            @Override
            public void onChange(Color before, Color after) {}

            @Override
            public void onIndexShift(int newIndex) {
                if (newIndex == 0) {
                    shiftedWasNotified[0] = true;
                }
            }
        });

        controller.deleteColor(0);

        assertTrue(deletedWasNotified[0]);
        assertTrue(shiftedWasNotified[0]);
    }

    @Test
    public void whenColorIsDeleted_deletionListenersAreNotified() {
        Controller controller = setupSingleColorController();
        final boolean[] wasNotified = {false};

        controller.addColorDeletionListener(removedIndex -> wasNotified[0] = true);

        controller.deleteColor(0);

        assertTrue(wasNotified[0]);
    }

    @Test
    public void whenColorIsCreated_creationListenersAreNotified() {
        Controller controller = setupSingleColorController();
        final boolean[] wasNotified = {false};

        controller.addColorCreationListener(addedIndex -> {
            if (addedIndex == 1) {
                wasNotified[0] = true;
            }
        });

        controller.addColor();

        assertTrue(wasNotified[0]);
    }
}
