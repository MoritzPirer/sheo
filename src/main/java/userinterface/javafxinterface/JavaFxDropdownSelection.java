package userinterface.javafxinterface;

import javafx.scene.layout.Region;
import userinterface.ContentAlignment;
import userinterface.components.DropdownSelection;
import userinterface.listeners.DropdownSelectionListener;

import java.util.List;

public class JavaFxDropdownSelection extends JavaFxComponent implements DropdownSelection {
    @Override
    public void setValue(String value) {

    }

    @Override
    public String getValue() {
        return "";
    }

    @Override
    public void setOptions(List<String> options) {

    }

    @Override
    public List<String> getOptions() {
        return List.of();
    }

    @Override
    public void addDropdownSelectionListener(DropdownSelectionListener listener) {

    }

    @Override
    Region getJavaFxNode() {
        return null;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {

    }
}
