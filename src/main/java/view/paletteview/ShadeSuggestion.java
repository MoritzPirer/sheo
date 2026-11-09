package view.paletteview;

import shared.CopyUtility;
import shared.colorconversion.ColorConverter;
import shared.colorgeneration.ColorGenerator;
import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.Label;
import userinterface.components.Button;
import userinterface.components.UiContainer;

import java.awt.*;

class ShadeSuggestion implements Suggestion {
    private static final int SHADE_COUNT = 7;
    private final UiContainer container;
    private final UiContainer[] shadeContainers;
    private final Label[] labels;

    private Color activeColor;

    public ShadeSuggestion(UiFactory factory) {

        container = factory.createVerticalUiContainer();
        labels = new Label[SHADE_COUNT];
        shadeContainers = new UiContainer[SHADE_COUNT];

        for (int index = 0; index < SHADE_COUNT; index++) {
            shadeContainers[index] = factory.createHorizontalUiContainer();
            labels[index] = factory.createLabel("");
            labels[index].setPreferredSize(new Size(25, 100));
            labels[index].enableAutomaticTextColor();
            Button button = factory.createButton("C");
            button.setPreferredSize(new Size(20, 30));

            int finalIndex = index; // required for lambda
            button.addButtonListener(source ->
                    CopyUtility.setSystemClipboardContent(labels[finalIndex].getText())
            );

            shadeContainers[index].addChild(labels[index]);
            shadeContainers[index].addChild(button);

            container.addChild(shadeContainers[index]);
        }
        container.setContentAlignment(ContentAlignment.CENTER);
    }

    @Override
    public UiContainer getUiComponent() {
        return container;
    }

    @Override
    public String getName() {
        return "Shade";
    }

    @Override
    public void onSelect() {
        generateSuggestions();
    }

    @Override
    public void onColorChange(Color newColor) {
        activeColor = newColor;
        generateSuggestions();
    }

    private void generateSuggestions() {
        Color[] shadeColors = ColorGenerator.getColorShades(activeColor, SHADE_COUNT);
        for (int i = 0; i < SHADE_COUNT; i++) {
            shadeContainers[i].setBackground(shadeColors[i]);
            labels[i].setBackground(shadeColors[i]);
            labels[i].setText(ColorConverter.colorToHexString(shadeColors[i]));
        }
    }
}
