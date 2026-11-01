package userinterface.javafxinterface;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import userinterface.components.ColorPicker;

import java.awt.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JavaFxColorPickerTest extends JavaFxTest {

    static Stream<ColorPicker> streamComponents() {
        return Stream.of(
            new JavaFxCanvasColorPicker(),
            new JavaFxSliderColorPicker(new RgbSliderManager()),
            new JavaFxSliderColorPicker(new HsvSliderManager()),
            new JavaFxSliderColorPicker(new HslSliderManager()),
            new JavaFxTextBoxColorPicker()
        );
    }

    @ParameterizedTest
    @MethodSource("streamComponents")
    public void whenColorIsSet_thenGetReturnsNewColor(ColorPicker component) {
        layoutComponent(component);
        Color newColor = new Color(255, 255, 0);
        component.setColor(newColor);

        assertEquals(newColor, component.getColor(), "Fail for implementation=" + component.getClass().getSimpleName());
    }

    @ParameterizedTest
    @MethodSource("streamComponents")
    public void whenColorIsSet_thenListenersAreNotified(ColorPicker component) {
        final boolean[] wasNotified = {false};

        layoutComponent(component);
        component.addListener((before, after) ->  wasNotified[0] = true);

        component.setColor(new Color(255, 255, 0));

        assertTrue(wasNotified[0]);
    }
}
