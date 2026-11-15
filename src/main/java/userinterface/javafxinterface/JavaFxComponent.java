package userinterface.javafxinterface;

import javafx.animation.ScaleTransition;
import javafx.beans.binding.Bindings;
import javafx.scene.Parent;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import userinterface.CornerRadius;
import userinterface.Size;
import userinterface.listeners.HoverListener;
import userinterface.components.UiComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base for every component produced by the JavaFxFactory. Implements methods of UiComponent that only depend
 * on the Component's primary javaFx component
 */
abstract class JavaFxComponent implements UiComponent {
    ScaleTransition applyScaling;
    ScaleTransition resetScaling;
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
        Background background = getJavaFxNode().getBackground();
        if (background == null) {
            getJavaFxNode().setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));
            return;
        }

        List<BackgroundFill> fills = background.getFills();
        List<BackgroundFill> newFills = new ArrayList<>(fills.size());

        for (BackgroundFill fill : fills) {
            newFills.add(new BackgroundFill(javaFxColor, fill.getRadii(), fill.getInsets()));
        }

        getJavaFxNode().setBackground(new Background(newFills.toArray(BackgroundFill[]::new)));
    }

    @Override
    public void setCornerRadius(CornerRadius cornerRadius) {
        CornerRadii cornerRadii = JavaFxConverter.toCornerRadii(cornerRadius);
        Region node = getJavaFxNode();
        Background background = node.getBackground();
        Rectangle clip = new Rectangle();

        clip.widthProperty().bind(node.widthProperty());
        clip.heightProperty().bind(node.heightProperty());

        if (cornerRadius.isPercent()) {
            clip.arcWidthProperty()
                .bind(Bindings.min(node.widthProperty(), node.heightProperty())
                .multiply(cornerRadius.radius() * 2));
            clip.arcHeightProperty().bind(clip.arcWidthProperty());
        }
        else {
            clip.setArcWidth(cornerRadius.radius() * 2);
            clip.setArcHeight(cornerRadius.radius() * 2);

        }
        node.setClip(clip);
        if (background == null) {
            node.setBackground(new Background(new BackgroundFill(null, cornerRadii, null)));
            System.out.println(node.getBackground());
            return;
        }

        List<BackgroundFill> fills = background.getFills();
        List<BackgroundFill> newFills = new ArrayList<>(fills.size());

        for (BackgroundFill fill : fills) {
            newFills.add(new BackgroundFill(fill.getFill(), cornerRadii, fill.getInsets()));
        }

        node.setBackground(new Background(newFills.toArray(BackgroundFill[]::new)));
    }

    @Override
    public void scaleTemporarily(double factor) {
        if (factor <= 0) {
            throw new IllegalArgumentException("factor must be positive");
        }

        Region node = getJavaFxNode();
        applyScaling = new ScaleTransition(Duration.millis(150), node);
        applyScaling.setToX(factor);
        applyScaling.setToY(factor);

        resetScaling = new ScaleTransition(Duration.millis(150), node);
        resetScaling.setToX(1.0);
        resetScaling.setToY(1.0);

        node.toFront(); // Ensure tile renders on top of its neighbors
        resetScaling.stop();
        applyScaling.playFromStart();
    }

    @Override
    public void resetScale() {
        if (resetScaling == null) {
            return;
        }

        applyScaling.stop();
        resetScaling.playFromStart();
    }
}
