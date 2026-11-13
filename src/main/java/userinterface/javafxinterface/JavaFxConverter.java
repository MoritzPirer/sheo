package userinterface.javafxinterface;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.CornerRadii;
import javafx.util.Duration;
import userinterface.ContentAlignment;
import userinterface.CornerRadius;
import userinterface.Padding;

/**
 * Utility class to convert between JavaFx's types and custom or standard library (i.e. not third party dependent) types
 */
class JavaFxConverter {
    public static javafx.scene.paint.Color toJavaFxColor(java.awt.Color color) {
        return javafx.scene.paint.Color.rgb(
            color.getRed(),
            color.getGreen(),
            color.getBlue()
        );
    }

    public static java.awt.Color toAwtColor(javafx.scene.paint.Color javaFxColor) {
        return new java.awt.Color(
            percentToAbsolute(javaFxColor.getRed()),
            percentToAbsolute(javaFxColor.getGreen()),
            percentToAbsolute(javaFxColor.getBlue())
        );
    }

    /**
     * convert a percentage to a value in the range [0-255] i.e. and RGB color channel
     * @param percent the percentage to convert
     * @return the corresponding value in the range [0-255]
     */
    private static int percentToAbsolute(double percent) {
        return (int) (percent * 255);
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

    public static javafx.util.Duration toDuration(java.time.Duration duration) {
        return new Duration(duration.toMillis());
    }

    public static CornerRadii toCornerRadii(CornerRadius cornerRadius) {
        return new CornerRadii(
            cornerRadius.radius(),
            cornerRadius.radius(),
            cornerRadius.radius(),
            cornerRadius.radius(),
            cornerRadius.isPercent()
        );
    }
}
