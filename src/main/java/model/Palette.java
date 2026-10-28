package model;

import java.awt.*;

public interface Palette {

    /**
     * @return the number of colors in the palette
     */
    int size();

    /**
     * @param index the index of the color to retrieve.
     * @return the requested color
     * @throws IllegalArgumentException if the index is negative or >= size()
     */
    Color getColor(int index);

    /**
     * updates the color at the given index to the given value
     * @param index the index to set at
     * @param color the color to set to
     * @throws IllegalArgumentException if the index is negative or >= size()
     */
    void setColor(int index, Color color);

    /**
     * @param index the index of the color to delete.
     * @throws IllegalArgumentException if the index is negative or >= size()
     */
    void deleteColor(int index);

    /**
     * add the given color to the end of the palette
     * @param color the color to add
     */
    void addColor(Color color);
}
