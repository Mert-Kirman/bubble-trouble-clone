/**
 * Class that contains arrow object-related constants and methods
 */

public class Arrow {
    public final static double PERIOD_OF_ARROW = 1500; // The total time required for an arrow to traverse through the Y axis excluding bar
    public final static double ARROW_VELOCITY = Environment.scaleY / PERIOD_OF_ARROW; // Vertical velocity of the arrow
    private double arrowCoordinateX; // Current location of the arrow object on the x-axis
    private double arrowCoordinateY; // Current location of the arrow object on the y-axis

    public Arrow(double playerCoordinateX){
        this.arrowCoordinateX = playerCoordinateX; // Arrow starts from the player's exact position in the X-axis when the spacebar is pressed
        this.arrowCoordinateY = 0; // Arrow always starts from the ground level
    }

    /**
     * Method for getting the arrow object's x-coordinate
     * @return X coordinate of the arrow object
     */
    public double getArrowCoordinateX() {
        return arrowCoordinateX;
    }

    /**
     * Method for getting the arrow object's y-coordinate
     * @return Y coordinate of the arrow object
     */
    public double getArrowCoordinateY() {
        return arrowCoordinateY;
    }

    /**
     * Method for updating the y-coordinate of the arrow object
     * @param timeDifference Time passed between two while loops in the environment class method "runGame"
     */
    public void updateArrowCoordinateY(double timeDifference) {
        this.arrowCoordinateY += ARROW_VELOCITY * timeDifference;
    }
}
