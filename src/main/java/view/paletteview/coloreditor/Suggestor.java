package view.paletteview.coloreditor;

import shared.CopyUtility;
import shared.colorconversion.ColorConverter;
import shared.colorgeneration.ColorGenerator;
import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.Button;
import userinterface.components.UiContainer;
import userinterface.components.Label;

import java.awt.*;
import java.util.function.Function;

public class Suggestor {
    private final UiContainer container;
    private final String name;
    private final Label[] labels;
    private final Function<Color, Color[]> suggestionProducer;

    private Suggestor(UiFactory factory, ColorEditor context, int labelCount, String name, Function<Color, Color[]> suggestionProducer) {
        this.suggestionProducer = suggestionProducer;
        this.name = name;
        labels = new Label[labelCount];

        container = factory.createVerticalUiContainer();
        container.setContentAlignment(ContentAlignment.CENTER);

        for (int index = 0; index < labelCount; index++) {
            UiContainer rowContainer = factory.createHorizontalUiContainer();
            labels[index] = createLabel(factory);

            Button copyButton = createCopyButton(factory, index);
            Button applyButton = createApplyButton(factory, index, context);

            rowContainer.addChildren(labels[index], copyButton, applyButton);
            container.addChild(rowContainer);
        }
    }

    private Label createLabel(UiFactory factory) {
        Label label = factory.createLabel("");
        label.setPreferredSize(new Size(25, 100));
        label.enableAutomaticTextColor();

        return label;
    }

    private Button createCopyButton(UiFactory factory, int index) {
        Button copyButton = factory.createButton("C");
        copyButton.setPreferredSize(new Size(20, 30));
        copyButton.addButtonListener(source ->
                CopyUtility.setSystemClipboardContent(labels[index].getText())
        );

        return copyButton;
    }

    public Button createApplyButton(UiFactory factory, int index, ColorEditor context) {
        Button applyButton = factory.createButton("A");
        applyButton.setPreferredSize(new Size(20, 30));
        applyButton.addButtonListener(source ->
            context.onChange(null, null, ColorConverter.hexToColor(labels[index].getText()))
        );

        return applyButton;
    }

    public static Suggestor createShadeSuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 7, "Shade", c -> ColorGenerator.getColorShades(c, 7));
    }

    public static Suggestor createAnalogousSuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 3, "Analogous", c -> ColorGenerator.getAnalogousColors(c, 1));
    }

    public static Suggestor createComplementarySuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 2, "Complementary", c -> new Color[]{c, ColorGenerator.getComplementColor(c)});
    }

    public static Suggestor createSplitComplementarySuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 3, "Split Complementary", ColorGenerator::getSplitComplementaryColors);
    }

    public static Suggestor createTriadicSuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 3, "Triadic", ColorGenerator::getTriadicColors);
    }

    public static Suggestor createSquareSuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 4, "Square", ColorGenerator::getSquareColors);
    }

    public static Suggestor[] createAllSuggestors(UiFactory factory, ColorEditor context) {
        return new Suggestor[]{
            createShadeSuggestor(factory, context),
            createAnalogousSuggestor(factory, context),
            createComplementarySuggestor(factory, context),
            createSplitComplementarySuggestor(factory, context),
            createTriadicSuggestor(factory, context),
            createSquareSuggestor(factory, context)
        };
    }

    UiContainer getUiComponent() {
        return container;
    }

    protected String getName() {
        return name;
    }

    public void updateSuggestions(Color color) {
        Color[] colors = suggestionProducer.apply(color);

        assert colors.length == labels.length;

        for (int i = 0; i < labels.length; i++) {
            labels[i].setBackground(colors[i]);
            labels[i].setText(ColorConverter.colorToHexString(colors[i]));
        }
    }
}
