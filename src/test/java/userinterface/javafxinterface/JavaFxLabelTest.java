package userinterface.javafxinterface;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import userinterface.components.Label;

import java.util.stream.Stream;

public class JavaFxLabelTest extends JavaFxTest{

    static Stream<Label> streamComponents() {
        return Stream.of(
            new JavaFxLabel(""),
            new JavaFxButton("")
        );
    }

    @ParameterizedTest
    @MethodSource("streamComponents")
    void whenTextIsSet_thenGetReturnsNewText(Label component) {
        String newText = "Hello World!";
        component.setText(newText);

        Assertions.assertEquals(newText, component.getText());
    }
}
