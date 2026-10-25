package view;

import model.ColorChangeListener;
import model.Palette;
import shared.ColorUtility;
import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.Button;
import userinterface.components.Label;
import userinterface.components.UiComponent;
import userinterface.components.UiContainer;
import userinterface.listeners.ButtonListener;
import userinterface.listeners.HoverListener;

import java.awt.*;
import java.util.List;

public class ColorCard implements ColorChangeListener {
    private Color color;
    private final UiContainer uiContainer;
    private final UiContainer background;
    private Label hexCode;
    private Label colorName;

    private final int index;

    public ColorCard(int index, Palette palette, UiFactory factory) {
        this.index = index;
        this.color = palette.getColor(index);

        uiContainer = factory.createVerticalUiContainer();

        uiContainer.setMinimumSize(new Size(180, 120));

        background = createColorCardHoverMenu(factory, palette);
        UiContainer colorInfo = createColorInfo(factory);

        uiContainer.addChild(background);
        uiContainer.addChild(colorInfo);

        background.setPercentageSize(70.0, 100.0);
        colorInfo.setPercentageSize(30.0, 100.0);
    }

    private UiContainer createColorCardHoverMenu(UiFactory factory, Palette palette) {
        UiContainer hoverMenu = factory.createHorizontalUiContainer();
        hoverMenu.setContentAlignment(ContentAlignment.CENTER);
        hoverMenu.setBackground(color);

        List<Button> components = List.of(
                factory.createButton("I"),
                factory.createButton("E"),
                factory.createButton("D"),
                factory.createButton("R")
        );

        components.get(3).addButtonListener(source -> palette.randomizeColor(index));

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
        this.hexCode = factory.createLabel(hexString);
        uiContainer.addChild(hexCode);

        this.colorName = factory.createLabel("White");
        uiContainer.addChild(colorName);

        return uiContainer;
    }

    public UiContainer getUiComponent() {
        return uiContainer;
    }

    @Override
    public void onChange(int index, Color before, Color after) {
        if (this.index != index) {
            return;
        }

        setColor(after);
    }

    private void setColor(Color color) {
        this.color = color;
        background.setBackground(color);
        hexCode.setText(ColorUtility.colorToHex(color));
        colorName.setText("NAME OF " + ColorUtility.colorToHex(color)); //TODO connect to colorname module
    }
}
