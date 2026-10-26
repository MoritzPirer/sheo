package controller.listeners;

public interface ColorCreationListener {
    /**
     * called when a new color is added to the palette
     * @param addedIndex the index at which a color was added
     */
    void onAdd(int addedIndex);
}
