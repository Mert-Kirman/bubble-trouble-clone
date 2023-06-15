# Bubble Trouble Clone

This java program runs a clone of the game known as “Bubble Trouble”.

## How It Works

The program has many classes that hold information to create objects such as balls of different
sizes, a time bar, player character and arrow for in game use. Time bar starts as yellow in
color and shows how much game time is left. As the game progresses, the time bar shrinks and
becomes red. The ball objects are given different starting locations on the canvas according to
their levels. They do projectile motion and again have different maximum heights they can
reach once they bounce from the ground, again determined by their levels. There is a
function in the ball class that calculates if any of the balls will exceed the game screen
boundaries. If any of them does, the algorithm reverses their vertical or horizontal speeds in
order to make them bounce off of the left, right walls or the ground; i.e. making them do an
ellastic collision. Player object on the other hand can only move to left and right within the
boundaries of the game screen, moving in a constant horizontal speed. Finally, the arrow
object starts from the ground level and from where the player presses spacebar to activate it.
It moves upwards in a constant speed until it reaches the upper boundary of the game
screen, after which it disappears.

The algorithm that runs the game starts by setting up a canvas of desired size and creating
ball objects of different levels (radius), which are then stored in a list. The method then
gets the system time in order to make time calculations for moving the ball, player, time bar
and arrow objects. Remaining time is found by calculating the difference between the total
game duration and time passed since the game started. Also, the time passed between each
game frame is stored with the name “time difference”. After these time calculations, the
code checks if the arrow object is activated by the player by pressing the spacebar key. If
arrow is activated, a method is used to check if any of the balls stored in the previously
mentioned list collides with the arrow. If there is collision, the ball that has been hit is
removed from the list and instead two new smaller balls with a decreased level are added to
this list. If the ball that has been hit is of level 0 however, it cannot split and thus no further
balls are added in its place. After this, the arrow object is immediately deactivated. If
however there has been no collision between the arrow and any of the balls yet, the
algorithm calculates how much distance it should go by using “time difference” between the
current frame and previous frame and moves the arrow object upwards. After this, the code
also moves balls according to their vertical and horizontal velocities and “time difference”.
The animation frame is then showed to the player and player input is waited for a short
amount of time. These events repeat in each loop until one of the following three happens:
the ball hits the player, time runs out or all balls are hit. In the first scenario, an algorithm
checks if there is any collision between the balls stored in the list and the player by using
mathematical inequalities. If there is collision, the program exits the loop and shows a
“Game Over” screen to the player. If time runs out, the loop again ends and player is shown
a game over screen. If however the player manages to hit every ball stored in the list within
the given time, the loop again ends but this time the player is shown a “You Won” screen.
In both the game over and winning scenes the player is asked to play the game again or quit.
If the “Y” button is pressed, the main method calls for the method that starts the game again.
If “N” is pressed, the java program ends.

### Prerequisites

- An IDE or text editor to run the Java code.

- StdDraw Library

## Running the tests

The player will use left-right arrow keys to move and space to fire arrow.
