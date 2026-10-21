package userInterface.JavaFxInterface;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import userInterface.ContentAlignment;
import userInterface.Size;
import userInterface.components.UiComponent;
import userInterface.components.UiComponentWrapper;
import userInterface.components.UiContainer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class JavaFxContainer implements UiContainer, JavaFxComponent {
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
    public void addChild(UiComponentWrapper uiComponentWrapper) {
        addChild(uiComponentWrapper.getUiComponent());
    }

    @Override
    public void setBackground(Color color) {
        javafx.scene.paint.Color javaFxColor = JavaFxConverter.toJavaFxColor(color);
        pane.setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));
    }

    @Override
    public void setMinimumSize(Size size) {
        pane.setMinSize(size.width(), size.height());
    }

    @Override
    public void setMaximumSize(Size size) {
        pane.setMaxSize(size.width(), size.height());
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
    public Parent getJavaFxNode() {
        return pane;
    }
}

