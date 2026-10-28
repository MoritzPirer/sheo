package colorname;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;



class KnnColorNameProviderTest {
    private Path getResourcePath(String resourcePath) throws Exception {
        return Paths.get(
                Objects.requireNonNull(
                        getClass().getClassLoader().getResource(resourcePath),
                        "Resource not found: " + resourcePath
                ).toURI()
        );
    }

    @Test
    void givenExactMatch_exactMatchIsFound() throws Exception {
        Path path = getResourcePath("blackAndWhite.csv");
        KnnColorNameProvider knnColorNameProvider = new KnnColorNameProvider(path);

        String name = knnColorNameProvider.get(new Color(0, 0, 0)).toLowerCase();

        assertEquals("black", name);
    }

    @Test
    void givenEmptyList_peacefulFail() throws Exception {
        Path path = getResourcePath("emptyList.csv");
        KnnColorNameProvider knnColorNameProvider = new KnnColorNameProvider(path);

        String name = knnColorNameProvider.get(new Color(0, 0, 0)).toLowerCase();

        assertEquals("", name);
    }

    @Test
    void givenNearMatch_closerMatchIsFound() throws Exception {
        Path path = getResourcePath("blackAndWhite.csv");
        KnnColorNameProvider knnColorNameProvider = new KnnColorNameProvider(path);

        String name = knnColorNameProvider.get(new Color(2, 4, 123)).toLowerCase();

        assertEquals("black", name);
    }

    @Test
    void givenExtremes_pickClosest() throws Exception {
        Path path = getResourcePath("redGreenBlue.csv");
        KnnColorNameProvider knnColorNameProvider = new KnnColorNameProvider(path);

        String name = knnColorNameProvider.get(new Color(255, 254, 254)).toLowerCase();

        assertEquals("red", name);
    }

    @Test
    void givenLargeFile_findAnswerFast() throws Exception {
        Path path = getResourcePath("colornames.csv");
        KnnColorNameProvider knnColorNameProvider = new KnnColorNameProvider(path);
        long start = System.currentTimeMillis();

        //input color is close match
        String name = knnColorNameProvider.get(new Color(0, 17, 80)).toLowerCase();

        long end = System.currentTimeMillis();

        assertEquals("Interstellar Blue".toLowerCase(), name);
        assertTrue(end - start < 250);
    }
}