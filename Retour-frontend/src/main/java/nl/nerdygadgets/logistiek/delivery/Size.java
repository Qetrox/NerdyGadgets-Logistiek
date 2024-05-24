package nl.nerdygadgets.logistiek.delivery;

public class Size {

    private int width;
    private int height;
    private int depth;

    public Size(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getVolume() {
        return width * height * depth;
    }

    public boolean fitsIn(Size size) {
        return width <= size.getWidth() && height <= size.getHeight() && depth <= size.getDepth();
    }

    public boolean equals(Size size) {
        return width == size.getWidth() && height == size.getHeight() && depth == size.getDepth();
    }

    public String toString() {
        return width + "x" + height + "x" + depth;
    }

}
