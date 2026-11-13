package userinterface.javafxinterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.HBox;
import userinterface.ContentAlignment;
import userinterface.Padding;
import userinterface.components.UiComponent;
import userinterface.components.UiContainer;

import java.util.ArrayList;
import java.util.List;

class JavaFxHorizontalContainer extends JavaFxComponent implements UiContainer {
    private final HBox hBox = new HBox();
    private final List<UiComponent> components = new ArrayList<>();

    public JavaFxHorizontalContainer() {
        hBox.setFillHeight(false);
    }

    @Override
    public void addChild(UiComponent uiComponent) {
        addChild(components.size(), uiComponent);
    }

    @Override
    public void addChild(int index, UiComponent uiComponent) {
        if (uiComponent instanceof JavaFxComponent component) {
            if (index < 0 || index > components.size()) {
                throw new IndexOutOfBoundsException("Index: " + index + "is out of bounds for size: " + components.size());
            }

            components.add(index, uiComponent);
            hBox.getChildren().add(index, component.getJavaFxNode());
        }
        else {
            throw new IllegalArgumentException(uiComponent.getClass().getSimpleName() + " is not a JavaFxComponent");
        }
    }

    @Override
    public void removeChild(UiComponent uiComponent) {
        if (uiComponent instanceof JavaFxComponent component) {
            components.remove(component);
            hBox.getChildren().remove(component.getJavaFxNode());
        }
    }

    @Override
    public List<UiComponent> getChildren() {
        return components;
    }

    @Override
    public void setPadding(Padding padding) {
        Insets insets = JavaFxConverter.toInsets(padding);
        hBox.setPadding(insets);
    }

    @Override
    Region getJavaFxNode() {
        return hBox;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        Pos pos = JavaFxConverter.toPos(alignment);
        hBox.setAlignment(pos);
    }

    @Override
    public void setSpacing(double spacing) {
        hBox.setSpacing(spacing);
    }

}
