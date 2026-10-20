package userInterface.JavaFxInterface;

import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import userInterface.ContentAlignment;
import userInterface.SizeConfiguration;
import userInterface.components.Label;
import userInterface.components.UiComponent;

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
    public JavaFxLabel setBackground(Color color) {
        javafx.scene.paint.Color javaFxColor = javafx.scene.paint.Color.rgb(
                color.getRed(),
                color.getGreen(),
                color.getBlue()
        );

        label.setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));
        return this;
    }

    @Override
    public JavaFxLabel setSizeConfiguration(SizeConfiguration config) {
        JavaFxHelper.applySizeConfiguration(label, config);
        return this;
    }

    @Override
    public JavaFxLabel setContentAlignment(ContentAlignment alignment) {
        label.setAlignment(JavaFxHelper.toPos(alignment));
        return this;
    }

    @Override
    public String toString() {
        return "JavaFxLabel{\n" +
            "text=" + getText() + "\n" +
            "}";
    }
}
