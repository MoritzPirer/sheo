package userInterface.JavaFxInterface;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import userInterface.ContentAlignment;
import userInterface.SizeConfiguration;
import userInterface.components.Button;
import userInterface.components.ButtonListener;
import userInterface.components.UiComponent;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class JavaFxButton implements Button, JavaFxComponent{
    private final javafx.scene.control.Button button;
    private final List<ButtonListener> listeners = new ArrayList<>();

    public JavaFxButton(String text) {
        button = new javafx.scene.control.Button(text);
        button.setOnAction(event -> onClick());
    }

    private void onClick() {
        for (ButtonListener listener : listeners) {
            listener.onClick(this);
        }
    }
    @Override
    public void addListener(ButtonListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void setText(String text) {
        button.setText(text);
    }

    @Override
    public String getText() {
        return button.getText();
    }

    @Override
    public javafx.scene.Parent getJavaFxNode() {
        return button;
    }

    @Override
    public JavaFxButton setBackground(Color color) {
        javafx.scene.paint.Color javaFxColor = javafx.scene.paint.Color.rgb(
            color.getRed(),
            color.getGreen(),
            color.getBlue()
        );

        button.setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));
        return this;
    }

    @Override
    public JavaFxButton setSizeConfiguration(SizeConfiguration config) {
        JavaFxHelper.applySizeConfiguration(button, config);
        return this;
    }

    @Override
    public UiComponent setContentAlignment(ContentAlignment alignment) {
        //TODO
        return null;
    }

    @Override
    public void setImage(BufferedImage image) {
        javafx.scene.image.Image converted = SwingFXUtils.toFXImage(image, null);
        ImageView view = new ImageView(converted);
        double size = 20; //button.getHeight();
        System.out.println("size: " + size);
        view.setFitWidth(size);
        view.setFitHeight(size);
        view.setPreserveRatio(true);

        button.setGraphic(view);
    }

    @Override
    public String toString() {
        return "JavaFxButton{\n" +
            "text=" + getText() + "\n" +
            "listeners=" + listeners + "\n" +
            '}';
    }
}
