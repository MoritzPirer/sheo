package proofOfConceptV2;

import userinterface.javafxinterface.JavaFxFactory;
import userinterface.Size;
import userinterface.components.Scene;
import userinterface.components.UiApplication;
import userinterface.components.UiContainer;
import userinterface.UiFactory;

public class prototypeMain {
    public static void main(String[] args) throws Exception {
        new prototypeMain().run(args);
    }

    private void run(String[] args) throws Exception {
        UiFactory factory = new JavaFxFactory();

        UiApplication application = factory.createUiApplication("proofOfConceptV2");
        application.initialize(args);
        Scene scene = initializeScene(factory);
        application.start(scene);
    }


    private Scene initializeScene(UiFactory factory) {
        UiContainer root = factory.createVerticalUiContainer();
        root.setMinimumSize(new Size(50, 100));
        root.setPreferredSize(new Size(540, 860));
        root.setMaximumSize(new Size(1080, 1920));

        UiContainer toolBand = factory.createHorizontalUiContainer();
        toolBand.setMinimumSize(new Size(50, 100));
        toolBand.setPreferredSize(new Size(50, 860));
        toolBand.setMaximumSize(new Size(100, 1920));

        UiContainer colorSegment = factory.createHorizontalUiContainer();
        colorSegment.setMinimumSize(new Size(50, 100));
        colorSegment.setPreferredSize(new Size(440, 860));
        colorSegment.setMaximumSize(new Size(980, 1920));
        for (int i = 0; i < 4; i++) {
            ColorContainer container = new ColorContainer(factory);
            colorSegment.addChild(container);
        }

        root.addChild(toolBand);
        root.addChild(colorSegment);

        return factory.createScene(root);
    }
}
