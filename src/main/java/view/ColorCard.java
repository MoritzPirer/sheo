package view;

import controller.Controller;
import model.listeners.ColorChangeListener;
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

        List<Button> components = List.of(
                factory.createButton("I"), //INSPECT
                factory.createButton("E"), //EDIT
                factory.createButton("D"), //DELETE
                factory.createButton("R")  //RANDOMIZE
        );


        components.get(2).addButtonListener(b -> controller.deleteColor(index));
        components.get(3).addButtonListener(b -> controller.randomizeColor(index));

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
