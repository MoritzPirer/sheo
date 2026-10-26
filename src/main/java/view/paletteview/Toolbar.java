package view.paletteview;

import controller.Controller;
import userinterface.ContentAlignment;
import userinterface.UiFactory;
import userinterface.components.UiComponent;
import userinterface.components.UiContainer;
import userinterface.components.Button;

import java.util.ArrayList;
import java.util.List;

public class Toolbar {
    private final UiContainer container;
    private final List<Button> buttons;

    public Toolbar(UiFactory factory, Controller controller) {
        this.container = factory.createHorizontalUiContainer();
        this.container.setContentAlignment(ContentAlignment.CENTER_LEFT);
        this.buttons = new ArrayList<>();
        createAddButton(factory, controller);
    }

    private void createAddButton(UiFactory factory, Controller controller) {
        Button button = factory.createButton("Add");
        buttons.add(button);
        container.addChild(button);
        button.addButtonListener(source -> {
            if (source != button) {
                return;
            }

            controller.addColor();
        });
    }

    public UiComponent getUiComponent() {
        return container;
    }
}
