package userinterface.javafxinterface;

import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import userinterface.Size;
import userinterface.listeners.HoverListener;
import userinterface.components.UiComponent;

abstract class JavaFxComponent implements UiComponent {
    abstract javafx.scene.layout.Region getJavaFxNode();

    @Override
    public void addHoverListener(HoverListener hoverListener) {
        getJavaFxNode().hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                hoverListener.onHoverStart(this);
            }
            else {
                hoverListener.onHoverStop(this);
            }
        });
    }

    @Override
    public void setMaximumSize(Size size) {
        Region component = getJavaFxNode();
        if (size.height() >= 0) {
            if (component.maxHeightProperty().isBound()) {
                component.maxHeightProperty().unbind();
            }
            component.setMaxHeight(size.height());
        }
        if (size.width() >= 0) {
            if (component.minWidthProperty().isBound()) {
                component.minWidthProperty().unbind();
            }
            component.setMaxWidth(size.width());
        }
    }

    @Override
    public void setMinimumSize(Size size) {
        Region component = getJavaFxNode();
        if (size.height() >= 0) {
            component.setMinHeight(size.height());
        }
        if (size.width() >= 0) {
            component.setMinWidth(size.width());
        }
    }

    @Override
    public void setPreferredSize(Size size) {
        Region component = getJavaFxNode();
        if (size.height() >= 0) {
            component.setPrefHeight(size.height());
        }
        if (size.width() >= 0) {
            component.setPrefWidth(size.width());
        }
    }

    @Override
    public void setPercentageSize(double heightPercentage, double widthPercentage) {
        if (heightPercentage < 0 || heightPercentage > 100) {
            throw new IllegalArgumentException("heightPercentage must be between 0 and 100");
        }
        if (widthPercentage < 0 || widthPercentage > 100) {
            throw new IllegalArgumentException("widthPercentage must be between 0 and 100");
        }

        Region child = getJavaFxNode();
        Parent parent = child.getParent();

        if (parent == null) {
            throw new IllegalArgumentException("Node does not have a parent!");
        }
        if (!(parent instanceof Region region)) {
            throw new IllegalArgumentException("Parent is not a Region!");
        }

        child.prefHeightProperty().bind(region.heightProperty().multiply(heightPercentage / 100.0));
        child.prefWidthProperty().bind(region.widthProperty().multiply(widthPercentage / 100.0));

        child.maxHeightProperty().bind(region.heightProperty());
        child.maxWidthProperty().bind(region.widthProperty());

        child.minHeightProperty().bind(region.heightProperty().multiply(heightPercentage / 100.0));
        child.minWidthProperty().bind(region.widthProperty().multiply(widthPercentage / 100.0));
    }

    @Override
    public void setToolTip(String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(Duration.millis(250));
        Tooltip.install(getJavaFxNode(), tooltip);
    }

    @Override
    public void hide() {
        getJavaFxNode().setVisible(false);
    }

    @Override
    public void show() {
        getJavaFxNode().setVisible(true);
    }
}
