package userinterface.javafxinterface;

import javafx.geometry.Pos;
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

class JavaFxCanvasColorPicker extends JavaFxComponent implements ColorPicker {
    private final List<ColorPickerListener> listeners = new ArrayList<>();
    private double selectedX = 0;
    private double selectedY = 0;

    private final VBox container = new VBox();
    private final Canvas canvas;
    private final Slider hueSlider;

    public JavaFxCanvasColorPicker() {
        container.setMinSize(10, 10);
        container.setPrefSize(10, 10);
        canvas = createCanvas();
        hueSlider = createHueSlider();

        container.getChildren().addAll(canvas, hueSlider);
        drawMarker();
    }

    /**
     * create and initialize the canvas. Mouse events are relayed to processMouseInput().
     */
    private Canvas createCanvas() {
        Canvas canvas = new Canvas(100, 100);
        canvas.widthProperty().bind(container.widthProperty());
        canvas.heightProperty().bind(container.heightProperty().multiply(0.8));

        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) {
                adjustHorizontalSelectionAfterResize(oldVal.doubleValue(), newVal.doubleValue());
                drawGradient();
            }
        });

        canvas.heightProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() > 0) {
                adjustVerticalSelectionAfterResize(oldVal.doubleValue(), newVal.doubleValue());
                drawGradient();
            }
        });

        canvas.setOnMouseClicked(event -> processMouseInput(event.getX(), event.getY()));
        canvas.setOnMouseDragged(event -> processMouseInput(event.getX(), event.getY()));

        return canvas;
    }

    /**
     * correct the x-position of the selection marker within the canvas after width changes
     * to keep saturation percentage consistent
     */
    private void adjustHorizontalSelectionAfterResize(Double widthBefore, Double widthAfter) {
        double percent;
        if (widthBefore == 0) {
            percent = 0;
        }
        else {
            percent = selectedX / widthBefore;
        }
        selectedX = percent * widthAfter;
    }

    /**
     * correct the y-position of the selection marker within the canvas after height changes
     * to keep percentage consistent
     */
    private void adjustVerticalSelectionAfterResize(Double heightBefore, Double heightAfter) {
        double percent;
        if (heightBefore == 0) {
            percent = 0;
        }
        else {
            percent = selectedY / heightBefore;
        }
        selectedY = percent * heightAfter;
    }

    /**
     * create the slider that controls the hue. all changes are relayed to processSliderMove()
     */
    private Slider createHueSlider() {
        Slider slider = new Slider(0, 359, 0);

        slider.skinProperty().addListener((obs, oldSkin, newSkin) -> {
            Node track = slider.lookup(".track");
            if (track instanceof Region region) {
                region.setStyle("-fx-background-color: linear-gradient(to right," +
                    "#FF0000, #FFFF00, #00FF00, #00FFFF, #0000FF, #FF00FF, #FF0000);");
                region.prefHeightProperty().bind(slider.heightProperty().multiply(0.5));
            }

            Node thumb = slider.lookup(".thumb");
            if (thumb instanceof Region region) {
                region.prefHeightProperty().bind(slider.heightProperty().multiply(0.6));
                region.setStyle("-fx-background-radius: 25;");
            }
        });

        slider.valueProperty().addListener(
            (obs, oldVal, newVal) -> processSliderMove((Double) oldVal, (Double) newVal)
        );
        slider.prefHeightProperty().bind(container.heightProperty().multiply(0.2));
        return slider;
    }

    /**
     * draw the value / saturation gradient to the canvas. Further up is a larger value, further right
     */
    private void drawGradient() {
        GraphicsContext context = canvas.getGraphicsContext2D();
        PixelWriter writer = context.getPixelWriter();

        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        Color interpolationTarget = JavaFxConverter.toJavaFxColor(ColorUtility.fromHsv(getHue(), 1, 1));

        for (int pixelRow = 0; pixelRow <= height; pixelRow++) {
            double heightPercentage = (double) pixelRow / (height - 1);

            for (int pixelColumn = 0; pixelColumn <= width; pixelColumn++) {
                double widthPercentage = (double) pixelColumn / (width - 1);

                Color topColor = Color.WHITE.interpolate(interpolationTarget, widthPercentage);
                Color finalColor = topColor.interpolate(Color.BLACK, heightPercentage);

                writer.setColor(pixelColumn, pixelRow, finalColor);
            }
        }

        drawMarker();
    }

    /**
     * draw the maker onto the canvas
     */
    private void drawMarker() {
        double thickness = 5;

        double diameter = 5 + Math.ceil(canvas.getWidth() * 0.03);
        double radius = diameter / 2;

        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setStroke(Color.BLACK);
        context.setLineWidth(thickness);
        context.strokeOval(selectedX - radius, selectedY - radius, diameter, diameter);

        context.setStroke(Color.WHITE);
        context.setLineWidth(thickness / 2);
        context.strokeOval(selectedX - radius, selectedY - radius, diameter, diameter);
    }

    /**
     * process the mouse clicking or dragging to a new position of the canvas
     * @param x the x coordinate relative to the canvas's left edge
     * @param y the y coordinate relative to the canvas's top edge
     */
    private void processMouseInput(double x, double y) {
        double hue = hueSlider.getValue();
        double oldSaturation = getSaturation();
        double oldValue = getValue();

        // don't allow selection to leave canvas
        if (x >= 0 && x <= canvas.getWidth()) {
            selectedX = x;
        }
        if (y >= 0 && y <= canvas.getHeight()) {
            selectedY = y;
        }

        drawGradient();

        java.awt.Color before = ColorUtility.fromHsv(hue, oldSaturation, oldValue);
        java.awt.Color after = ColorUtility.fromHsv(hue, getSaturation(), getValue());

        listeners.forEach(l -> l.onChange(before, after));
    }

    /**
     * process the hue slider moving
     * @param oldPosition the hue before moving
     * @param newPosition the hue after moving
     */
    private void processSliderMove(double oldPosition, double newPosition) {
        drawGradient();

        // slider only changes hue
        double saturation = getSaturation();
        double value = getValue();

        java.awt.Color beforeAwt = ColorUtility.fromHsv(oldPosition, saturation, value);
        java.awt.Color afterAwt = ColorUtility.fromHsv(newPosition, saturation, value);

        listeners.forEach(listener -> listener.onChange(beforeAwt, afterAwt));
    }

    @Override
    public void setColor(java.awt.Color color) {
        java.awt.Color before = getColor();

        //prevent feedback loops if a listener sets the color
        if (before.equals(color)) {
            return;
        }

        double[] hsv = ColorUtility.toHsv(color);
        double hue = hsv[0];
        double saturation = hsv[1];
        double value = hsv[2];

        setHue(hue);
        setSaturation(saturation);
        setValue(value);

        drawGradient();

        listeners.forEach(l -> l.onChange(before, color));
    }

    /**
     * @return the hue of the current selection, in degrees in the range [0, 360) (including start, excluding end)
     */
    private double getHue() {
        return hueSlider.getValue();
    }

    /**
     * @return the saturation of the current selection, as a value in the (inclusive) range [0, 1]
     */
    private double getSaturation() {
        return selectedX / canvas.getWidth();
    }

    /**
     * @return the HSV-value of the current selection, as a value in the (inclusive) range [0, 1]
     */
    private double getValue() {
        // larger y-coordinate -> further down -> lower value -> invert value
        return 1 - (selectedY / canvas.getHeight());
    }

    private void setHue(double hue) {
        hueSlider.setValue(hue);
    }
    private void setSaturation(double saturation) {
        selectedX = saturation * canvas.getWidth();
    }

    private void setValue(double value) {
        selectedY = (1.0 - value) * canvas.getHeight();
    }

    @Override
    public java.awt.Color getColor() {
        System.out.println("!!getCol: " + hueSlider.getValue() + " " + getSaturation() + " " + getValue());
        return ColorUtility.fromHsv(getHue(), getSaturation(), getValue());
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
    public void setContentAlignment(ContentAlignment alignment) {
        Pos pos = JavaFxConverter.toPos(alignment);
        container.setAlignment(pos);
    }
}
