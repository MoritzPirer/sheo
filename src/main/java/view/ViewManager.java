package view;

import controller.Controller;
import model.Palette;
import userinterface.UiFactory;
import userinterface.components.Scene;
import userinterface.components.UiApplication;

public class ViewManager {
    private UiApplication application;
    private final UiFactory factory;
    private PaletteView view;

    public ViewManager(UiFactory factory, String[] args) {
        this.factory = factory;
    }

    public void initialize(String[] args) {
        application = factory.createUiApplication("sheo");
        application.initialize(args);
        //application.start(view.initializeScene(factory, palette));

    }

    public void switchToEditor(Controller controller) {
        view = new PaletteView();
        Scene scene = view.initializeScene(factory, controller);
        application.start(scene);
        view.createListeners(controller);
    }
}
