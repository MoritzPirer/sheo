package userinterface.components;

public interface GridContainer extends FixedSizeUiContainer {
    int getRowCount();
    int getColumnCount();

    /**
     * Place the given component at the given coordinate, moving all subsequent elements (row-major). If the
     * specified coordinates would leave a gap between elements, the component is moved to a different coordinate
     * @param row the requested row
     * @param column the requested column
     * @param uiComponent the component to add
     * @throws IllegalStateException if the container does not have room for the additional element
     */
    void insertChild(int row, int column, UiComponent uiComponent);
}