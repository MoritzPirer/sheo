package model;

public class RandomPaletteCreator implements PaletteCreator {

    private final int amount;

    public RandomPaletteCreator(int amount) {
        this.amount = amount;
    }

    @Override
    public Palette create() {
        return Palette.createRandom(amount);
    }
}
