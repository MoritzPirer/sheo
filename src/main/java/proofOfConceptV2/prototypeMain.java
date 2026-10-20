package proofOfConceptV2;

import userInterface.JavaFxInterface.JavaFxFactory;
import userInterface.Size;
import userInterface.SizeConfiguration;
import userInterface.components.Scene;
import userInterface.components.UiApplication;
import userInterface.components.UiContainer;
import userInterface.UiFactory;

public class prototypeMain {
    public static void main(String[] args) throws Exception {
        new prototypeMain().run(args);
    }

    private void run(String[] args) throws Exception {
        UiFactory factory = new JavaFxFactory();

        UiApplication application = factory.createUiApplication("proofOfConceptV2");
        application.initialize(args);
        Scene scene = initializeScene(factory);
        System.out.println(scene);
        application.start(scene);
    }


    private Scene initializeScene(UiFactory factory) {
        UiContainer root = factory.createVerticalUiContainer(new SizeConfiguration(
            new Size(50, 100),
            new Size(540, 860),
            new Size(1080, 1920)
        ));
        UiContainer toolBand = factory.createHorizontalUiContainer(new SizeConfiguration(
                new Size(50, 100),
                new Size(50, 860),
                new Size(100, 1920)
        ));

        UiContainer colorSegment = factory.createHorizontalUiContainer(new SizeConfiguration(
                new Size(50, 100),
                new Size(440, 860),
                new Size(980, 1920)
        ));
        for (int i = 0; i < 4; i++) {
            ColorContainer container = new ColorContainer(factory);
            colorSegment.addChild(container);
        }

        root.addChild(toolBand);
        root.addChild(colorSegment);

        return factory.createScene(root);
    }
}
