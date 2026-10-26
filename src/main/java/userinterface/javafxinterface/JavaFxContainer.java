package userinterface.javafxinterface;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.components.UiComponent;
import userinterface.components.UiComponentWrapper;
import userinterface.components.UiContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class JavaFxContainer extends JavaFxComponent implements UiContainer {
    private final Pane pane;
    private final List<UiComponent> components;

    public JavaFxContainer(Pane pane) {
        this.pane = pane;
        components = new ArrayList<>();
    }

    @Override
    public void addChild(UiComponent uiComponent) {
        if (uiComponent instanceof JavaFxComponent component) {
            components.add(uiComponent);
            pane.getChildren().add(component.getJavaFxNode());
        }
        else {
            throw new IllegalArgumentException(uiComponent.getClass().getSimpleName() + " is not a JavaFxComponent");
        }
    }

    @Override
    public void addChild(int index, UiComponent uiComponent) {
        if (uiComponent instanceof JavaFxComponent component) {
            if (index < 0 || index >= components.size()) {
                throw new IndexOutOfBoundsException("Index: " + index + "is out of bounds for size: " + components.size());
            }

            components.add(index, uiComponent);
            pane.getChildren().add(index, component.getJavaFxNode());
        }
        else {
            throw new IllegalArgumentException(uiComponent.getClass().getSimpleName() + " is not a JavaFxComponent");
        }
    }

    @Override
    public void removeChild(UiContainer uiComponent) {
        if (uiComponent instanceof JavaFxComponent component) {
            components.remove(component);
            pane.getChildren().remove(component.getJavaFxNode());
        }
    }

    @Override
    public List<UiComponent> getChildren() {
        return components;
    }

    @Override
    public void addChild(UiComponentWrapper uiComponentWrapper) {
        addChild(uiComponentWrapper.getUiComponent());
    }

    @Override
    public void setBackground(Color color) {
        javafx.scene.paint.Color javaFxColor = JavaFxConverter.toJavaFxColor(color);
        pane.setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));
    }

    @Override
    public void setPreferredSize(Size size) {
        pane.setPrefSize(size.width(), size.height());
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        Pos pos = JavaFxConverter.toPos(alignment);
        if (pane instanceof HBox hBox) {
            hBox.setAlignment(pos);
        }
        else if (pane instanceof VBox vBox) {
            vBox.setAlignment(pos);
        }
    }

    @Override
    public void hide() {
       pane.setVisible(false);
    }

    @Override
    public void show() {
        pane.setVisible(true);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("JavaFxContainer{\ncomponents=");
        for (UiComponent uiComponent : components) {
            builder.append(uiComponent.toString())
                .append("\n");
        }
        if (components.isEmpty()) {
            builder.append("<no components>\n");
        }
        builder.append("}");
        return builder.toString();
    }

    @Override
    public Region getJavaFxNode() {
        return pane;
    }

    @Override
    public void stretch() {
        if (pane instanceof HBox hBox) {
            hBox.setFillHeight(true);
        }
        else if (pane instanceof VBox vBox) {
            vBox.setFillWidth(true);
        }
    }
}

