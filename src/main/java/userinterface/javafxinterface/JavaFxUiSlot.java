package userinterface.javafxinterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import userinterface.ContentAlignment;
import userinterface.Padding;
import userinterface.components.UiComponent;
import userinterface.components.UiSlot;

class JavaFxUiSlot extends JavaFxComponent implements UiSlot {
    private final StackPane container = new StackPane();
    private UiComponent child = null;

    @Override
    public void setChild(UiComponent uiComponent) {
        if (!(uiComponent instanceof JavaFxComponent fxComponent)) {
            throw new IllegalArgumentException(uiComponent.getClass().getSimpleName() + " is not a JavaFxComponent");
        }

        if (child != null) {
            removeChild();
        }

        assert child == null;

        container.getChildren().add(fxComponent.getJavaFxNode());
        child = uiComponent;
    }

    @Override
    public void removeChild() {
        assert !container.getChildren().isEmpty();
        assert child != null;

        container.getChildren().removeFirst();
        child = null;
    }

    @Override
    public UiComponent getChild() {
        return child;
    }

    @Override
    public void setPadding(Padding padding) {
        Insets insets = JavaFxConverter.toInsets(padding);
        container.setPadding(insets);
    }

    @Override
    Region getJavaFxNode() {
        return container;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        Pos pos = JavaFxConverter.toPos(alignment);
        container.setAlignment(pos);
    }
}
