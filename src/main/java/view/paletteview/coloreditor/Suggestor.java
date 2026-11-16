package view.paletteview.coloreditor;

import javafx.util.Pair;
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
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Suggestor {
    private static final Size BUTTON_SIZE = new Size(20, 30);
    private static final Color CONFIRMATION_COLOR = new Color(58, 195, 32);
    private static final Duration CONFIRMATION_DURATION = Duration.ofMillis(250);
    private final UiContainer container;
    private final String name;
    private final Label[] labels;
    private final Function<Color, List<Pair<Color, String>>> suggestionProducer;

    private Suggestor(UiFactory factory, ColorEditor context, int labelCount, String name, Function<Color, List<Pair<Color, String>>> suggestionProducer) {
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
        label.setMinimumSize(new Size(27, 110));
        label.setPreferredSize(new Size(27, 110));
        label.enableAutomaticTextColor();
        label.setContentAlignment(ContentAlignment.CENTER);

        return label;
    }

    private Button createCopyButton(UiFactory factory, int index) {
        Button copyButton = factory.createButton("C");
        copyButton.setPreferredSize(BUTTON_SIZE);
        copyButton.setToolTip("Copy");

        copyButton.addButtonListener(source -> {
            CopyUtility.setSystemClipboardContent(labels[index].getText());
            copyButton.setTemporaryStyle(CONFIRMATION_COLOR, null, null, CONFIRMATION_DURATION);
        });

        return copyButton;
    }

    public Button createApplyButton(UiFactory factory, int index, ColorEditor context) {
        Button applyButton = factory.createButton("A");
        applyButton.setPreferredSize(BUTTON_SIZE);
        applyButton.addButtonListener(source ->
            context.onChange(null, null, ColorConverter.hexToColor(labels[index].getText()))
        );
        applyButton.setToolTip("Apply");

        return applyButton;
    }

    public static Suggestor createShadeSuggestor(UiFactory factory, ColorEditor context) {
        final int count = 7;
        return new Suggestor(factory, context, count, "Shade", c ->
            makeColorNamePairs(ColorGenerator.getColorShades(c, count))
        );
    }

    public static Suggestor createAnalogousSuggestor(UiFactory factory, ColorEditor context) {
        final int count = 3;
        return new Suggestor(factory, context, 3, "Analogous", c ->
            makeColorNamePairs(ColorGenerator.getAnalogousColors(c, (count - 1) / 2))
        );
    }

    public static Suggestor createComplementarySuggestor(UiFactory factory, ColorEditor context) {
        final int count = 2;
        return new Suggestor(factory, context, count, "Complementary", c ->
            makeColorNamePairs(new Color[]{c, ColorGenerator.getComplementColor(c)})
        );
    }

    public static Suggestor createSplitComplementarySuggestor(UiFactory factory, ColorEditor context) {
        final int count = 3;
        return new Suggestor(factory, context, count, "Split Complementary", c ->
            makeColorNamePairs(ColorGenerator.getSplitComplementaryColors(c))
        );
    }

    public static Suggestor createTriadicSuggestor(UiFactory factory, ColorEditor context) {
        final int count = 3;
        return new Suggestor(factory, context, count, "Triadic", c ->
            makeColorNamePairs(ColorGenerator.getTriadicColors(c))
        );
    }

    public static Suggestor createSquareSuggestor(UiFactory factory, ColorEditor context) {
        final int count = 4;
        return new Suggestor(factory, context, count, "Square", c ->
            makeColorNamePairs(ColorGenerator.getSquareColors(c))
        );
    }

    public static Suggestor createSimilarSuggestor(UiFactory factory, ColorEditor context) {
        final int count = 4;
        return new Suggestor(factory, context, count, "Similar", c -> context.getSimilarColors(c, count));
    }

    private static List<Pair<Color, String>> makeColorNamePairs(Color[] colors) {
        return Arrays.stream(colors).map(Suggestor::makeColorNamePair).toList();
    }

    private static Pair<Color, String> makeColorNamePair(Color color) {
        return new Pair<>(color, ColorConverter.colorToHexString(color));
    }

    public static Suggestor[] createAllSuggestors(UiFactory factory, ColorEditor context) {
        return new Suggestor[]{
            createShadeSuggestor(factory, context),
            createAnalogousSuggestor(factory, context),
            createComplementarySuggestor(factory, context),
            createSplitComplementarySuggestor(factory, context),
            createTriadicSuggestor(factory, context),
            createSquareSuggestor(factory, context),
            createSimilarSuggestor(factory, context)
        };
    }

    UiContainer getUiComponent() {
        return container;
    }

    protected String getName() {
        return name;
    }

    public void updateSuggestions(Color color) {
        List<Pair<Color, String>> colors = suggestionProducer.apply(color);

        assert colors.size() == labels.length;

        for (int i = 0; i < labels.length; i++) {
            labels[i].setBackground(colors.get(i).getKey());
            labels[i].setText(colors.get(i).getValue());
        }
    }
}
