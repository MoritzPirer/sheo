package userinterface.javafxinterface;

import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Region;
import userinterface.ContentAlignment;
import userinterface.components.Button;
import userinterface.listeners.ButtonListener;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

class JavaFxButton extends JavaFxComponent implements Button {
    private final javafx.scene.control.Button button;
    private final List<ButtonListener> listeners = new ArrayList<>();

    public JavaFxButton(String text) {
        button = new javafx.scene.control.Button(text);
        button.setOnAction(event -> onClick());
    }

    /**
     * called by javaFx's click listener. Relays click to listeners with internal format
     */
    private void onClick() {
        for (ButtonListener listener : listeners) {
            listener.onClick(this);
        }
    }

    @Override
    public void addButtonListener(ButtonListener listener) {
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
    public Region getJavaFxNode() {
        return button;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        Pos pos = JavaFxConverter.toPos(alignment);
        button.setAlignment(pos);
    }

    @Override
    public void setImage(BufferedImage image) {
        javafx.scene.image.Image converted = SwingFXUtils.toFXImage(image, null);
        ImageView view = new ImageView(converted);
        view.fitHeightProperty().bind(button.prefHeightProperty());
        view.fitWidthProperty().bind(button.prefHeightProperty());
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
