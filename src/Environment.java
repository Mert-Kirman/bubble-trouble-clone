/**
 * Class that contains game-related constants and methods
 * Also includes a method for running the game
 */

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Environment {
    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 500;
    public static final int TOTAL_GAME_DURATION = 40000;
    public static final int PAUSE_DURATION = 15;
    public static final double scaleX = 16.0;
    public static final double scaleY = 9.0;

    public Environment() {

    }

    /**
     * Method that runs the game
     */
    public static void runGame() {
        StdDraw.setCanvasSize(CANVAS_WIDTH, CANVAS_HEIGHT); // Create a canvas
        StdDraw.setXscale(0.0, scaleX);
        StdDraw.setYscale(-1.0, scaleY);
        StdDraw.enableDoubleBuffering(); // For smoother animation

        double startTime = System.currentTimeMillis(); // Get the time before starting the animation for using as a reference
        double timeDifference;
        double previousTime = startTime; // Variable that holds the time value of the previous loop
        double remainingTime;

        // Create a bar object
        Bar timeBar = new Bar();

        // Create an arraylist to store all the balls the player has to shoot
        ArrayList<Ball> balls = new ArrayList<>();
        balls.add(new Ball(0));
        balls.add(new Ball(1));
        balls.add(new Ball(2));

        // Create an arraylist to store an arrow object
        ArrayList<Arrow> arrows = new ArrayList<>();

        // Create a player object
        Player player1 = new Player();

        // Variable denoting whether the arrow is active or not
        boolean arrowActive = false;

        // Variable denoting whether the player has won the game by hitting all the balls
        boolean playerWins = false;

        // Variable denoting whether there has been a collision between a ball and the player or not
        boolean collision = false;

        while(true) {
            StdDraw.clear();

            double currentTime = System.currentTimeMillis();
            timeDifference = currentTime - previousTime; // Amount of time passed between two successive while loops
            previousTime = currentTime; // Update the variable so that it holds the current loop's time instead of the former one's
            remainingTime = TOTAL_GAME_DURATION - (currentTime - startTime); // Time left until the game ends
            StdDraw.picture(scaleX/2, scaleY/2,"background.png", scaleX,scaleY); // Set the background
            StdDraw.picture(scaleX/2, -0.5, "bar.png", scaleX, 1); // Set the floor display

            // Check arrow events if it is activated
            if(arrowActive) {
                // Check if there is any collision between the balls and the arrow
                for(int i = 0; i < balls.size(); i++) {
                    if(ballArrowCollides(balls.get(i), arrows.get(0))) {
                        if(balls.size() == 1) {
                            balls.remove(i);
                            break;
                        }
                        arrows.clear();
                        arrowActive = false; // Deactivate the arrow as it has already hit a ball

                        int levelOfBallHit = balls.get(i).getLevel();
                        double coordinateX = balls.get(i).getCoordinateX();
                        double coordinateY = balls.get(i).getCoordinateY();
                        double horizontalVelocity = balls.get(i).findHorizontalVelocity();
                        double verticalVelocity = balls.get(i).findStandardVerticalVelocity();
                        balls.remove(i); // Remove the ball that has been hit by the arrow from the array list
                        if(levelOfBallHit > 0) {
                            // Create two smaller level balls with opposite horizontal velocities
                            balls.add(new Ball(levelOfBallHit - 1, coordinateX, coordinateY, horizontalVelocity, verticalVelocity));
                            balls.add(new Ball(levelOfBallHit - 1, coordinateX, coordinateY, horizontalVelocity * -1, verticalVelocity));
                        }
                        break;
                    }
                }

                // If there is no collision between a ball and the arrow, thus if the arrow is still active
                if(arrowActive) {
                    // If the arrow reaches the canvas's top it should be deactivated
                    if(arrows.get(0).getArrowCoordinateY() >= scaleY){
                        arrows.clear();
                        arrowActive = false;
                    }
                    // Move the arrow upwards
                    else {
                        arrows.get(0).updateArrowCoordinateY(timeDifference);
                        StdDraw.picture(arrows.get(0).getArrowCoordinateX(), arrows.get(0).getArrowCoordinateY()/2, "arrow.png", 0.2,arrows.get(0).getArrowCoordinateY());
                    }
                }
            }

            // Draw the player
            StdDraw.picture(player1.getPlayerCoordinateX(), Player.PLAYER_HEIGHT/2, "player_back.png", Player.PLAYER_WIDTH, Player.PLAYER_HEIGHT);

            // If time is up the game ends
            if(remainingTime <= 0) {
                StdDraw.show();
                break;
            }

            // Set the size and color of the time bar according to the remaining time
            double lengthOfTimeBar = timeBar.timeBarLength(remainingTime);
            if(lengthOfTimeBar < 0) { // If total game duration is exceeded, break from the while loop
                break;
            }
            int barColorGreen = timeBar.findGreenColorValue(remainingTime); // Decreases in value as the game progresses
            StdDraw.setPenColor(new Color(Bar.BAR_COLOR_RED, barColorGreen, Bar.BAR_COLOR_BLUE)); // Change the bar color from yellow to red as time decreases
            StdDraw.filledRectangle(lengthOfTimeBar/2, -0.5, lengthOfTimeBar/2, Bar.HEIGHT_SCALE/2); // Bar that visualizes remaining time

            // If the player is hit by a ball game ends
            if(collision) {
                StdDraw.show();
                break;
            }

            // If all balls are hit the player wins
            if(balls.isEmpty()) {
                StdDraw.show();
                playerWins = true;
                break;
            }

            // Move the ball objects in the array list
            for(Ball ball:balls) {
                ball.updateCurrentX(timeDifference);
                ball.updateCurrentY(timeDifference);
                StdDraw.picture(ball.getCoordinateX(), ball.getCoordinateY(), "ball.png", ball.getRadius()*2, ball.getRadius()*2);
            }

            StdDraw.show();
            StdDraw.pause(PAUSE_DURATION-5);

            // Move the player left or right using keyboard input
            if(StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
                player1.updatePlayerCoordinateX(-1, timeDifference);
            }
            if(StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
                player1.updatePlayerCoordinateX(1, timeDifference);
            }

            // Activate the arrow when player presses spacebar
            if(StdDraw.isKeyPressed(KeyEvent.VK_SPACE)) {
                if(arrows.isEmpty()) { // There cannot be more than one arrow object
                    arrowActive = true;
                    arrows.add(new Arrow(player1.getPlayerCoordinateX())); // Assign player's current position on x-axis to the arrow objects starting position
                }
            }

            // Check if there is any collision between a ball and the player
            for(Ball ball:balls) {
                if(ballPlayerCollides(ball, player1)) {
                    collision = true;
                    break;
                }
            }
        }

        // After exiting the while loop, draw the game screen
        StdDraw.picture(scaleX/2, scaleY/2.18, "game_screen.png", scaleX/3.8, scaleY/4);
        StdDraw.setPenColor(Color.black);

        // If the player wins
        if(playerWins) {
            StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
            StdDraw.text(scaleX/2, scaleY/2.0,"YOU WON!");
        }

        // If time is up or player is hit by a ball
        if(remainingTime <= 0 || collision) {
            StdDraw.setFont(new Font("Helvetica", Font.BOLD, 30));
            StdDraw.text(scaleX/2, scaleY/2.0,"GAME OVER!");
        }

        // Press "y" or "n" for replaying or quitting the game
        StdDraw.setFont(new Font("Helvetica", Font.ITALIC, 15));
        StdDraw.text(scaleX/2, scaleY/2.3, "To Replay Click \"Y\"");
        StdDraw.text(scaleX/2, scaleY/2.6, "To Quit Click \"N\"");
        StdDraw.show();
    }

    /**
     * Method that checks if the ball and the player collides
     * @param ball Ball object
     * @param player Player object
     * @return True if there is collision between the ball and the player, otherwise false
     */
    public static boolean ballPlayerCollides(Ball ball, Player player) {

        // Check if the height of the bottom of the ball is lower than or equal to the player's height as this is a region with collision risk
        if(ball.getCoordinateY() - ball.getRadius() <= Player.PLAYER_HEIGHT) {

            // Check if the ball touches the player from above
            if((player.getPlayerCoordinateX() - Player.PLAYER_WIDTH * 0.5 < ball.getCoordinateX()) && (ball.getCoordinateX() <= player.getPlayerCoordinateX() + Player.PLAYER_WIDTH * 0.5)) {
                return true;
            }

            // Check if the ball touches the player from left or right sides
            if(Math.abs(ball.getCoordinateX() - player.getPlayerCoordinateX()) < Player.PLAYER_WIDTH * 0.5 + ball.getRadius()) {
                // Find the current distance squared between the ball and the player
                double ballPlayerDistanceSquared = euclideanDistanceSquared(ball.getCoordinateX(), ball.getCoordinateY(), player.getPlayerCoordinateX(), Player.PLAYER_HEIGHT * 0.5);

                // Find the square of the minimum distance required between the ball and the player for them to not collide with each other
                double minDistanceRequiredSquared = ball.getRadius() + Math.pow((Math.pow(Player.PLAYER_HEIGHT * 0.5, 2) + Math.pow(Player.PLAYER_WIDTH * 0.5, 2)), 0.5);

                if(ballPlayerDistanceSquared <= minDistanceRequiredSquared) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that finds the squared value of Euclidean distance between two points
     * @param x1 x-axis coordinate of the first point
     * @param y1 y-axis coordinate of the first point
     * @param x2 x-axis coordinate of the second point
     * @param y2 y-axis coordinate of the second point
     * @return Squared value of Euclidean distance between two points
     */
    public static double euclideanDistanceSquared(double x1, double y1, double x2, double y2) {
        return Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2);
    }

    /**
     * Method that checks if the ball and the arrow collides
     * @param ball Ball object
     * @param arrow Arrow object
     * @return True if there is collision between the ball and the arrow, otherwise false
     */
    public static boolean ballArrowCollides(Ball ball, Arrow arrow) {
        // Check if the height of the center of the ball is lower than or equal to the arrow's height as this is a region with collision risk
        if(ball.getCoordinateY() <= arrow.getArrowCoordinateY()) {

            // Check if the ball collides with the arrow along its length
            if(Math.abs(ball.getCoordinateX() - arrow.getArrowCoordinateX()) <= ball.getRadius()) {
                return true;
            }
        }

        // Check if the height of the bottom of the ball is lower than or equal to the arrow's height as this is a region with collision risk
        else if(ball.getCoordinateY() - ball.getRadius() <= arrow.getArrowCoordinateY()) {

            // Find the current distance squared between the ball and the tip of the arrow
            double ballArrowDistanceSquared = euclideanDistanceSquared(ball.getCoordinateX(), ball.getCoordinateY(), arrow.getArrowCoordinateX(), arrow.getArrowCoordinateY());

            // Find the square of the minimum distance required between the ball and the arrowhead for them to not collide with each other
            double minDistanceRequiredSquared = Math.pow(ball.getRadius(), 2);

            if(ballArrowDistanceSquared <= minDistanceRequiredSquared) {
                return true;
            }
        }
        return false;
    }

}
