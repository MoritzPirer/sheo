package userInterface.JavaFxInterface;

import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import userInterface.ContentAlignment;
import userInterface.Size;
import userInterface.components.Label;

import java.awt.*;

class JavaFxLabel implements Label, JavaFxComponent {
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
    public Parent getJavaFxNode() {
        return label;
    }


    @Override
    public void setBackground(Color color) {
        javafx.scene.paint.Color javaFxColor = JavaFxConverter.toJavaFxColor(color);
        label.setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));
    }

    @Override
    public void setMinimumSize(Size size) {
        label.setMinSize(size.width(), size.height());
    }

    @Override
    public void setMaximumSize(Size size) {
        label.setMaxSize(size.width(), size.height());
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
    public void hide() {
        label.setVisible(false);
    }

    @Override
    public void show() {
        label.setVisible(true);
    }

    @Override
    public String toString() {
        return "JavaFxLabel{\n" +
            "text=" + getText() + "\n" +
            "}";
    }
}
