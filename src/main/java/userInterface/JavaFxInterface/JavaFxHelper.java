package userInterface.JavaFxInterface;

import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import userInterface.ContentAlignment;
import userInterface.SizeConfiguration;

class JavaFxHelper {
    public static void applySizeConfiguration(Region element, SizeConfiguration config) {
        if (config == null) {
            return;
        }
        if (config.maximum() != null) {
            element.setMaxSize(config.maximum().width(), config.maximum().height());
        }
        if (config.minimum() != null) {
            element.setMinSize(config.minimum().width(), config.minimum().height());
        }
        if (config.preferred() != null) {
            element.setPrefSize(config.preferred().width(), config.preferred().height());
        }
    }

    public static Pos toPos(ContentAlignment alignment) {
        return switch (alignment) {
            case TOP_LEFT -> Pos.TOP_LEFT;
            case TOP_CENTER -> Pos.TOP_CENTER;
            case TOP_RIGHT -> Pos.TOP_RIGHT;
            case CENTER_LEFT ->  Pos.CENTER_LEFT;
            case CENTER -> Pos.CENTER;
            case CENTER_RIGHT -> Pos.CENTER_RIGHT;
            case BOTTOM_LEFT -> Pos.BOTTOM_LEFT;
            case BOTTOM_CENTER -> Pos.BOTTOM_CENTER;
            case BOTTOM_RIGHT -> Pos.BOTTOM_RIGHT;
        };
    }
}
