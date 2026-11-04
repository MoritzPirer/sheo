package controller.listeners;

import java.awt.*;

public interface ColorChangeListener {
    int COLOR_WAS_DELETED = -1;

    /**
     * @return what index the Listener is listening to
     */
    int getIndex();

    /**
     * called when the color at the listened index changes
     * @param before the value of the color before the change
     * @param after the value of the color after the change
     */
    void onChange(Color before, Color after);

    /**
     * called when the color at the listened index is shifted, e.g. due to insertion or deletion of colors
     * @param newIndex the new index the color that was at the listened index before changing,
     *                 or COLOR_WAS_DELETED if that color was removed
     */
    void onIndexShift(int newIndex);
}
