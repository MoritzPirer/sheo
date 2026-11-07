package userinterface.javafxinterface;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import shared.colorconversion.ColorUtility;
import userinterface.ContentAlignment;
import userinterface.components.ColorPicker;
import userinterface.listeners.ColorPickerListener;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class JavaFxTextBoxColorPicker extends JavaFxComponent implements ColorPicker {
    private final TextField textField;
    private final List<ColorPickerListener> listeners = new ArrayList<>();
    private Color color;

    public JavaFxTextBoxColorPicker() {
        textField = new TextField();
        textField.setPromptText("#000000");
        textField.setText("#000000");
        textField.setEditable(true);

        EventHandler<ActionEvent> eventHandler = event -> processUserEnter();
        textField.setOnAction(eventHandler); // when enter is pressed

        color = new Color(0, 0, 0);
    }

    private void processUserEnter() {
        String hex = textField.getText();
        if (!ColorUtility.isHexString(hex)) {
            textField.setText(ColorUtility.colorToHexString(color));
            return;
        }

        Color newColor = ColorUtility.hexToColor(hex);
        if (color.equals(newColor)) {
            return;
        }

        //textField.setPromptText(textField.getText());
        Color oldColor = color;
        color = newColor;

        listeners.forEach(l -> l.onChange(this, oldColor, newColor));
    }

    @Override
    public void setColor(Color color) {
        if (this.color.equals(color)) {
            return;
        }

        Color before = this.color;
        this.color = color;
        String hex = ColorUtility.colorToHexString(color);
        textField.setText(hex);
        textField.setPromptText(hex);

        listeners.forEach(l -> l.onChange(this, before, color));
    }

    @Override
    public Color getColor() {
        return color;
    }


    @Override
    public void addListener(ColorPickerListener listener) {
        if (listener != null && !listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    @Override
    Region getJavaFxNode() {
        return textField;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        Pos pos = JavaFxConverter.toPos(alignment);
        textField.setAlignment(pos);
    }
}
