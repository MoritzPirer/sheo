package userinterface.javafxinterface;

import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import userinterface.Size;
import userinterface.listeners.HoverListener;
import userinterface.components.UiComponent;

import java.awt.*;

/**
 * Abstract base for every component produced by the JavaFxFactory. Implements methods of UiComponent that only depend
 * on the Component's primary javaFx component
 */
abstract class JavaFxComponent implements UiComponent {
    /**
     * Used by the methods of this class. Return the primary javaFx component of the UiComponent (for wrappers, this is
     * the wrapped component. For custom components, this is the container of the used components)
     * @return the primary component
     */
    abstract Region getJavaFxNode();

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
        else {
            component.setPrefHeight(Region.USE_COMPUTED_SIZE);
        }
        if (size.width() >= 0) {
            component.setPrefWidth(size.width());
        }
        else {
            component.setPrefWidth(Region.USE_COMPUTED_SIZE);
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

        //child.minHeightProperty().bind(region.heightProperty().multiply(heightPercentage / 100.0));
        //child.minWidthProperty().bind(region.widthProperty().multiply(widthPercentage / 100.0));

        child.prefHeightProperty().bind(region.heightProperty().multiply(heightPercentage / 100.0));
        child.prefWidthProperty().bind(region.widthProperty().multiply(widthPercentage / 100.0));

        child.maxHeightProperty().bind(region.heightProperty());
        child.maxWidthProperty().bind(region.widthProperty());
    }

   @Override
   public void setPercentageWidth(double widthPercentage) {
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

       child.minWidthProperty().bind(region.widthProperty().multiply(widthPercentage / 100.0));
       child.prefWidthProperty().bind(region.widthProperty().multiply(widthPercentage / 100.0));
       child.maxWidthProperty().bind(region.widthProperty());
   }

   @Override
   public void setPercentageHeight(double heightPercentage) {
       if (heightPercentage < 0 || heightPercentage > 100) {
           throw new IllegalArgumentException("heightPercentage must be between 0 and 100");
       }

       Region child = getJavaFxNode();
       Parent parent = child.getParent();

       if (parent == null) {
           throw new IllegalArgumentException("Node does not have a parent!");
       }
       if (!(parent instanceof Region region)) {
           throw new IllegalArgumentException("Parent is not a Region!");
       }

       child.minHeightProperty().bind(region.heightProperty().multiply(heightPercentage / 100.0));
       child.prefHeightProperty().bind(region.heightProperty().multiply(heightPercentage / 100.0));
       child.maxHeightProperty().bind(region.heightProperty());
   }

   @Override
   public void fillHeight() {
        getJavaFxNode().setMaxHeight(Double.MAX_VALUE);
        getJavaFxNode().setPrefHeight(Region.USE_COMPUTED_SIZE);
   }

   @Override
    public void fillWidth() {
        getJavaFxNode().setMaxWidth(Double.MAX_VALUE);
        getJavaFxNode().setPrefWidth(Region.USE_COMPUTED_SIZE);
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

    @Override
    public void setBackground(Color color) {
        javafx.scene.paint.Color javaFxColor = JavaFxConverter.toJavaFxColor(color);
        getJavaFxNode().setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));
    }
}
