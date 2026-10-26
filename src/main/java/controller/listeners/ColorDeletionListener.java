package controller.listeners;

public interface ColorDeletionListener {
    /**
     * called when any color is removed from the palette
     * @param removedIndex the index of the color that was removed
     */
    void onRemove(int removedIndex);
}
