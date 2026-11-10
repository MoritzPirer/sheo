package userinterface.javafxinterface;

import javafx.scene.control.OverrunStyle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import shared.colorevaluation.ColorEvaluator;
import userinterface.ContentAlignment;
import userinterface.components.Label;

import java.awt.*;

class JavaFxLabel extends JavaFxComponent implements Label {
    private final javafx.scene.control.Label label;
    private boolean doAutomaticTextColor;

    public JavaFxLabel(String text) {
        label = new javafx.scene.control.Label(text);
        label.setTextOverrun(OverrunStyle.WORD_ELLIPSIS);
    }

    @Override
    public void setText(String text) {
        label.setText(text);
    }

    @Override
    public String getText() {
        return label.getText();
    }

    @Override
    public void setBackground(Color color) {
        javafx.scene.paint.Color javaFxColor = JavaFxConverter.toJavaFxColor(color);
        label.setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));

        if (doAutomaticTextColor) {
            double contrastWithBlack = ColorEvaluator.calculateContrast(color, Color.BLACK);
            double contrastWithWhite = ColorEvaluator.calculateContrast(color, Color.WHITE);

            if (contrastWithBlack > contrastWithWhite) {
                label.setTextFill(javafx.scene.paint.Color.BLACK);
            }
            else {
                label.setTextFill(javafx.scene.paint.Color.WHITE);
            }
        }
    }
    @Override
    public void enableAutomaticTextColor() {
        doAutomaticTextColor = true;
    }

    @Override
    public Region getJavaFxNode() {
        return label;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        label.setAlignment(JavaFxConverter.toPos(alignment));
    }

    @Override
    public String toString() {
        return "JavaFxLabel{\n" +
            "text=" + getText() + "\n" +
            "}";
    }
}
