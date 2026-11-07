package shared.colorconversion;

import java.awt.*;

class ColorStringConverter {
    private static final int HEX_CODE_LENGTH = 6;

    public static String colorToHexString(Color color) {
        int value = color.getRGB() & 0xFFFFFF;

        StringBuilder builder = new StringBuilder("#");

        String hex = Integer.toHexString(value);

        // add leading zeros if needed
        int paddingNeeded = HEX_CODE_LENGTH - hex.length();
        builder.repeat("0", paddingNeeded);

        return builder.append(hex).toString();
    }

    public static boolean isHexString(String hex) {
        if (hex.length() > HEX_CODE_LENGTH + 1 || hex.length() < HEX_CODE_LENGTH) {
            return false;
        }
        if (hex.length() == HEX_CODE_LENGTH + 1) {
            if (hex.startsWith("#")) {
                hex = hex.substring(1);
            }
            else {
                return false;
            }
        }

        for (char c : hex.toCharArray()) {
            if (!Character.isDigit(c) && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')) {
                return false;
            }
        }

        return true;
    }

    public static Color hexStringToColor(String hex) {
        assert isHexString(hex);

        if (hex.startsWith("#")) {
            hex = hex.substring(1);
        }

        if (hex.isEmpty()) {
            return new Color(0, 0, 0);
        }

        int rgb = Integer.parseInt(hex, 16);
        return new Color(rgb);
    }
}
