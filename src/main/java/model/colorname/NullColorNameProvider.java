package model.colorname;

import java.awt.*;

public class NullColorNameProvider implements ColorNameProvider {
    @Override
    public String get(Color color) {
        return "";
    }
}
