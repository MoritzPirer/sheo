package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class RandomPaletteCreatorTest {
    @Test
    public void whenAmountIsZero_returnsEmptyPalette() {
        PaletteCreator creator = new RandomPaletteCreator(0);
        ListPalette palette = creator.create();

        assertEquals(0, palette.size());
    }

    @Test
    public void whenAmountIsNegative_thenThrows() {
        assertThrows(IllegalArgumentException.class, () -> new RandomPaletteCreator(-1));
    }

    @Test
    public void whenAmountIsPositive_returnsPalette() {
        PaletteCreator creator = new RandomPaletteCreator(5);
        ListPalette palette = creator.create();

        assertEquals(5, palette.size());
    }
}
