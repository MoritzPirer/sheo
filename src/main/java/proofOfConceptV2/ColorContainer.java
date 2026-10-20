package proofOfConceptV2;

import userInterface.ContentAlignment;
import userInterface.Size;
import userInterface.SizeConfiguration;
import userInterface.UiFactory;
import userInterface.components.*;
import userInterface.components.Button;
import userInterface.components.Label;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Random;

public class ColorContainer implements ButtonListener, UiComponentWrapper {
    private final Random random = new Random();
    private final UiContainer uiContainer;
    private final Label label;
    private final Button button;

    public ColorContainer(UiFactory uiFactory) {
        uiContainer = uiFactory.createVerticalUiContainer(new SizeConfiguration(
            new Size(100, 100),
            new Size(200, 150),
            new Size(200, 150)
        ));
        uiContainer.setContentAlignment(ContentAlignment.BOTTOM_CENTER);

        label = uiFactory.createLabel("UNINITIALIZED");
        uiContainer.addChild(label);
        try {
            BufferedImage image = ImageIO.read(new File("img.png"));
            button = uiFactory.createButton("Randomize");
            button.setSizeConfiguration(new SizeConfiguration(
                new Size(30, 80),
                new Size(30, 120),
                null //new Size(30, 80)
            ));
            button.setImage(image);
            button.addListener(this);
            uiContainer.addChild(button);

            randomizeColor();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
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
}
