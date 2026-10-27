package model;

import shared.random.Random;
import shared.random.RandomnessProvider;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class RandomPaletteCreator implements PaletteCreator {

    private final int amount;

    public RandomPaletteCreator(int amount) {
        this.amount = amount;
    }

    @Override
    public Palette create() {
        RandomnessProvider random = new Random();
        List<Color> colors = new ArrayList<>(amount);

        for (int i = 0; i < amount; i++) {
            colors.add(random.makeColor());
        }

        return new Palette(colors);
    }
}
