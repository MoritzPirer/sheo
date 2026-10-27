package userinterface.javafxinterface;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.image.PixelWriter;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import shared.ColorUtility;
import userinterface.ContentAlignment;
import userinterface.components.ColorPicker;
import userinterface.listeners.ColorPickerListener;

import java.util.List;
import java.util.ArrayList;

public class JavaFxColorPicker extends JavaFxComponent implements ColorPicker {
    private final javafx.scene.control.ColorPicker colorPicker = new javafx.scene.control.ColorPicker();
    private final List<ColorPickerListener> listeners = new ArrayList<>();
    private double markerX = 0;
    private double markerY = 0;

    private final VBox container;
    private final Canvas canvas;
    private final Slider hueSlider;

    public JavaFxColorPicker() {
        container = new VBox();
        canvas = new Canvas(100, 100);
        canvas.widthProperty().bind(container.widthProperty());
        canvas.heightProperty().bind(container.heightProperty().multiply(0.8));

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) {
                drawGradient();
            }
        });
        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) {
                drawGradient();
            }
        });;

        hueSlider = createHueSlider();
        hueSlider.prefHeightProperty().bind(container.heightProperty().multiply(0.2));

        container.getChildren().addAll(canvas, hueSlider);
        canvas.setOnMouseClicked(event -> handleMouse(event.getX(), event.getY()));
        canvas.setOnMouseDragged(event -> handleMouse(event.getX(), event.getY()));
    }

    private Slider createHueSlider() {
        Slider slider = new Slider(0, 359, 0);

        slider.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            Node track = slider.lookup(".track");
            if (track instanceof Region region) {
                region.setStyle("-fx-background-color: linear-gradient(to right," +
                    "#FF0000, #FFFF00, #00FF00, #00FFFF, #0000FF, #FF00FF, #FF0000);");
                region.prefHeightProperty().bind(slider.heightProperty().multiply(0.7));
            }
            Node thumb = slider.lookup(".thumb");
            if (thumb instanceof Region region) {
                region.prefHeightProperty().bind(slider.heightProperty());
                region.setStyle("-fx-background-radius: 25%;");
            }
        });

        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            processSliderMove((Double) oldVal, (Double) newVal);
        });
        return slider;
    }

    private void drawGradient() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        PixelWriter writer = context.getPixelWriter();

        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        Color interpolationTarget = JavaFxConverter.toJavaFxColor(ColorUtility.fromHsv(hueSlider.getValue(), 1, 1));
        for (int y = 0; y < height; y++) {
            double v = (double) y / (height - 1); // 0 at top, 1 at bottom
            for (int x = 0; x < width; x++) {
                double u = (double) x / (width - 1); // 0 at left, 1 at right

                Color topColor = Color.WHITE.interpolate(interpolationTarget, u);
                Color finalColor = topColor.interpolate(Color.BLACK, v);

                writer.setColor(x, y, finalColor);
            }
        }

        drawMarker();
    }

    private void drawMarker() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeOval(markerX - 4, markerY - 4, 8, 8);
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.strokeOval(markerX - 4, markerY - 4, 8, 8);
    }

    private void handleMouse(double x, double y) {
        double hue = hueSlider.getValue();
        double oldSaturation = getSaturation();
        double oldValue = getValue();

        if (x >= 0 && x <= canvas.getWidth()) {
            markerX = x;
        }
        if (y >= 0 && y <= canvas.getHeight()) {
            markerY = y;
        }
        drawGradient();

        java.awt.Color before = ColorUtility.fromHsv(hue, oldSaturation, oldValue);
        java.awt.Color after = ColorUtility.fromHsv(hue, getSaturation(), getValue());

        listeners.forEach(l -> l.onChange(before, after));
    }

    private void processSliderMove(double oldPosition, double newPosition) {
        drawGradient();
        double saturation = getSaturation();
        double value = getValue();

        java.awt.Color beforeAwt = ColorUtility.fromHsv(oldPosition, saturation, value);
        java.awt.Color afterAwt = ColorUtility.fromHsv(newPosition, saturation, value);

        listeners.forEach(listener -> listener.onChange(beforeAwt, afterAwt));
    }

    @Override
    public void setColor(java.awt.Color color) {
        java.awt.Color before = readColor();
        if (before.equals(color)) {
            return;
        }

        double[] hsv = ColorUtility.toHsv(color);
        double hue = hsv[0];
        double saturation = hsv[1];
        double value = hsv[2];

        hueSlider.setValue(hue);
        markerX = saturation * canvas.getWidth();
        markerY = value * canvas.getHeight();

        drawGradient();

        listeners.forEach(l -> l.onChange(before, color));
    }

    private java.awt.Color readColor() {
        return ColorUtility.fromHsv(hueSlider.getValue(), getSaturation(), getValue());
    }

    private double getSaturation() {
        return markerX / canvas.getWidth();
    }

    private double getValue() {
        return 1 - (markerY / canvas.getHeight());
    }

    @Override
    public java.awt.Color getColor() {
        Color javaFxColor = colorPicker.getValue();
        return JavaFxConverter.toAwtColor(javaFxColor);
    }

    @Override
    public void addListener(ColorPickerListener listener) {
        listeners.add(listener);
    }

    @Override
    Region getJavaFxNode() {
        return container;
    }

    @Override
    public void setBackground(java.awt.Color color) {
        Color javaFxColor = JavaFxConverter.toJavaFxColor(color);
        colorPicker.setBackground(new Background(new BackgroundFill(javaFxColor, null, null)));
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        throw new UnsupportedOperationException("not applicable");
    }

    @Override
    public void hide() {
        colorPicker.setVisible(false);
    }

    @Override
    public void show() {
        colorPicker.setVisible(true);
    }
}
