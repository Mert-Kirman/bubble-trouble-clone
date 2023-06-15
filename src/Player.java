/**
 * Class that contains player object-related constants and methods
 */

public class Player {
    public final static double PERIOD_OF_PLAYER = 6000; // The total time required for a player to traverse through the X-axis
    public final static double PLAYER_HEIGHT_WIDTH_RATE = 37.0/23.0;
    public final static double PLAYER_HEIGHT_SCALEY_RATE = 1.0/8.0;
    public final static double PLAYER_WIDTH = (PLAYER_HEIGHT_SCALEY_RATE/PLAYER_HEIGHT_WIDTH_RATE) * Environment.scaleY;
    public final static double PLAYER_HEIGHT = PLAYER_WIDTH * PLAYER_HEIGHT_WIDTH_RATE;
    public final static double PLAYER_SPEED = Environment.scaleX / PERIOD_OF_PLAYER; // Horizontal speed of the player
    private double playerCoordinateX; // Current location of the player on the x-axis

    public Player() {
        this.playerCoordinateX = Environment.scaleX / 2;
    }

    /**
     * Method for getting the player object's x-coordinate
     * @return X coordinate of the player object
     */
    public double getPlayerCoordinateX() {
        return playerCoordinateX;
    }

    /**
     * Method for updating the x-axis location of the player
     * @param direction If -1, player moves to left; otherwise to right
     */
    public void updatePlayerCoordinateX(int direction, double timeDifference) {
        if(direction == -1) {
            if(this.playerCoordinateX - PLAYER_WIDTH/2 - PLAYER_SPEED * timeDifference >= 0) {
                this.playerCoordinateX -= PLAYER_SPEED * timeDifference;
            }
        }
        else {
            if(this.playerCoordinateX + PLAYER_WIDTH/2 + PLAYER_SPEED * timeDifference <= Environment.scaleX) {
                this.playerCoordinateX += PLAYER_SPEED * timeDifference;
            }
        }
    }
}
