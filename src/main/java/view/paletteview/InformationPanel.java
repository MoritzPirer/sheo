package view.paletteview;

import controller.Controller;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.UiComponent;
import userinterface.components.UiSlot;
import view.paletteview.coloreditor.ColorEditor;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class InformationPanel {
    private final UiSlot container;
    private final ColorEditor colorEditor;
    private final Controller controller;
    private final List<InformationPanelListener> listeners = new ArrayList<>();
    private int activeIndex = -1;

    public InformationPanel(UiFactory factory, Controller controller) {
        container = factory.createUiSlot();
        container.setMinimumSize(new Size(-1, 200));
        container.setPreferredSize(new Size(-1, 200));
        container.setMaximumSize(new Size(-1, 500));
        container.setBackground(new Color(221, 221, 221));
        colorEditor = new ColorEditor(factory, controller);
        this.controller = controller;
    }

    public UiComponent getUiComponent() {
        return container;
    }

    public void toggleColorEditor(int colorIndex) {
        if (activeIndex == colorIndex) {
            closeEditor();
            return;
        }

        container.setChild(colorEditor.getUiComponent());
        colorEditor.getUiComponent().setPercentageWidth(100.0);
        colorEditor.open(colorIndex);
        controller.addColorDeletionListener(removedIndex -> {
            if (removedIndex == colorIndex) {
                closeEditor();
            }
        });

        if (activeIndex != -1) {
            listeners.forEach(listener -> listener.onColorEditorClose(activeIndex));
        }
        activeIndex = colorIndex;
        listeners.forEach(listener -> listener.onColorEditorOpen(colorIndex));
    }

    public void closeEditor() {
        colorEditor.close();
        container.removeChild();
        listeners.forEach(l -> l.onColorEditorClose(activeIndex));
        activeIndex = -1;
    }

    public void addListener(InformationPanelListener listener) {
        listeners.add(listener);
    }
}
