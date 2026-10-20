package userInterface.JavaFxInterface;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import userInterface.ContentAlignment;
import userInterface.SizeConfiguration;
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
    public JavaFxContainer setBackground(Color color) {
        javafx.scene.paint.Color javaFxColor = javafx.scene.paint.Color.rgb(
                color.getRed(),
                color.getGreen(),
                color.getBlue()
        );

        pane.setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));
        return this;
    }

    @Override
    public JavaFxContainer setSizeConfiguration(SizeConfiguration config) {
        if (config == null) {
            return this;
        }
        if (config.maximum() != null) {
            pane.setMaxSize(config.maximum().width(), config.maximum().height());
        }
        if (config.minimum() != null) {
            pane.setMinSize(config.minimum().width(), config.minimum().height());
        }
        if (config.preferred() != null) {
            pane.setPrefSize(config.preferred().width(), config.preferred().height());
        }

        return this;
    }

    @Override
    public JavaFxContainer setContentAlignment(ContentAlignment alignment) {
        Pos pos = JavaFxHelper.toPos(alignment);
        if (pane instanceof HBox hBox) {
            hBox.setAlignment(pos);
        }
        else if (pane instanceof VBox vBox) {
            vBox.setAlignment(pos);
        }

        return this;
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

