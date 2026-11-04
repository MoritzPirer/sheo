package userinterface.javafxinterface;

import javafx.scene.control.ScrollPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import userinterface.ContentAlignment;
import userinterface.Padding;
import userinterface.components.UiComponent;
import userinterface.components.UiContainer;

import java.util.ArrayList;
import java.util.List;

class JavaFxRearrangingContainer extends JavaFxComponent implements UiContainer {
    private final TilePane tilePane;
    private final ScrollPane scrollPane;
    private final List<UiComponent> components = new ArrayList<>();

    JavaFxRearrangingContainer(int preferredRowCount, int preferredColumnCount) {
        tilePane = new TilePane();
        tilePane.setPrefColumns(preferredColumnCount);
        tilePane.setPrefRows(preferredRowCount);
        scrollPane = new ScrollPane(tilePane);

        tilePane.setPrefTileWidth(Region.USE_COMPUTED_SIZE);
        tilePane.setPrefTileHeight(Region.USE_COMPUTED_SIZE);

        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(false);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

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

            components.add(index, uiComponent);
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
    public void stretch() {
        // TODO: check if this can be applied logically
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
        return scrollPane;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        Pos pos = JavaFxConverter.toPos(alignment);
        tilePane.setAlignment(pos);
        tilePane.setTileAlignment(pos);
    }
}
