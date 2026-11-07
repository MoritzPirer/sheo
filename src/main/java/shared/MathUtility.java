package shared;

public class MathUtility {
    public static double max(double... options) {
        if (options.length == 0) return -1;

        double candidate = options[0];
        for (double option : options) {
            if (option > candidate) {
                candidate = option;
            }
        }

        return candidate;
    }

    public static double min(double... options) {
        if (options.length == 0) return -1;

        double candidate = options[0];
        for (double option : options) {
            if (option < candidate) {
                candidate = option;
            }
        }

        return candidate;
    }
}
