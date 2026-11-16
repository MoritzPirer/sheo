package view.paletteview.coloreditor;

import userinterface.UiFactory;
import userinterface.components.*;
import userinterface.listeners.DropdownSelectionListener;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ColorSuggestionHandler {
    private final UiContainer container;
    private Map<String, Suggestor> suggestions;
    private final UiSlot slot;
    private Suggestor activeSuggestor;
    private Color activeColor;

    public ColorSuggestionHandler(UiFactory factory, ColorEditor context) {
        container = factory.createVerticalUiContainer();

        createSuggestions(factory, context);
        DropdownSelection dropdown = createDropdown(factory, suggestions.keySet().toArray(String[]::new));

        activeSuggestor = suggestions.get("Shade");

        slot = factory.createUiSlot();
        slot.setChild(activeSuggestor.getUiComponent());

        container.addChildren(dropdown, slot);

        dropdown.setPercentageWidth(100.0);
        slot.setPercentageWidth(100.0);
    }

    DropdownSelection createDropdown(UiFactory factory, String[] options) {
        DropdownSelection dropdown = factory.createDropdownSelection(options);
        dropdown.addDropdownSelectionListener(new DropdownSelectionListener() {
            @Override
            public void onSelectionChange(DropdownSelection source, String before, String after) {
                activeSuggestor = suggestions.get(after);
                slot.setChild(activeSuggestor.getUiComponent());
                activeSuggestor.updateSuggestions(activeColor);
            }

            @Override
            public void onOptionsChange(DropdownSelection source, List<String> before, List<String> after) {
                throw new IllegalStateException("onOptionsChange should never be called here");
            }
        });

        return dropdown;
    }

    void createSuggestions(UiFactory factory, ColorEditor context) {
        suggestions = new LinkedHashMap<>();
        for (Suggestor suggestor : Suggestor.createAllSuggestors(factory, context)) {
            suggestions.put(suggestor.getName(), suggestor);
        }
    }

    public void setActiveColor(Color newColor) {
        activeColor = newColor;
        activeSuggestor.updateSuggestions(newColor);
    }

    public UiContainer getUiComponent() {
        return container;
    }
}
