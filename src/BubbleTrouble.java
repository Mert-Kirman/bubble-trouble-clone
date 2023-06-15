/**
 * Program that initializes and runs the game by calling the Environment class method "runGame"
 * Game is replayable so if the player wants to play again or exit the game, the program proceeds accordingly
 */

import java.awt.event.KeyEvent;

public class BubbleTrouble {
    public static void main(String[] args) {
        // Start the game
        Environment.runGame();

        while(true) {
            // Player presses "Y" to play the game again
            if(StdDraw.isKeyPressed(KeyEvent.VK_Y)) {
                Environment.runGame();
            }
            // Player presses "N" to exit the game
            else if(StdDraw.isKeyPressed(KeyEvent.VK_N)) {
                System.exit(0);
            }
        }
    }
}