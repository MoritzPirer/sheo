package shared;

import java.awt.*;

public class ColorUtility {
    private static final int HEX_CODE_LENGTH = 6;

    public static String colorToHex(Color color) {
        int value = color.getRGB() & 0xFFFFFF;

        StringBuilder builder = new StringBuilder("#");

        String hex = Integer.toHexString(value);

        // add leading zeros if needed
        int paddingNeeded = HEX_CODE_LENGTH - hex.length();
        builder.repeat("0", paddingNeeded);

        return builder.append(hex).toString();
    }
}
