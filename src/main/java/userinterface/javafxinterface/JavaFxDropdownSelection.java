package userinterface.javafxinterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import userinterface.ContentAlignment;
import userinterface.components.DropdownSelection;
import userinterface.listeners.DropdownSelectionListener;

import java.util.ArrayList;
import java.util.List;

public class JavaFxDropdownSelection extends JavaFxComponent implements DropdownSelection {
    private final ComboBox<String> comboBox;
    private final List<DropdownSelectionListener> listeners = new ArrayList<>();

    public JavaFxDropdownSelection(String... initialOptions) {
        ObservableList<String> options = FXCollections.observableArrayList(initialOptions);
        comboBox = new ComboBox<>(options);
        comboBox.setEditable(false);
        if  (initialOptions.length > 0) {
            comboBox.setValue(initialOptions[0]);
        }

        comboBox.valueProperty() .addListener((observable, oldValue, newValue) ->
            processUserSelection(oldValue, newValue)
        );
    }

    private void processUserSelection(String before, String after) {
        listeners.forEach(l -> l.onSelectionChange(this, before, after));
    }


    @Override
    public void setValue(String value) {
        if (comboBox.getValue() != null && comboBox.getValue().equals(value)) {
            return;
        }

        String before = comboBox.getValue();

        if (!comboBox.getItems().contains(value)) {
            comboBox.getItems().add(value);
        }

        comboBox.setValue(value);

        listeners.forEach(l -> l.onSelectionChange(this, before, value));
    }

    @Override
    public String getValue() {
        return comboBox.getValue();
    }

    @Override
    public void setOptions(List<String> options) {
        ObservableList<String> convertedOptions = FXCollections.observableList(options);

        if (comboBox.getItems().equals(convertedOptions)) {
            return;
        }

        List<String> before = getOptions();
        comboBox.setItems(convertedOptions);


        listeners.forEach(l -> l.onOptionsChange(this, before, options));
    }

    @Override
    public List<String> getOptions() {
        return new ArrayList<>(comboBox.getItems());
    }

    @Override
    public void addDropdownSelectionListener(DropdownSelectionListener listener) {
        listeners.add(listener);
    }

    @Override
    Region getJavaFxNode() {
        return comboBox;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        //not applicable
    }
}
