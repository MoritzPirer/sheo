package userinterface;

public record Size(int height, int width) {

    /**
     * @return true if this can be placed in other without any part of it leaving other.
     */
    public boolean fitsWithin(Size other) {
        return height < other.height && width < other.width;
    }
}
