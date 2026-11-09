package view.paletteview.coloreditor;

import userinterface.UiFactory;
import userinterface.components.ColorPicker;
import userinterface.components.DropdownSelection;
import userinterface.components.UiContainer;
import userinterface.components.UiSlot;
import userinterface.listeners.ColorPickerListener;
import userinterface.listeners.DropdownSelectionListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

class ColorPickerGroup implements ColorPickerListener, DropdownSelectionListener {
    private final List<ColorPickerListener> listeners = new ArrayList<>();

    private final UiContainer container;
    private final DropdownSelection dropdown;
    private final UiSlot slot;
    private Color activeColor;

    private final Map<String, ColorPicker> colorPickers;
    private ColorPicker selected;

    public ColorPickerGroup(UiFactory factory) {
        container = factory.createVerticalUiContainer();
        colorPickers = createColorPickers(factory);

        dropdown = factory.createDropdownSelection(colorPickers.keySet().toArray(String[]::new));
        dropdown.addDropdownSelectionListener(this);
        selected = colorPickers.get(dropdown.getValue());

        slot = factory.createUiSlot();
        slot.setChild(selected);

        container.addChildren(dropdown, slot);
        dropdown.setPercentageWidth(100.0);
    }

    private Map<String, ColorPicker> createColorPickers(UiFactory factory) {
        Map<String, ColorPicker> colorPickers = new LinkedHashMap<>(); //LinkedHashMap to preserve this order
        colorPickers.put("Canvas", factory.createCanvasColorPicker());
        colorPickers.put("RGB", factory.createRgbColorPicker());
        colorPickers.put("HSV", factory.createHsvColorPicker());
        colorPickers.put("HSL", factory.createHslColorPicker());

        for (ColorPicker colorPicker : colorPickers.values()) {
            colorPicker.addListener(this);
        }

        return colorPickers;
    }

    @Override
    public void onChange(ColorPicker source, Color before, Color after) {
        listeners.forEach(l -> l.onChange(source, before, after));
    }

    public void setColor(Color color) {
        activeColor = color;
        selected.setColor(color);
    }

    public Color getColor() {
        return activeColor;
    }

    public void addListener(ColorPickerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void onSelectionChange(DropdownSelection source, String before, String after) {
        assert source == dropdown;

        selected = colorPickers.get(after);
        slot.setChild(selected);
        selected.setColor(activeColor);
    }

    @Override
    public void onOptionsChange(DropdownSelection source, List<String> before, List<String> after) {
        throw new IllegalStateException("Options are not allowed to change here");
    }

    public UiContainer getUiComponent() {
        return container;
    }
}
