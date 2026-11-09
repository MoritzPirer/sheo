package view.paletteview;

import controller.Controller;
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
    private Map<String, Suggestor> suggestions;
    private final UiSlot slot;
    private Suggestor activeSuggestor;
    private Color activeColor;

    public ColorSuggestionHandler(UiFactory factory, Color initialColor, ColorEditor context) {
        container = factory.createVerticalUiContainer();

        createSuggestions(factory, context);
        dropdown = factory.createDropdownSelection(suggestions.keySet().toArray(String[]::new));
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

        activeColor = initialColor;

        slot = factory.createUiSlot();
        activeSuggestor = suggestions.get("Shade");
        slot.setChild(activeSuggestor.getUiComponent());
        activeSuggestor.updateSuggestions(activeColor);

        container.addChild(dropdown);
        container.addChild(slot);
        dropdown.setPercentageWidth(100.0);
        slot.setPercentageWidth(100.0);
    }

    void createSuggestions(UiFactory factory, ColorEditor context) {
        suggestions = new LinkedHashMap<>();
        for (Suggestor suggestor : Suggestor.makeAllSuggestors(factory, context)) {
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
