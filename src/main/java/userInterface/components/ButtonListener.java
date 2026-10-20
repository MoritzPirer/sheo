package userInterface.components;

public interface ButtonListener {
    /**
     * called when a button this listener was registered with is clicked.
     * @param source the button that caused the click. Can be used to disambiguate
     *               if multiple buttons are listened to by the same listener
     */
    void onClick(Button source);
}
