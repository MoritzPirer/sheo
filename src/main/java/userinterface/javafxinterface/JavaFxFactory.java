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
        return new JavaFxVerticalContainer();
    }

    @Override
    public UiContainer createHorizontalUiContainer() {
        return new JavaFxHorizontalContainer();
    }

    @Override
    public Scene createScene(UiContainer uiContainer) {
        return new JavaFxScene(uiContainer);
    }

    @Override
    public UiApplication createUiApplication(String title) {
        return new JavaFxApplication(title);
    }
}
