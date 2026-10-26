package view.paletteview;

import controller.Controller;
import controller.listeners.ColorChangeListener;
import shared.ColorUtility;
import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.Button;
import userinterface.components.Label;
import userinterface.components.UiComponent;
import userinterface.components.UiContainer;
import userinterface.listeners.HoverListener;

import java.awt.*;
import java.util.List;

public class ColorCard implements ColorChangeListener {
    private Color color;
    private final UiContainer uiContainer;
    private final UiContainer background;
    private Label hexCode;
    private Label colorName;

    private int index;

    public ColorCard(int index, Controller controller, UiFactory factory) {
        this.index = index;
        this.color = controller.getColor(index);

        uiContainer = factory.createVerticalUiContainer();

        uiContainer.setMinimumSize(new Size(180, 120));

        background = createColorCardHoverMenu(factory, controller);
        UiContainer colorInfo = createColorInfo(factory);

        uiContainer.addChild(background);
        uiContainer.addChild(colorInfo);

        background.setPercentageSize(70.0, 100.0);
        colorInfo.setPercentageSize(30.0, 100.0);
    }

    private UiContainer createColorCardHoverMenu(UiFactory factory, Controller controller) {
        UiContainer hoverMenu = factory.createHorizontalUiContainer();
        hoverMenu.setContentAlignment(ContentAlignment.CENTER);
        hoverMenu.setBackground(color);

        Button inspectButton = factory.createButton("I");
        inspectButton.setToolTip("Inspect");

        Button editButton = factory.createButton("E");
        editButton.setToolTip("Edit");

        Button deleteButton = factory.createButton("D");
        deleteButton.setToolTip("Delete");
        deleteButton.addButtonListener(b -> controller.deleteColor(index));

        Button randomizeButton = factory.createButton("R");
        randomizeButton.setToolTip("Randomize");
        randomizeButton.addButtonListener(b -> controller.randomizeColor(index));

        List<Button> buttons = List.of(inspectButton, editButton, deleteButton, randomizeButton);

        for (UiComponent component : buttons) {
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
    public int getIndex() {
        return index;
    }

    @Override
    public void onChange(Color before, Color after) {
        setColor(after);
    }

    @Override
    public void onIndexShift(int newIndex) {
       this.index = newIndex;
    }

    private void setColor(Color color) {
        this.color = color;
        background.setBackground(color);
        hexCode.setText(ColorUtility.colorToHex(color));
        colorName.setText("NAME OF " + ColorUtility.colorToHex(color)); //TODO connect to colorname module
    }
}
