package view.paletteview;

import controller.Controller;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.UiComponent;
import userinterface.components.UiSlot;

public class InformationPanel {
    private final UiSlot container;
    private final ColorEditor colorEditor;
    private final Controller controller;

    public InformationPanel(UiFactory factory, Controller controller) {
        container = factory.createUiSlot();
        container.setMinimumSize(new Size(Integer.MAX_VALUE, 250));
        container.setPreferredSize(new Size(Integer.MAX_VALUE, 250));
        container.setMaximumSize(new Size(Integer.MAX_VALUE, 500));
        colorEditor = new ColorEditor(factory, controller);
        this.controller = controller;
    }

    public UiComponent getUiComponent() {
        return container;
    }

    public void openEditor(int colorIndex) {
        container.setChild(colorEditor.getUiComponent());
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
