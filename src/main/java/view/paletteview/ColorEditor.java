package view.paletteview;

import controller.Controller;
import controller.listeners.ColorChangeListener;
import shared.ColorUtility;
import shared.CopyUtility;
import userinterface.ContentAlignment;
import userinterface.Size;
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

    private final UiSlot colorPickerSlot;
    private final Map<String, ColorPicker> colorPickers;

    private ColorPicker selectedColorPicker;
    private ColorPicker textBox;

    private final Controller controller;
    private int activeIndex = -1;
    private boolean isUpdating = false;

    public ColorEditor(UiFactory factory, Controller controller) {
        this.controller = controller;
        colorPickers = createColorPickers(factory);
        dropdown = factory.createDropdownSelection(colorPickers.keySet().toArray(new String[0]));
        dropdown.addDropdownSelectionListener(this);

        selectedColorPicker = colorPickers.get(dropdown.getValue());

        colorPickerSlot = factory.createUiSlot();
        colorPickerSlot.setChild(selectedColorPicker);

        container = factory.createVerticalUiContainer();
        container.addChild(dropdown);
        container.addChild(colorPickerSlot);

        UiContainer secondaryAction = createSecondaryColorAction(factory);
        container.addChild(secondaryAction);

        container.setContentAlignment(ContentAlignment.TOP_CENTER);
        container.setBackground(new Color(244, 117, 117));
    }

    private Map<String, ColorPicker> createColorPickers(UiFactory factory) {
        Map<String, ColorPicker> colorPickers = new LinkedHashMap<>(); //LinkedHashMap to preserve this order
        colorPickers.put("Canvas", factory.createCanvasColorPicker());
        colorPickers.put("RGB", factory.createRgbColorPicker());
        colorPickers.put("HSV", factory.createHsvColorPicker());
        colorPickers.put("HSL", factory.createHslColorPicker());

        return colorPickers;
    }

    //TODO come up with a better name
    private UiContainer createSecondaryColorAction(UiFactory factory) {
        textBox = factory.createTextBoxColorPicker();
        textBox.addListener(this);

        Button copyButton = createCopyButton(factory);
        Button randomizeButton = createRandomizeButton(factory, controller);

        UiContainer container = factory.createHorizontalUiContainer();

        container.addChild(textBox);
        container.addChild(randomizeButton);
        container.addChild(copyButton);

        
        textBox.setPreferredSize(new Size(20, 100));
        randomizeButton.setPreferredSize(new Size(20, 30));
        copyButton.setPreferredSize(new Size(20, 30));

        return container;
    }

    private Button createCopyButton(UiFactory factory) {
        Button copyButton = factory.createButton("C");
        copyButton.setToolTip("Copy Hex");
        copyButton.addButtonListener(b -> {
            Color color = textBox.getColor();
            String colorString = ColorUtility.colorToHex(color);
            CopyUtility.setSystemClipboardContent(colorString);
            //TODO: visual confirmation
            //TODO: icon
        });

        return copyButton;
    }

    private Button createRandomizeButton(final UiFactory factory, Controller controller) {
        Button randomizeButton = factory.createButton("R");
        randomizeButton.setToolTip("Randomize");
        randomizeButton.addButtonListener(b -> controller.randomizeColor(activeIndex));

        return randomizeButton;
    }

    public void open(int colorIndex) {
        activeIndex = colorIndex;
        selectedColorPicker.addListener(this);
        selectedColorPicker.setColor(controller.getColor(colorIndex));
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
        selectedColorPicker = colorPickers.get(source.getValue());
        selectedColorPicker.addListener(this);
        colorPickerSlot.setChild(selectedColorPicker);
        if (activeIndex != -1) {
            selectedColorPicker.setColor(controller.getColor(activeIndex));
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
        if (isUpdating) {
            return;
        }
        if (before.equals(after)) {
            return;
        }

        controller.setColor(activeIndex, after);
    }

    @Override
    public void onChange(Color before, Color after) {
        isUpdating = true;

        textBox.setColor(after);
        selectedColorPicker.setColor(after);

        isUpdating = false;
    }

    @Override
    public void onIndexShift(int newIndex) {
        if (newIndex == COLOR_WAS_DELETED) {
            close();
            return;
        }
        activeIndex = newIndex;
    }

}
