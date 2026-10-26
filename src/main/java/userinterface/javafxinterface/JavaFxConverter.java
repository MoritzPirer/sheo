package userinterface.javafxinterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import userinterface.ContentAlignment;
import userinterface.Padding;

class JavaFxConverter {
    public static javafx.scene.paint.Color toJavaFxColor(java.awt.Color color) {
        return javafx.scene.paint.Color.rgb(
                color.getRed(),
                color.getGreen(),
                color.getBlue()
        );
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

    public static Insets toInsets(Padding padding) {
        return new Insets(padding.top(), padding.right(), padding.bottom(), padding.left());
    }
}
