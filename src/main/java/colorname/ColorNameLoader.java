package colorname;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

class ColorNameLoader {
    public static ImmutableIntToStringMap loadColorNames(Path colorNameFile) {
        List<String> names = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        try (BufferedReader reader = Files.newBufferedReader(colorNameFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(", ");
                if (tokens.length != 2) {
                    continue; //ignore misformatted entries
                }

                int hex = hexStringToInt(tokens[0]);
                if (hex < 0) {
                    continue;
                }

                colors.add(hex);
                names.add(tokens[1]);
            }

            int[] array = colors.stream()
                    .mapToInt(Integer::intValue)
                    .toArray();

            return new ImmutableIntToStringMap(array, names.toArray(String[]::new));
        }
        catch (IOException e) {
            return new ImmutableIntToStringMap(new int[0], new String[0]);
        }
    }

    private static int hexStringToInt(String hexString) {
        if (hexString.startsWith("#")) {
            hexString = hexString.substring(1);
        }

        try {
           return Integer.parseInt(hexString, 16);
        }
        catch (NumberFormatException e) {
            return -1;
        }
    }
}
