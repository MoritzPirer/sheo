package userinterface.javafxinterface;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import userinterface.ContentAlignment;
import userinterface.Padding;
import userinterface.components.GridContainer;
import userinterface.components.UiComponent;

import java.util.ArrayList;
import java.util.List;


public class JavaFxGridContainer extends JavaFxComponent implements GridContainer {
    private final GridPane gridPane;
    private final int rows;
    private final int columns;
    private final int capacity;
    private int size;

    public JavaFxGridContainer(int rows, int columns) {
        gridPane = new GridPane();
        this.rows = rows;
        this.columns = columns;
        capacity = rows * columns;
        size = 0;

        if (rows < 1 || columns < 1) {
            throw new IllegalArgumentException("Rows and columns must be greater than 0.");
        }

        for (int i = 0; i < columns; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / columns);
            colConst.setHgrow(Priority.ALWAYS);
            gridPane.getColumnConstraints().add(colConst);
        }

        for (int i = 0; i < rows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / rows);
            rowConst.setVgrow(Priority.ALWAYS);
            gridPane.getRowConstraints().add(rowConst);
        }
    }

    @Override
    public void addChild(UiComponent uiComponent) {
        if (size == capacity) {
            throw new IllegalStateException("Capacity exceeded");
        }

        if (!(uiComponent instanceof JavaFxComponent fxComponent)) {
            throw new IllegalArgumentException("UiComponent must be of type JavaFxComponent.");
        }

        int insertRow = size / columns;
        int insertColumn = size % columns;

        gridPane.add(fxComponent.getJavaFxNode(), insertColumn, insertRow);
        size++;
    }

    @Override
    public void insertChild(int row, int column, UiComponent uiComponent) {
        throw new UnsupportedOperationException("TODO");
    }

    @Override
    public void removeChild(UiComponent uiComponent) {
        if (!(uiComponent instanceof JavaFxComponent fxComponent)) {
            throw new IllegalArgumentException("UiComponent must be of type JavaFxComponent.");
        }

        List<Node> children = new ArrayList<>(gridPane.getChildren());

        if (!children.contains(fxComponent.getJavaFxNode())) {
            return;
        }
        children.remove(fxComponent.getJavaFxNode());

        children.sort(this::compare);

        gridPane.getChildren().clear();

        for (int i = 0; i < children.size(); i++) {
            Node child = children.get(i);
            int newCol = i % columns;
            int newRow = i / columns;

            gridPane.add(child, newCol, newRow);
        }

        size--;
    }

    private int compare(Node a, Node b) {
        int rowA = getRow(a);
        int columnA = getColumn(a);
        int rowB = getRow(b);
        int columnB = getColumn(b);

        if (rowA != rowB) {
            return Integer.compare(rowA, rowB);
        }
        return Integer.compare(columnA, columnB);
    }

    private int getRow(Node node) {
        Integer row = GridPane.getRowIndex(node);
        if (row == null) {
            return 0;
        }

        return row;
    }

    private int getColumn(Node node) {
        Integer column = GridPane.getColumnIndex(node);
        if (column == null) {
            return 0;
        }

        return column;
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void setPadding(Padding padding) {
        Insets insets = JavaFxConverter.toInsets(padding);
        gridPane.setPadding(insets);
    }

    @Override
    public void setSpacing(double spacing) {
        gridPane.setHgap(spacing);
        gridPane.setVgap(spacing);
    }

    @Override
    Region getJavaFxNode() {
        return gridPane;
    }

    @Override
    public void setContentAlignment(ContentAlignment alignment) {
        gridPane.setAlignment(JavaFxConverter.toPos(alignment));
    }

    @Override
    public int getRowCount() {
        return rows;
    }

    @Override
    public int getColumnCount() {
        return columns;
    }
}
