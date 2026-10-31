package userinterface.javafxinterface;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import userinterface.components.DropdownSelection;
import userinterface.listeners.DropdownSelectionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class JavaFxDropdownSelectionTest {
    static Stream<DropdownSelection> streamComponents() {
        return Stream.of(

        );
    }

    @ParameterizedTest
    @MethodSource("streamComponents")
    public void whenOptionsAreSet_thenOptionsAreUpdated(DropdownSelection component) {
        List<String> options = new ArrayList<>();
        options.add("Apple");
        options.add("Banana");
        options.add("Cherry");

        component.setOptions(options);

        List<String> actual = component.getOptions();
        assertIterableEquals(options, actual);
    }

    @ParameterizedTest
    @MethodSource("streamComponents")
    public void whenValueIsSet_thenValueIsUpdated(DropdownSelection component) {
        String value = "Apple";

        component.setValue(value);
        String actual = component.getValue();

        assertEquals(value, actual);
    }

    @ParameterizedTest
    @MethodSource("streamComponents")
    public void whenValueIsSet_thenListenersAreNotified(DropdownSelection component) {
        final boolean[] wasNotified = {false};

        List<String> options = new ArrayList<>();
        options.add("Apple");
        options.add("Banana");
        options.add("Cherry");

        component.addDropdownSelectionListener(new DropdownSelectionListener() {
            @Override
            public void onSelectionChange(DropdownSelection source, String before, String after) { }

            @Override
            public void onOptionsChange(DropdownSelection source, List<String> before, List<String> after) {
                wasNotified[0] = true;
            }
        });

        component.setOptions(options);

        assertTrue(wasNotified[0]);
    }


    @ParameterizedTest
    @MethodSource("streamComponents")
    private void whenOptionsAreSet_thenListenersAreNotified(DropdownSelection component) {
        final boolean[] wasNotified = {false};

        String value = "Apple";
        component.addDropdownSelectionListener(new DropdownSelectionListener() {
            @Override
            public void onSelectionChange(DropdownSelection source, String before, String after) { }

            @Override
            public void onOptionsChange(DropdownSelection source, List<String> before, List<String> after) {
                wasNotified[0] = true;
            }
        });

        component.setValue(value);

        assertTrue(wasNotified[0]);
    }
}
