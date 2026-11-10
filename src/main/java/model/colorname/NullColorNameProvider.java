package model.colorname;

import javafx.util.Pair;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class NullColorNameProvider implements ColorNameProvider {
    @Override
    public String get(Color color) {
        return "";
    }

    @Override
    public List<Pair<Color, String>> getNearest(Color color, int count) {
        List<Pair<Color, String>> nullResult = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            nullResult.add(new Pair<>(Color.BLACK, "No color found"));
        }

        return nullResult;
    }
}
