package view.paletteview;

import controller.Controller;
import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.*;
import view.ColorCard;

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
        toolbar = new Toolbar(factory, controller);

        UiContainer lowerHalf = factory.createHorizontalUiContainer();
        lowerHalf.setBackground(new Color(0, 0, 250));


        UiContainer colorSegment = factory.createHorizontalUiContainer();
        colorSegment.setContentAlignment(ContentAlignment.CENTER);
        for (int i = 0; i < controller.getColorCount(); i++) {
            ColorCard card = new ColorCard(i, controller, factory);
            colorCards.add(card);
            colorSegment.addChild(card.getUiComponent());
        }

        lowerHalf.addChild(colorSegment);
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

        colorSegment.setPercentageSize(100.0, 80.0);
        UiContainer informationPanel = factory.createVerticalUiContainer();
        informationPanel.setBackground(new Color(0, 250, 250));
        lowerHalf.addChild(informationPanel);
        informationPanel.setPercentageSize(100.0, 20.0);
        informationPanel.setMinimumSize(new Size(-1, 200));

        root.addChild(toolbar.getUiComponent());
        root.addChild(lowerHalf);

        toolbar.getUiComponent().setPercentageSize(25.0, 100.0);
        toolbar.getUiComponent().setMaximumSize(new Size(175, -1));
        lowerHalf.setPercentageSize(75.0, 100.0);

        root.setContentAlignment(ContentAlignment.TOP_CENTER);
        return factory.createScene(root);
    }
}
