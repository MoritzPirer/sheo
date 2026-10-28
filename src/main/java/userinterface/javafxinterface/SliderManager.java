package userinterface.javafxinterface;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

interface SliderManager {
    void initialize(Pane parent, JavaFxSliderColorPicker context);
    Color get();
    void set(Color color);

}
