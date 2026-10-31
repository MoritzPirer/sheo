package userinterface.components;

import userinterface.listeners.DropdownSelectionListener;

import java.util.List;

public interface DropdownSelection extends UiComponent {
    /**
     * Sets what the current value of the selection is. Notifies listeners unless that value was already selected.
     * @param value the value to set to.
     * @throws IllegalArgumentException if the given value is not part of the list of values
     */
    void setValue(String value);

    /**
     * @return the currently selected value
     */
    String getValue();

    /**
     * Replaces the current list of options with the given list. Notifies listeners unless
     * the options and order were not changed
     * @param options the new list of option for the dropdown
     * @throws IllegalArgumentException if options is null or an empty list.
     */
    void setOptions(List<String> options);

    /**
     * @return the list of current options
     */
    List<String> getOptions();

    /**
     * add a listener to be notified when this DropdownSelection changes
     * @param listener the listener to add
     */
    void addDropdownSelectionListener(DropdownSelectionListener listener);
}
