package userInterface.components;

/**
 * Encapsulates all resources the UI providing library needs to run
 */
public interface UiApplication {
    /**
     * brings the application into a state where components can be created and combined.
     * @param args all arguments given to the program through the commandline
     */
    void initialize(String[] args);

    /**
     * starts displaying the given scene
     * @param scene the contents to be shown right away
     */
    void start(Scene scene) throws Exception;
}
