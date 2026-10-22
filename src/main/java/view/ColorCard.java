package view;

import shared.ColorUtility;
import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.Label;
import userinterface.components.UiComponent;
import userinterface.components.UiContainer;
import userinterface.listeners.HoverListener;

import java.awt.*;
import java.util.List;

public class ColorCard {
    private Color color;
    private final UiContainer uiContainer;

    public ColorCard(Color color, UiFactory factory) {
        this.color = color;

        uiContainer = factory.createVerticalUiContainer();

        uiContainer.setMaximumSize(new Size(360, 240));

        UiContainer hoverMenu = createColorCardHoverMenu(factory);
        UiContainer colorInfo = createColorInfo(factory);

        uiContainer.addChild(hoverMenu);
        uiContainer.addChild(colorInfo);

        hoverMenu.setPercentageSize(70.0, 100.0);
        colorInfo.setPercentageSize(30.0, 100.0);
    }

    private UiContainer createColorCardHoverMenu(UiFactory factory) {
        UiContainer hoverMenu = factory.createHorizontalUiContainer();
        hoverMenu.setContentAlignment(ContentAlignment.CENTER);
        hoverMenu.setBackground(color);

        java.util.List<UiComponent> components = List.of(
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

        String hexString = ColorUtility.colorToHex(color);
        userinterface.components.Label hexCode = factory.createLabel(hexString);
        uiContainer.addChild(hexCode);

        Label colorName = factory.createLabel("White");
        uiContainer.addChild(colorName);

        return uiContainer;
    }

    public UiContainer getUiComponent() {
        return uiContainer;
    }
}
