package view.paletteview;

import controller.Controller;
import controller.listeners.ColorChangeListener;
import userinterface.UiFactory;
import userinterface.components.Button;
import userinterface.components.ColorPicker;
import userinterface.components.DropdownSelection;
import userinterface.components.UiContainer;
import userinterface.components.UiSlot;
import userinterface.listeners.ColorPickerListener;
import userinterface.listeners.DropdownSelectionListener;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ColorEditor implements DropdownSelectionListener, ColorPickerListener, ColorChangeListener {
    private final UiContainer container;
    private final DropdownSelection dropdown;
    private final Map<String, ColorPicker> colorPickers;
    private ColorPicker activeColorPicker;
    private final ColorPicker textBox;
    private final UiSlot colorPickerSlot;

    private final Controller controller;
    private int activeIndex = -1;
    private boolean isUpdating = false;

    public ColorEditor(UiFactory factory, Controller controller) {
        this.controller = controller;
        colorPickers = new LinkedHashMap<>();
        colorPickers.put("Canvas", factory.createCanvasColorPicker());
        colorPickers.put("RGB", factory.createRgbColorPicker());
        colorPickers.put("HSV", factory.createHsvColorPicker());
        colorPickers.put("HSL", factory.createHslColorPicker());

        dropdown = factory.createDropdownSelection(colorPickers.keySet().toArray(new String[0]));
        activeColorPicker = colorPickers.get(dropdown.getValue());

        colorPickerSlot = factory.createUiSlot();

        container = factory.createVerticalUiContainer();
        container.addChild(dropdown);
        container.addChild(colorPickerSlot);
        colorPickerSlot.setChild(activeColorPicker);
        dropdown.addDropdownSelectionListener(this);

        textBox = factory.createTextBoxColorPicker();
        container.addChild(textBox);
        textBox.addListener(this);

        Button randomizeButton = createRandomizeButton(factory, controller);
        container.addChild(randomizeButton);
    }

    public void open(int colorIndex) {
        activeIndex = colorIndex;
        activeColorPicker.addListener(this);
        activeColorPicker.setColor(controller.getColor(colorIndex));
        controller.addColorChangeListener(this);
    }

    public void close() {
        activeIndex = -1; //TODO Check if needed
    }

    public UiContainer getUiComponent() {
        return container;
    }

    @Override
    public void onSelectionChange(DropdownSelection source, String before, String after) {
        assert source == dropdown: "ColorEditor listening to unknown DropdownSelection";
        activeColorPicker = colorPickers.get(source.getValue());
        activeColorPicker.addListener(this);
        colorPickerSlot.setChild(activeColorPicker);
        if (activeIndex != -1) {
            activeColorPicker.setColor(controller.getColor(activeIndex));
        }
    }

    @Override
    public void onOptionsChange(DropdownSelection source, List<String> before, List<String> after) {
        assert source == dropdown: "ColorEditor listening to unknown DropdownSelection";
        throw new IllegalStateException("Options should never change for this dropdown");
    }

    @Override
    public int getIndex() {
        return activeIndex;
    }

    @Override
    public void onChange(ColorPicker source, Color before, Color after) {
        //change by activeColorPicker or textBox

        if (isUpdating) {
            return;
        }
        if (before.equals(after)) {
            return;
        }

        if (source == activeColorPicker) {
            textBox.setColor(after);
        }
        else if (source == textBox) {
            activeColorPicker.setColor(after);
        }

        controller.setColor(activeIndex, after);
    }

    @Override
    public void onChange(Color before, Color after) {
        isUpdating = true;
        textBox.setColor(after);
        activeColorPicker.setColor(after);
        isUpdating = false;
    }

    @Override
    public void onIndexShift(int newIndex) {
        activeIndex = newIndex;
    }

    private Button createRandomizeButton(final UiFactory factory, Controller controller) {
        Button randomizeButton = factory.createButton("R");
        randomizeButton.setToolTip("Randomize");
        randomizeButton.addButtonListener(b -> controller.randomizeColor(activeIndex));

        return randomizeButton;
    }
}
