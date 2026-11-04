package view;

import controller.Controller;
import userinterface.UiFactory;
import userinterface.components.Scene;
import userinterface.components.UiApplication;
import view.paletteview.PaletteView;

public class ViewManager {
    private UiApplication application;
    private final UiFactory factory;
    private PaletteView view;

    public ViewManager(UiFactory factory) {
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
    }

    public void openEditor(int colorIndex) {
        view.openEditor(colorIndex);
    }
}
