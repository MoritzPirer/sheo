package userinterface.javafxinterface;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import userinterface.UiFactory;
import userinterface.components.*;

public class JavaFxFactory implements UiFactory {
    @Override
    public Button createButton(String text) {
        return new JavaFxButton(text);
    }

    @Override
    public Label createLabel(String text) {
        return new JavaFxLabel(text);
    }

    @Override
    public UiContainer createVerticalUiContainer() {
        VBox container = new VBox();
        container.setFillWidth(false);

        return new JavaFxContainer(container);
    }

    @Override
    public UiContainer createHorizontalUiContainer() {
        HBox container = new HBox();
        container.setFillHeight(false);

        return new JavaFxContainer(container);
    }

    @Override
    public Scene createScene(UiContainer uiContainer) {
        if (uiContainer instanceof JavaFxContainer) {
            return new JavaFxScene(uiContainer);
        }

        throw new IllegalArgumentException("UiContainer was not created by this factory");
    }

    @Override
    public UiApplication createUiApplication(String title) {
        return new JavaFxApplication(title);
    }
}
