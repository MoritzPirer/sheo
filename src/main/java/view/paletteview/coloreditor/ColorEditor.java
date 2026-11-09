package view.paletteview.coloreditor;

import controller.Controller;
import controller.listeners.ColorChangeListener;
import shared.colorconversion.ColorConverter;
import shared.CopyUtility;
import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.Button;
import userinterface.components.ColorPicker;
import userinterface.components.UiContainer;
import userinterface.listeners.ColorPickerListener;

import java.awt.*;

public class ColorEditor implements ColorPickerListener, ColorChangeListener {
    private final UiContainer container;

    private ColorPicker textBox;

    ColorPickerGroup colorPickerGroup;
    ColorSuggestionHandler colorSuggestionHandler;

    private final Controller controller;
    private int activeIndex = -1;
    private boolean isUpdating = false;

    public ColorEditor(UiFactory factory, Controller controller) {
        this.controller = controller;

        container = factory.createVerticalUiContainer();
        container.setContentAlignment(ContentAlignment.TOP_CENTER);
        container.setBackground(new Color(244, 117, 117));

        colorPickerGroup = new ColorPickerGroup(factory);
        container.addChild(colorPickerGroup.getUiComponent());
        createSecondaryColorAction(factory);

        colorSuggestionHandler = new ColorSuggestionHandler(factory, this);
        container.addChild(colorSuggestionHandler.getUiComponent());
        colorSuggestionHandler.getUiComponent().setPercentageWidth(100.0);
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
        colorPickerGroup.addListener(this);
        colorPickerGroup.setColor(controller.getColor(colorIndex));
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
        colorPickerGroup.setColor(after);
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
