package proofOfConceptV2;

import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.UiFactory;
import userinterface.components.*;
import userinterface.components.Button;
import userinterface.components.Label;
import userinterface.listeners.ButtonListener;
import userinterface.listeners.HoverListener;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class ColorContainer implements ButtonListener, UiComponentWrapper, HoverListener {
    private final Random random = new Random();
    private final UiContainer uiContainer;
    private final Label label;
    private final Button button;
    private final Label hoverOnly;

    public ColorContainer(UiFactory uiFactory) {
        uiContainer = uiFactory.createVerticalUiContainer();
        uiContainer.setMinimumSize(new Size(100, 100));
        uiContainer.setPreferredSize(new Size(200, 150));
        uiContainer.setMaximumSize(new Size(200, 150));
        uiContainer.setContentAlignment(ContentAlignment.BOTTOM_CENTER);
        uiContainer.addHoverListener(this);

        label = uiFactory.createLabel("UNINITIALIZED");
        uiContainer.addChild(label);
        try {
            BufferedImage image = ImageIO.read(new File("img.png"));
            button = uiFactory.createButton("Randomize");
            button.setMinimumSize(new Size(30, 120));
            button.setPreferredSize(new Size(30, 120));
            button.setImage(image);
            button.addButtonListener(this);
            button.addHoverListener(this);

            uiContainer.addChild(button);

            randomizeColor();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        hoverOnly = uiFactory.createLabel("Now you see me");
        hoverOnly.hide();
        uiContainer.addChild(hoverOnly);
    }

    @Override
    public void onClick(Button source) {
        // == instead of .equals bc we only care about this specific button
        if (source == button) {
            randomizeColor();
        }
    }

    private void randomizeColor() {
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        Color color = new Color(r, g, b);

        uiContainer.setBackground(color);
        label.setText(String.format("#%02X%02X%02X", r, g, b));
    }

    @Override
    public UiComponent getUiComponent() {
        return uiContainer;
    }

    @Override
    public void onHoverStart(UiComponent hovered) {
        if (hovered == button) {
            randomizeColor();
        }
        else if (hovered == uiContainer) {
            hoverOnly.show();
        }
    }

    @Override
    public void onHoverStop(UiComponent hovered) {
        if (hovered == button) {
            randomizeColor();
        }
        else if (hovered == uiContainer) {
            hoverOnly.hide();
        }
    }
}
