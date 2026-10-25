package view;

import model.Palette;
import userinterface.UiFactory;
import userinterface.components.UiApplication;

public class ViewManager {
    private final Palette palette;
    private final PaletteView view;


    public ViewManager(UiFactory factory, String[] args) {
        this.palette = Palette.createRandom(5);
        this.view = new PaletteView();

        UiApplication application = factory.createUiApplication("sheo");
        application.initialize(args);
        application.start(view.initializeScene(factory, palette));

        view.createListeners(palette);
    }
}
