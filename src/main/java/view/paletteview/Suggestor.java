package view.paletteview;

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

        container = factory.createVerticalUiContainer();
        labels = new Label[labelCount];

        for (int index = 0; index < labelCount; index++) {
            UiContainer rowContainer = factory.createHorizontalUiContainer();
            labels[index] = factory.createLabel("");
            labels[index].setPreferredSize(new Size(25, 100));
            labels[index].enableAutomaticTextColor();
            Button copyButton = factory.createButton("C");
            copyButton.setPreferredSize(new Size(20, 30));

            int finalIndex = index; // required for lambda
            copyButton.addButtonListener(source ->
                    CopyUtility.setSystemClipboardContent(labels[finalIndex].getText())
            );

            Button applyButton = factory.createButton("A");
            applyButton.setPreferredSize(new Size(20, 30));
            applyButton.addButtonListener(source ->
                context.onChange(null, null, ColorConverter.hexToColor(labels[finalIndex].getText()))
            );


            rowContainer.addChild(labels[index]);
            rowContainer.addChild(copyButton);
            rowContainer.addChild(applyButton);

            container.addChild(rowContainer);
        }
        container.setContentAlignment(ContentAlignment.CENTER);
    }

    public static Suggestor makeShadeSuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 7, "Shade", c -> ColorGenerator.getColorShades(c, 7));
    }

    public static Suggestor makeAnalogousSuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 3, "Analogous", c -> ColorGenerator.getAnalogousColors(c, 1));
    }

    public static Suggestor makeComplementSuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 2, "Complementary", c -> new Color[]{c, ColorGenerator.getComplementColor(c)});
    }

    public static Suggestor makeSplitComplementSuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 3, "Split Complementary", ColorGenerator::getSplitComplementaryColors);
    }

    public static Suggestor makeTriadicSuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 3, "Triadic", ColorGenerator::getTriadicColors);
    }

    public static Suggestor makeSquareSuggestor(UiFactory factory, ColorEditor context) {
        return new Suggestor(factory, context, 4, "Square", ColorGenerator::getSquareColors);
    }

    public static Suggestor[] makeAllSuggestors(UiFactory factory, ColorEditor context) {
        return new Suggestor[]{
            makeShadeSuggestor(factory, context),
            makeAnalogousSuggestor(factory, context),
            makeComplementSuggestor(factory, context),
            makeSplitComplementSuggestor(factory, context),
            makeTriadicSuggestor(factory, context),
            makeSquareSuggestor(factory, context)
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
