package view.paletteview;

import controller.Controller;
import controller.listeners.ColorChangeListener;
import shared.colorconversion.ColorConverter;
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

    private DropdownSelection colorPickerDropdown;
    private UiSlot colorPickerSlot;
    private Map<String, ColorPicker> colorPickers;
    private ColorPicker selectedColorPicker;

    private ColorPicker textBox;

    ColorSuggestionHandler colorSuggestionHandler;

    private final Controller controller;
    private int activeIndex = -1;
    private boolean isUpdating = false;

    public ColorEditor(UiFactory factory, Controller controller) {
        this.controller = controller;

        container = factory.createVerticalUiContainer();
        container.setContentAlignment(ContentAlignment.TOP_CENTER);
        container.setBackground(new Color(244, 117, 117));

        createColorSelection(factory);
        createSecondaryColorAction(factory);
        colorSuggestionHandler = new ColorSuggestionHandler(factory, Color.red, this);
        container.addChild(colorSuggestionHandler.getUiComponent());
        colorSuggestionHandler.getUiComponent().setPercentageWidth(100.0);
    }

    private void createColorSelection(UiFactory factory) {
        colorPickers = createColorPickers(factory);
        colorPickerDropdown = factory.createDropdownSelection(colorPickers.keySet().toArray(new String[0]));
        colorPickerDropdown.addDropdownSelectionListener(this);

        selectedColorPicker = colorPickers.get(colorPickerDropdown.getValue());

        colorPickerSlot = factory.createUiSlot();
        colorPickerSlot.setChild(selectedColorPicker);

        container.addChild(colorPickerDropdown);
        container.addChild(colorPickerSlot);
        colorPickerDropdown.setPercentageWidth(100.0);
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
    private void createSecondaryColorAction(UiFactory factory) {
        textBox = factory.createTextBoxColorPicker();
        textBox.addListener(this);

        Button copyButton = createCopyButton(factory);
        Button randomizeButton = createRandomizeButton(factory, controller);

        UiContainer secondaryColorActionContainer = factory.createHorizontalUiContainer();

        secondaryColorActionContainer.addChild(textBox);
        secondaryColorActionContainer.addChild(randomizeButton);
        secondaryColorActionContainer.addChild(copyButton);

        textBox.setMinimumSize(new Size(20, 100));
        randomizeButton.setMinimumSize(new Size(20, 30));
        copyButton.setMinimumSize(new Size(20, 30));

        container.addChild(secondaryColorActionContainer);
    }

    private Button createCopyButton(UiFactory factory) {
        Button copyButton = factory.createButton("C");
        copyButton.setToolTip("Copy Hex");
        copyButton.addButtonListener(b -> {
            Color color = textBox.getColor();
            String colorString = ColorConverter.colorToHexString(color);
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
        colorSuggestionHandler.setActiveColor(controller.getColor(colorIndex));
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
        assert source == colorPickerDropdown : "ColorEditor listening to unknown DropdownSelection";
        selectedColorPicker = colorPickers.get(source.getValue());
        selectedColorPicker.addListener(this);
        colorPickerSlot.setChild(selectedColorPicker);
        if (activeIndex != -1) {
            selectedColorPicker.setColor(controller.getColor(activeIndex));
        }
    }

    @Override
    public void onOptionsChange(DropdownSelection source, List<String> before, List<String> after) {
        assert source == colorPickerDropdown : "ColorEditor listening to unknown DropdownSelection";
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
        if (after.equals(before)) {
            return;
        }

        controller.setColor(activeIndex, after);
    }

    @Override
    public void onChange(Color before, Color after) {
        isUpdating = true;

        textBox.setColor(after);
        selectedColorPicker.setColor(after);
        colorSuggestionHandler.setActiveColor(after);

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
