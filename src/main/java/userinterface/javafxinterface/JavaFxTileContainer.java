package userinterface.javafxinterface;

import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import userinterface.ContentAlignment;
import userinterface.Padding;
import userinterface.components.TileContainer;
import userinterface.components.UiComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JavaFxTileContainer extends JavaFxComponent implements TileContainer {
    private final TilePane tilePane = new TilePane();
    private final List<UiComponent> components = new ArrayList<>();

    @Override
    public void setTileWidth(double width) {
        tilePane.setPrefWidth(width);
    }

    @Override
    public void setTileHeight(double height) {
        tilePane.setPrefHeight(height);
    }

    @Override
    public void setPreferredColumns(int columns) {
        tilePane.setPrefColumns(columns);
    }

    /// ///

    @Override
    public void addChild(UiComponent uiComponent) {
        addChild(components.size(), uiComponent);
    }

    @Override
    public void addChild(int index, UiComponent uiComponent) {
        if (uiComponent instanceof JavaFxComponent fxComponent) {
            if (index < 0 || index > components.size()) {
                throw new IndexOutOfBoundsException("Index: " + index + "is out of bounds for size: " + components.size());
            }

            components.add(index, fxComponent);
            tilePane.getChildren().add(index, fxComponent.getJavaFxNode());
        }
        else {
            throw new IllegalArgumentException(uiComponent.getClass().getSimpleName() + " is not a JavaFxComponent");
        }
    }

    @Override
    public void removeChild(UiComponent uiComponent) {
        if (uiComponent instanceof JavaFxComponent component) {
            components.remove(component);
            tilePane.getChildren().remove(component.getJavaFxNode());
        }
    }

    @Override
    public List<UiComponent> getChildren() {
        return components;
    }

    @Override
    public void setPadding(Padding padding) {
        Insets insets = JavaFxConverter.toInsets(padding);
        tilePane.setPadding(insets);
    }

    @Override
    public void setSpacing(double spacing) {
        tilePane.setHgap(spacing);
        tilePane.setVgap(spacing);
    }

    @Override
    Region getJavaFxNode() {
        return tilePane;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        tilePane.setAlignment(JavaFxConverter.toPos(alignment));
    }
}
