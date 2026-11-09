package view.paletteview;

import controller.Controller;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.UiComponent;
import userinterface.components.UiSlot;
import view.paletteview.coloreditor.ColorEditor;

import java.awt.*;

public class InformationPanel {
    private final UiSlot container;
    private final ColorEditor colorEditor;
    private final Controller controller;

    public InformationPanel(UiFactory factory, Controller controller) {
        container = factory.createUiSlot();
        container.setMinimumSize(new Size(-1, 200));
        container.setPreferredSize(new Size(-1, 200));
        container.setMaximumSize(new Size(-1, 500));
        container.setBackground(new Color(0, 255, 0));
        colorEditor = new ColorEditor(factory, controller);
        this.controller = controller;
    }

    public UiComponent getUiComponent() {
        return container;
    }

    public void openEditor(int colorIndex) {
        container.setChild(colorEditor.getUiComponent());
        colorEditor.getUiComponent().setPercentageWidth(100.0);
        colorEditor.open(colorIndex);
        controller.addColorDeletionListener(removedIndex -> {
            if (removedIndex == colorIndex) {
                closeEditor();
            }
        });
    }

    public void closeEditor() {
        colorEditor.close();
        container.removeChild();
    }
}
