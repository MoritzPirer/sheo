package view.paletteview;

import controller.Controller;
import shared.ColorUtility;
import userinterface.ContentAlignment;
import userinterface.Padding;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.*;
import userinterface.components.Button;
import userinterface.components.Label;
import userinterface.listeners.DropdownSelectionListener;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class PaletteView {
    private final List<ColorCard> colorCards = new ArrayList<>();
    private Toolbar toolbar;

    public void createListeners(Controller controller) {
        for (ColorCard card : colorCards) {
            controller.addColorChangeListener(card);
        }
    }

    public Scene initializeScene(UiFactory factory, Controller controller) {
        UiContainer root = factory.createVerticalUiContainer();
        root.setBackground(new Color(250, 0, 250));
        root.setContentAlignment(ContentAlignment.CENTER);
        root.stretch();
        root.setContentAlignment(ContentAlignment.TOP_CENTER);

        toolbar = new Toolbar(factory, controller);
        root.addChild(toolbar.getUiComponent());
        toolbar.getUiComponent().setPercentageSize(25.0, 100.0);
        toolbar.getUiComponent().setMaximumSize(new Size(175, -1));

        UiContainer lowerHalf = factory.createHorizontalUiContainer();
        root.addChild(lowerHalf);
        lowerHalf.setBackground(new Color(0, 0, 250));
        lowerHalf.setPercentageSize(75.0, 100.0);

        createColorSegment(factory, controller, lowerHalf);
        createInformationPanel(factory, controller, lowerHalf);

        return factory.createScene(root);
    }

    public void createColorSegment(UiFactory factory, Controller controller, UiContainer parent) {
        UiContainer colorSegment = factory.createRearrangingUiContainer(2, 6);
        parent.addChild(colorSegment);
        colorSegment.setContentAlignment(ContentAlignment.CENTER);
        colorSegment.setPadding(new Padding(15, 30));
        colorSegment.setSpacing(20.0);
        colorSegment.setPercentageSize(100.0, 80.0);

        for (int i = 0; i < controller.getColorCount(); i++) {
            ColorCard card = new ColorCard(i, controller, factory);
            colorCards.add(card);
            colorSegment.addChild(card.getUiComponent());
        }

        controller.addColorDeletionListener(removedIndex -> {
            ColorCard card = colorCards.remove(removedIndex);
            colorSegment.removeChild(card.getUiComponent());
        });

        controller.addColorCreationListener(addedIndex -> {
            ColorCard card = new ColorCard(addedIndex, controller, factory);
            colorCards.add(card);
            colorSegment.addChild(card.getUiComponent());
            controller.addColorChangeListener(card);
        });
    }

    public void createInformationPanel(UiFactory factory, Controller controller, UiContainer parent) {
        UiContainer informationPanel = factory.createVerticalUiContainer();
        parent.addChild(informationPanel);
        informationPanel.setBackground(new Color(255, 255, 255));
        informationPanel.setPercentageSize(100.0, 20.0);
        informationPanel.setContentAlignment(ContentAlignment.TOP_CENTER);
        informationPanel.setPadding(new Padding(15));

        UiSlot slot = factory.createUiSlot();
        slot.setMinimumSize(new Size(150, 150));
        informationPanel.addChild(slot);

        ColorPicker textBoxPicker = factory.createTextBoxColorPicker();
        ColorPicker canvasPicker = factory.createCanvasColorPicker();

        slot.setChild(textBoxPicker);
        Button toCanvas = factory.createButton("to canvas");
        informationPanel.addChild(toCanvas);
        toCanvas.addButtonListener(b -> slot.setChild(canvasPicker));
        toCanvas.setBackground(new Color(176, 11, 105));

        Button toTextBox = factory.createButton("to text box");
        informationPanel.addChild(toTextBox);
        toTextBox.addButtonListener(b -> slot.setChild(textBoxPicker));
        toTextBox.setBackground(new Color(176, 11, 105));
    }
}
