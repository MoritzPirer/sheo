package userinterface.components;

/**
 * An Object that is not itself an Ui component but contains a component and benefits from being treated uniformly
 * with ui components in containers
 */
public interface UiComponentWrapper {

    /**
     * @return the ui component being wrapped
     */
    UiComponent getUiComponent();
}
