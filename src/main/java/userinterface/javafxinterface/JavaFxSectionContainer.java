package userinterface.javafxinterface;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import userinterface.ContentAlignment;
import userinterface.components.SectionContainer;
import userinterface.components.UiComponent;

public class JavaFxSectionContainer extends JavaFxComponent implements SectionContainer {
    private final BorderPane container = new BorderPane();


    @Override
    Region getJavaFxNode() {
        return container;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        BorderPane.setAlignment(container, JavaFxConverter.toPos(alignment));
    }

    @Override
    public void setCenter(UiComponent component) {
        JavaFxComponent fxComponent = convertOrThrow(component);
        container.setCenter(fxComponent.getJavaFxNode());
    }

    @Override
    public void setTop(UiComponent component) {
        JavaFxComponent fxComponent = convertOrThrow(component);
        container.setTop(fxComponent.getJavaFxNode());
    }

    @Override
    public void setBottom(UiComponent component) {
        JavaFxComponent fxComponent = convertOrThrow(component);
        container.setBottom(fxComponent.getJavaFxNode());
    }

    @Override
    public void setLeft(UiComponent component) {
        JavaFxComponent fxComponent = convertOrThrow(component);
        container.setLeft(fxComponent.getJavaFxNode());
    }

    @Override
    public void setRight(UiComponent component) {
        JavaFxComponent fxComponent = convertOrThrow(component);
        container.setRight(fxComponent.getJavaFxNode());
    }

    private JavaFxComponent convertOrThrow(UiComponent component) {
        if (component instanceof JavaFxComponent fxComponent) {
            return fxComponent;
        }

        throw new IllegalArgumentException(component.getClass().getSimpleName() + "is not a JavaFxComponent");
    }
}
