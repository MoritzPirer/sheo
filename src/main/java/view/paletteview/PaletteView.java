package view.paletteview;

import controller.Controller;
import userinterface.Padding;
import userinterface.UiFactory;
import userinterface.components.*;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class PaletteView implements InformationPanelListener {
    private final List<ColorCard> colorCards = new ArrayList<>();
    private Toolbar toolbar;
    private InformationPanel informationPanel;

    public Scene initializeScene(UiFactory factory, Controller controller) {
        SectionContainer root = factory.createSectionContainer();
        root.setBackground(new Color(165, 165, 172));

        toolbar = new Toolbar(factory, controller);
        GridContainer colorSegment = createColorSegment(factory, controller);
        informationPanel = new InformationPanel(factory, controller);
        informationPanel.addListener(this);

        root.setTop(toolbar.getUiComponent());
        root.setCenter(colorSegment);
        root.setRight(informationPanel.getUiComponent());
        informationPanel.getUiComponent().setPercentageWidth(20.0);

        return factory.createScene(root);
    }

    private GridContainer createColorSegment(UiFactory factory, Controller controller) {
        final int rows = 2;
        final int columns = Controller.MAX_COLOR_COUNT / rows;
        GridContainer colorSegment = factory.createGridContainer(rows, columns);
        colorSegment.setBackground(new Color(251, 251, 251, 251));
        colorSegment.setSpacing(15.0);
        colorSegment.setPadding(new Padding(15,20));

        for (int i = 0; i < controller.getColorCount(); i++) {
            ColorCard card = new ColorCard(i, controller, factory);
            colorCards.add(card);
            colorSegment.addChild(card.getUiComponent());
            controller.addColorChangeListener(card);
        }

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

        return colorSegment;
    }

    public void toggleColorEditor(int colorIndex) {
        informationPanel.toggleColorEditor(colorIndex);
    }

    @Override
    public void onColorEditorOpen(int colorIndex) {
        colorCards.get(colorIndex).scaleUp();
    }

    @Override
    public void onColorEditorClose(int colorIndex) {
        colorCards.get(colorIndex).resetScale();
    }
}
