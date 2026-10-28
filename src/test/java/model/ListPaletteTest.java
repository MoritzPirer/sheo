package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;

public class ListPaletteTest {
    @Test
    public void givenEmptyPalette_whenSizeIsZero() {
        ListPalette palette = new ListPalette();

        int count = palette.size();

        assertEquals(0, count);
    }

    @Test
    public void givenEmptyPalette_whenGetColor_thenThrows() {
        ListPalette palette = new ListPalette();
        assertThrows(IllegalArgumentException.class, () -> palette.getColor(0));
    }

    @Test
    public void givenEmptyPalette_whenSettingColor_thenThrows() {
        ListPalette palette = new ListPalette();
        assertThrows(IllegalArgumentException.class, () -> palette.setColor(0, new Color(255, 0, 0)));
    }

    @Test
    public void givenEmptyPalette_whenDeletingColor_thenThrows() {
        ListPalette palette = new ListPalette();
        assertThrows(IllegalArgumentException.class, () -> palette.deleteColor(0));
    }

    @Test
    public void givenPalette_whenAddingColor_thenColorIsAdded() {
        ListPalette palette = new ListPalette();

        Color color = new Color(0, 0, 0);
        palette.addColor(color);

        Color read = palette.getColor(0);

        assertEquals(color, read);
    }

    @Test
    public void givenPalette_whenAddingRandomColor_thenColorIsAdded() {
        ListPalette palette = new ListPalette();

        palette.addColor(new Color(255, 0, 0));
        int count = palette.size();

        assertEquals(1, count);
    }

    @Test
    public void givenPalette_whenRemovingColor_thenColorIsRemoved() {
        ListPalette palette = new ListPalette();
        palette.addColor(new Color(0, 0, 0));

        palette.deleteColor(0);
        int count = palette.size();

        assertEquals(0, count);
    }

    @Test
    public void givenPalette_whenRemovingInvalidIndex_thenThrows() {
        ListPalette palette = new ListPalette();
        palette.addColor(new Color(255, 0, 0));

        assertThrows(IllegalArgumentException.class, () -> palette.deleteColor(-1));
        assertThrows(IllegalArgumentException.class, () -> palette.deleteColor(1));
    }

    @Test
    public void givenPalette_whenReadingInvalidIndex_thenThrows() {
        ListPalette palette = new ListPalette();
        palette.addColor(new Color(255, 0, 0));

        assertThrows(IllegalArgumentException.class, () -> palette.getColor(-1));
        assertThrows(IllegalArgumentException.class, () -> palette.getColor(1));
    }

    @Test
    public void givenPalette_whenSettingInvalidIndex_thenThrows() {
        ListPalette palette = new ListPalette();
        palette.addColor(new Color(255, 0, 0));

        Color color = new Color(0, 0, 0);

        assertThrows(IllegalArgumentException.class, () -> palette.setColor(-1, color));
        assertThrows(IllegalArgumentException.class, () -> palette.setColor(1, color));
    }

}
