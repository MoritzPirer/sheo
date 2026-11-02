package view.paletteview;

import controller.Controller;
import userinterface.UiFactory;
import userinterface.components.UiComponent;
import userinterface.components.UiSlot;

public class InformationPanel {
    private final UiSlot container;
    private final ColorEditor colorEditor;

    public InformationPanel(UiFactory factory, Controller controller) {
        container = factory.createUiSlot();
        colorEditor = new ColorEditor(factory, controller);
    }

    public UiComponent getUiComponent() {
        return container;
    }

    public void openEditor(int colorIndex) {
        container.setChild(colorEditor.getUiComponent());
        colorEditor.open(colorIndex);
    }

    public void closeEditor() {
        colorEditor.close();
        container.removeChild();
    }
}
