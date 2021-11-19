package fxtriangulate;

/**
 * Snap to a target, to make a UI more usable, if small position differences
 * matter.
 *
 * @author "Pieter van den Hombergh {@code p.vandenhombergh@fontys.nl}"
 */
public interface SnapTarget {

    /**
     * What range is considered nearby?.
     * @return 
     */
    default double rangeRadius() {
        return 50.0;
    }

    /**
     * Are the x and y as coordinates nearby.
     * @param x  to test
     * @param y to test
     * @return  nearby or not
     */
    default boolean inRange( double x, double y ) {
        return distanceTo( x, y ) < rangeRadius();
    }

    /**
     * The Pythagorean distance between this and the given coordinate. 
     * @param x to measure
     * @param y to measure
     * @return the distance
     */
    default double distanceTo( double x, double y ) {
        double xd = x - getLayoutX();
        double yd = y - getLayoutY();
        return Math.sqrt( xd * xd + yd * yd );

    }

    /**
     * Change the node to appear different, so the UI can show it is targeted.
     * @param hot hotness on or off
     * @return return this
     */
    SnapTarget makeHot( boolean hot );

    /**
     * Return the relevant coordinate.
     * @return the coordinate
     */
    double getLayoutX();

    /**
     * Return the relevant coordinate.
     * @return the coordinate
     */
    double getLayoutY();
}
