package userinterface.javafxinterface;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import userinterface.ContentAlignment;
import userinterface.Padding;
import userinterface.components.UiComponent;
import userinterface.components.UiContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class JavaFxVerticalContainer extends JavaFxComponent implements UiContainer {
    private final VBox vBox = new VBox();
    private final List<UiComponent> components = new ArrayList<>();

    public JavaFxVerticalContainer() {
        vBox.setFillWidth(false);
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
            vBox.getChildren().add(index, component.getJavaFxNode());
        }
        else {
            throw new IllegalArgumentException(uiComponent.getClass().getSimpleName() + " is not a JavaFxComponent");
        }
    }

    @Override
    public void removeChild(UiComponent uiComponent) {
        if (uiComponent instanceof JavaFxComponent component) {
            components.remove(component);
            vBox.getChildren().remove(component.getJavaFxNode());
        }
    }

    @Override
    public List<UiComponent> getChildren() {
        return components;
    }

    @Override
    public void stretch() {
        vBox.setFillWidth(true);
    }

    @Override
    public void setPadding(Padding padding) {
        Insets insets = JavaFxConverter.toInsets(padding);
        vBox.setPadding(insets);
    }

    @Override
    Region getJavaFxNode() {
        return vBox;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        Pos pos = JavaFxConverter.toPos(alignment);
        vBox.setAlignment(pos);
    }

    @Override
    public void setSpacing(double spacing) {
        vBox.setSpacing(spacing);
    }
}
