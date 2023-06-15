/**
 * Class that contains ball object-related constants and methods
 */

public class Ball {
    public final static int PERIOD_OF_BALL = 15000; // The total time required for a ball to traverse through the whole X axis
    public final static double HEIGHT_MULTIPLIER = 1.75;
    public final static double RADIUS_MULTIPLIER = 2.0;
    public final static double MIN_POSSIBLE_HEIGHT = Player.PLAYER_HEIGHT * 1.4; // Minimum level ball’s maximum height that it can reach starting from ground
    public final static double MIN_POSSIBLE_RADIUS = Environment.scaleY * 0.0175; // Minimum level ball’s radius
    public final static double GRAVITY = 0.0000004 * Environment.scaleY; // Gravitational constant
    private int level; // Level of the ball
    private double coordinateX; // Current x-coordinate of the ball
    private double coordinateY; // Current y-coordinate of the ball
    private double horizontalVelocity; // Horizontal velocity of the ball
    private double verticalVelocity; // Vertical velocity of the ball
    private double radius; // Radius of the ball

    public Ball(int level) {
        this.level = level;
        this.coordinateX = startingPointFinder();
        this.coordinateY = 0.5;
        this.radius = findRadius();
        this.horizontalVelocity = findHorizontalVelocity();
        this.verticalVelocity = 0;
    }

    public Ball(int level, double coordinateX, double coordinateY, double horizontalVelocity, double verticalVelocity) {
        this.level = level;
        this.coordinateX = coordinateX;
        this.coordinateY = coordinateY;
        this.radius = findRadius();
        this.horizontalVelocity = horizontalVelocity;
        this.verticalVelocity = verticalVelocity;
    }

    /**
     * Method for getting the ball object's level
     * @return Level of the current ball object
     */
    public int getLevel() {
        return level;
    }

    /**
     * Method for getting the ball object's x-coordinate
     * @return X-coordinate of the current ball object
     */
    public double getCoordinateX() {
        return coordinateX;
    }

    /**
     * Method for getting the ball object's y-coordinate
     * @return Y-coordinate of the current ball object
     */
    public double getCoordinateY() {
        return coordinateY;
    }

    /**
     * Method for getting the ball object's radius
     * @return Radius of the current ball object
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Method that returns the starting x-coordinate of a ball according to its level
     * @return Starting x-coordinate of the ball object
     */
    public double startingPointFinder() {
        if(this.level == 1) {
            return Environment.scaleX/3;
        }
        else {
            return Environment.scaleX/4;
        }
    }

    /**
     * Method that returns the starting horizontal velocity of a ball according to its level
     * @return Starting horizontal velocity of the ball
     */
    public double findHorizontalVelocity() {
        if(this.level == 1) {
            return -(Environment.scaleX)/PERIOD_OF_BALL; // Ball moves left if its level is 1
        }
        else {
            return (Environment.scaleX)/PERIOD_OF_BALL; // Ball moves right if its level is 0 or 2
        }
    }

    /**
     * Method that calculates the radius of the current ball object according to its level
     * @return Radius of the ball object
     */
    public double findRadius() {
        return MIN_POSSIBLE_RADIUS * Math.pow(RADIUS_MULTIPLIER, this.level);
    }

    /**
     * Method that calculates the maximum height that different levels of balls can reach
     * @return Max possible height the current ball object can reach
     */
    public double findMaxHeight() {
        return MIN_POSSIBLE_HEIGHT * Math.pow(HEIGHT_MULTIPLIER, this.level);
    }

    /**
     * Method for updating the ball object's x-coordinate
     */
    public void updateCurrentX(double timeDifference) {
        // If the ball hits the left border of the canvas it will bounce elastically to right
        if(this.coordinateX - this.radius + this.horizontalVelocity * timeDifference < 0) {
            this.coordinateX = this.radius;
            this.horizontalVelocity *= -1; // Horizontal velocity of the ball is inverted
        }
        // If the ball hits the right border of the canvas it will bounce elastically to left
        else if(this.coordinateX + this.radius + this.horizontalVelocity * timeDifference > Environment.scaleX) {
            this.coordinateX = Environment.scaleX - this.radius;
            this.horizontalVelocity *= -1;
        }
        // If the ball does not hit left or right borders, its horizontal velocity will not change
        else {
            this.coordinateX += this.horizontalVelocity * timeDifference;
        }
    }

    /**
     * Method for updating the ball object's y-coordinate
     */
    public void updateCurrentY(double timeDifference) {
        // If the ball goes under the floor in the next move, prevent it by setting its current vertical position to its radius and inverting its vertical speed
        if(this.coordinateY - this.radius + this.verticalVelocity * timeDifference - 0.5 * GRAVITY * Math.pow(timeDifference, 2) < 0) {
            this.coordinateY = this.radius;
            updateVerticalVelocity(timeDifference);
        }
        // If the ball will be inside the boundaries of the canvas in the next move, update its vertical location and velocity
        else {
            this.coordinateY += this.verticalVelocity * timeDifference - 0.5 * GRAVITY * Math.pow(timeDifference, 2);
            updateVerticalVelocity(timeDifference);
        }
    }

    /**
     * Update the vertical velocity of the current ball object
     * @param timeDifference Amount of time passed between two successive while loops
     */
    public void updateVerticalVelocity(double timeDifference) {
        // If the ball goes under the floor in the next move, invert its vertical velocity to make it bounce elastically from the ground
        if(this.coordinateY - this.radius + this.verticalVelocity * timeDifference - 0.5 * GRAVITY * Math.pow(timeDifference, 2) < 0) {
            this.verticalVelocity = findStandardVerticalVelocity();
        }

        else {
            this.verticalVelocity -= GRAVITY * timeDifference;
        }
    }

    /**
     * Method that calculates the max speed a ball with a specific level can reach if left from its maximum height
     * @return Max speed the current ball can reach when left from its maximum height
     */
    public double findStandardVerticalVelocity() {
        return Math.pow(2 * findMaxHeight() * GRAVITY, 0.5);
    }

}
