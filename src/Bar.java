/**
 * Class that contains time bar-related constants and methods
 */

public class Bar {
    public final static double HEIGHT_SCALE = 0.5; // Indicates the time bar’s scale on top of the bar image
    public final static int BAR_COLOR_RED = 225;
    public final static int BAR_COLOR_BLUE = 0;

    public Bar() {
    }

    /**
     * Method that calculates the length of the time bar according to time remaining
     * @param remainingTime Time left before the game ends
     * @return Length of the time bar
     */
    public double timeBarLength(double remainingTime) {
        return Environment.scaleX * (remainingTime/Environment.TOTAL_GAME_DURATION);
    }

    /**
     * Method that calculates the green color value of the time bar according to time remaining
     * @param remainingTime Time left before the game ends
     * @return Green color value of the time bar
     */
    public int findGreenColorValue(double remainingTime) {
        return (int)(225 * (remainingTime/Environment.TOTAL_GAME_DURATION));
    }
}
