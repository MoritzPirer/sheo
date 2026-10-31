package userinterface.listeners;

import userinterface.components.DropdownSelection;

import java.util.List;

public interface DropdownSelectionListener {
    /**
     * called when a DropdownSelection this listener is listening to changes its selected value.
     * @param source the DropdownSelection that was changed. Can be used to disambiguate if one listener listens
     *               to multiple DropdownSelections
     * @param before the selected value before the change
     * @param after the selected value after the change
     */
    void onSelectionChange(DropdownSelection source, String before, String after);

    /**
     * called when a DropdownSelection this listener is listening to changes its list of allowed values
     * @param source the DropdownSelection that was changed. Can be used to disambiguate if one listener listens
     *               to multiple DropdownSelections
     * @param before the list of allowed values before the change
     * @param after the list of allowed values after the change
     */
    void onOptionsChange(DropdownSelection source, List<String> before, List<String> after);
}
