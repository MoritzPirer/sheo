package view.paletteview;

import controller.Controller;
import controller.listeners.ColorChangeListener;
import shared.colorconversion.ColorConverter;
import userinterface.ContentAlignment;
import userinterface.CornerRadius;
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
    private static final double FOCUS_SCALE_UP_FACTOR = 1.1;
    private Color color;
    private final UiContainer uiContainer;
    private final UiContainer background;
    private Label hexCode;
    private Label colorName;

    private int index;
    private final Controller controller;

    private boolean isOpened = false;

    public ColorCard(int index, Controller controller, UiFactory factory) {
        this.index = index;
        this.color = controller.getColor(index);
        this.controller = controller;

        background = createColorCardHoverMenu(factory, controller);
        UiContainer colorInfo = createColorInfo(factory);

        uiContainer = factory.createVerticalUiContainer();
        uiContainer.setMinimumSize(new Size(180, 60));
        uiContainer.setMaximumSize(new Size(480, 360));

        uiContainer.addChild(background);
        uiContainer.addChild(colorInfo);
        uiContainer.setCornerRadius(new CornerRadius(0.1, true));
        uiContainer.addHoverListener(new HoverListener() {
            @Override
            public void onHoverStart(UiComponent hovered) {
                uiContainer.scaleTemporarily(FOCUS_SCALE_UP_FACTOR);
            }

            @Override
            public void onHoverStop(UiComponent hovered) {
                if (!isOpened) {
                    uiContainer.resetScale();
                }
            }
        });

        background.setPercentageSize(70.0, 100.0);
        colorInfo.setPercentageSize(30.0, 100.0);
    }

    private UiContainer createColorCardHoverMenu(UiFactory factory, Controller controller) {
        UiContainer hoverMenu = factory.createHorizontalUiContainer();

        List<Button> buttons = List.of(
            createInspectButton(factory, controller),
            createEditButton(factory, controller),
            createDeleteButton(factory, controller)
        );

        for (Button button : buttons) {
            hoverMenu.addChild(button);
            button.hide();
        }

        hoverMenu.setContentAlignment(ContentAlignment.CENTER);
        hoverMenu.setBackground(color);
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

    private Button createInspectButton(UiFactory factory, Controller controller) {
        Button inspectButton = factory.createButton("I");
        inspectButton.setToolTip("Inspect");
        inspectButton.addButtonListener(b -> controller.openInspector());

        return inspectButton;
    }

    private Button createEditButton(UiFactory factory, Controller controller) {
        Button editButton = factory.createButton("E");
        editButton.setToolTip("Edit");
        editButton.addButtonListener(b -> controller.toggleColorEditor(index));

        return editButton;
    }

    private Button createDeleteButton(UiFactory factory, Controller controller) {
        Button deleteButton = factory.createButton("D");
        deleteButton.setToolTip("Delete");
        deleteButton.addButtonListener(b -> controller.deleteColor(index));

        return deleteButton;
    }

    private UiContainer createColorInfo(UiFactory factory) {
        UiContainer uiContainer = factory.createVerticalUiContainer();
        uiContainer.setContentAlignment(ContentAlignment.TOP_LEFT);
        uiContainer.setBackground(new Color(200, 200, 200));

        String hexString = ColorConverter.colorToHexString(color);
        this.hexCode = factory.createLabel(hexString);
        uiContainer.addChild(hexCode);

        this.colorName = factory.createLabel(controller.getColorName(color));
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

    public void setColor(Color color) {
        this.color = color;
        background.setBackground(color);
        hexCode.setText(ColorConverter.colorToHexString(color));
        colorName.setText(controller.getColorName(color));
    }

    public void scaleUp() {
        isOpened = true;
        uiContainer.scaleTemporarily(FOCUS_SCALE_UP_FACTOR);
    }

    public void resetScale() {
        isOpened = false;
        uiContainer.resetScale();
    }
}
