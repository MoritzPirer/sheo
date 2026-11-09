package view.paletteview;

import userinterface.UiFactory;
import userinterface.components.*;
import userinterface.listeners.DropdownSelectionListener;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ColorSuggestionHandler {
    private final UiContainer container;
    private final DropdownSelection dropdown;
    private Map<String, Suggestion> suggestions;
    private final UiSlot slot;
    private Suggestion activeSuggestion;

    public ColorSuggestionHandler(UiFactory factory) {
        container = factory.createVerticalUiContainer();

        createSuggestions(factory);
        dropdown = factory.createDropdownSelection(suggestions.keySet().toArray(String[]::new));
        dropdown.addDropdownSelectionListener(new DropdownSelectionListener() {

            @Override
            public void onSelectionChange(DropdownSelection source, String before, String after) {
                Suggestion suggestion = suggestions.get(after);
                slot.setChild(suggestion.getUiComponent());
                suggestion.onSelect();
            }

            @Override
            public void onOptionsChange(DropdownSelection source, List<String> before, List<String> after) {
                throw new IllegalStateException("onOptionsChange should never be called here");
            }
        });

        slot = factory.createUiSlot();
        activeSuggestion = suggestions.get("Shade");
        slot.setChild(activeSuggestion.getUiComponent());

        container.addChild(dropdown);
        container.addChild(slot);
        dropdown.setPercentageWidth(100.0);
        slot.setPercentageWidth(100.0);
    }

    void createSuggestions(UiFactory factory) {
        suggestions = new LinkedHashMap<>();
        Suggestion suggestion = new ShadeSuggestion(factory);
        suggestions.put(suggestion.getName(), suggestion);
    }

    public void setActiveColor(Color newColor) {
        activeSuggestion.onColorChange(newColor);
    }

    public UiContainer getUiComponent() {
        return container;
    }
}
