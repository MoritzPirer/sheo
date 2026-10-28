package userinterface.javafxinterface;

import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import userinterface.ContentAlignment;
import userinterface.Size;
import userinterface.components.Label;

import java.awt.*;

class JavaFxLabel extends JavaFxComponent implements Label {
    private final javafx.scene.control.Label label;

    public JavaFxLabel(String text) {
        label = new javafx.scene.control.Label(text);
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
    public Region getJavaFxNode() {
        return label;
    }


    @Override
    public void setBackground(Color color) {
        javafx.scene.paint.Color javaFxColor = JavaFxConverter.toJavaFxColor(color);
        label.setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));
    }

    @Override
    public void setPreferredSize(Size size) {
        label.setPrefSize(size.width(), size.height());
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
