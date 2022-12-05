
/**
 * This class represents dimensions.
 * The reason it was created is only the checkStyle annoying error.
 * (maximal number of arguments when passing to constructor of GameLevel).
 */
public class Dimensions {
    private int horizontalBound;
    private int verticalBound;
    /**
     * constructs a Dimensions object from horizontal and vertical Bounds.
     * @param horizontalBound the given horizontalBound.
     * @param verticalBound the given verticalBound.
     */
    public Dimensions(int horizontalBound, int verticalBound) {
        this.horizontalBound = horizontalBound;
        this.verticalBound = verticalBound;
    }
    /**
     * a getter for the horizontalBound.
     * @return the horizontalBound.
     */
    public int horizontalBound() {
        return this.horizontalBound;
    }
    /**
     * a getter for the verticalBound.
     * @return the verticalBound.
     */
    public int verticalBound() {
        return this.verticalBound;
    }
}
