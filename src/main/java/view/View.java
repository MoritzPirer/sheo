package view;

import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.*;
import userinterface.components.Label;
import userinterface.javafxinterface.JavaFxFactory;
import userinterface.listeners.HoverListener;

import java.awt.*;
import java.util.List;

public class View {
    public View(String[] args) {
        UiFactory factory = new JavaFxFactory();

        UiApplication application = factory.createUiApplication("sheo");
        application.initialize(args);
        application.start(initializeScene(factory));
    }

    private Scene initializeScene(UiFactory factory) {
        UiContainer root = factory.createVerticalUiContainer();
        root.setBackground(new Color(250, 0, 250));
        root.setContentAlignment(ContentAlignment.CENTER);


        root.stretch();
        UiContainer toolBand = factory.createHorizontalUiContainer();
        toolBand.setBackground(new Color(0, 255, 0));

        UiContainer lowerHalf = factory.createHorizontalUiContainer();
        lowerHalf.setBackground(new Color(0, 0, 250));


        UiContainer colorSegment = factory.createHorizontalUiContainer();
        colorSegment.setContentAlignment(ContentAlignment.CENTER);
        for (int i = 0; i < 4; i++) {
            ColorCard card = new ColorCard(new Color(i * 20, i * 40, i * 60), factory);
            colorSegment.addChild(card.getUiComponent());
        }

        lowerHalf.addChild(colorSegment);

        colorSegment.setPercentageSize(100.0, 80.0);
        UiContainer informationPanel = factory.createVerticalUiContainer();
        informationPanel.setBackground(new Color(0, 250, 250));
        lowerHalf.addChild(informationPanel);
        informationPanel.setPercentageSize(100.0, 20.0);
        informationPanel.setMinimumSize(new Size(-1, 200));

        root.addChild(toolBand);
        root.addChild(lowerHalf);

        toolBand.setPercentageSize(25.0, 100.0);
        toolBand.setMaximumSize(new Size(175, -1));
        lowerHalf.setPercentageSize(75.0, 100.0);

        root.setContentAlignment(ContentAlignment.TOP_CENTER);
        return factory.createScene(root);
    }

    private UiContainer createColorCard(UiFactory factory) {
        UiContainer uiContainer = factory.createVerticalUiContainer();

        uiContainer.setMaximumSize(new Size(360, 240));

        UiContainer hoverMenu = createColorCardHoverMenu(factory);
        UiContainer colorInfo = createColorInfo(factory);

        uiContainer.addChild(hoverMenu);
        uiContainer.addChild(colorInfo);

        hoverMenu.setPercentageSize(70.0, 100.0);
        colorInfo.setPercentageSize(30.0, 100.0);
        return uiContainer;
    }

    private UiContainer createColorCardHoverMenu(UiFactory factory) {
        UiContainer hoverMenu = factory.createHorizontalUiContainer();
        hoverMenu.setContentAlignment(ContentAlignment.CENTER);
        hoverMenu.setBackground(new Color(255, 0, 0));

        List<UiComponent> components = List.of(
            factory.createButton("I"),
            factory.createButton("E"),
            factory.createButton("D"),
            factory.createButton("R")
        );

        for (UiComponent component : components) {
            hoverMenu.addChild(component);
            component.hide();
        }

        hoverMenu.addHoverListener(new HoverListener() {
            @Override
            public void onHoverStart(UiComponent hovered) {
                for (UiComponent component : hoverMenu.getChildren()) {
                    component.show();
                }
            }

            @Override
            public void onHoverStop(UiComponent hovered) {
                for (UiComponent component : hoverMenu.getChildren()) {
                    component.hide();
                }
            }
        });

        return hoverMenu;
    }

    private UiContainer createColorInfo(UiFactory factory) {
        UiContainer uiContainer = factory.createVerticalUiContainer();
        uiContainer.setContentAlignment(ContentAlignment.TOP_LEFT);
        uiContainer.setBackground(new Color(200, 200, 200));

        Label hexCode = factory.createLabel("#FFFFFF");
        uiContainer.addChild(hexCode);

        Label colorName = factory.createLabel("White");
        uiContainer.addChild(colorName);

        return uiContainer;
    }
}
