package view;

import userinterface.UiFactory;
import userinterface.javafxinterface.JavaFxFactory;

public class tempMain {
    public static void main(String[] args) {
        new tempMain().run(args);
    }

    public void run(String[] args) {
        UiFactory factory = new JavaFxFactory();
        ViewManager viewManager = new ViewManager(factory, args);
    }

}
