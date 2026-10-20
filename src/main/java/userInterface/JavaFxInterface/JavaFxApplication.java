package userInterface.JavaFxInterface;

import javafx.stage.Stage;
import javafx.application.Platform;
import userInterface.components.Scene;
import userInterface.components.UiApplication;
import java.util.concurrent.CountDownLatch;

class JavaFxApplication implements UiApplication {
    private final String title;
    private Stage stage;
    private JavaFxScene scene;

    public JavaFxApplication(String title) {
        this.title = title;
    }

    @Override
    public void initialize(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {
            try {
                stage = new Stage();
                stage.setTitle(title);
            } finally {
                latch.countDown();
            }
        });

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Failed to initialize JavaFX runtime", e);
        }
    }

    @Override
    public void start(Scene scene) {
        Platform.runLater(() -> {
            if (scene instanceof JavaFxScene javaFxScene) {
                this.scene = javaFxScene;
                stage.setScene(javaFxScene.getScene());
                System.out.println("running!!!");
                stage.show();
            }
            else {
                throw new IllegalArgumentException("Type mismatch: Expected JavaFxScene");
            }
        });
    }

    @Override
    public String toString() {
        return "JavaFxApplication{\n" +
                "title='" + title + "'\n" +
                "scene="  + scene + "\n" +
                '}';
    }
}
