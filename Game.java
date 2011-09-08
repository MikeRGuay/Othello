/*
 * Game.java
 *
 * Version:
 *    $Id$
 *
 * Revisions:
 *    &Log$
 *
 */

/** 
 * This class specifies the game state for a 2-dimensional board game.
 *
 * @author     YOUR NAME GOES HERE
 *
 */

public class Game {
    
    final static int BLACK = 1;          // Declare state of each square
    final static int WHITE = 2;
    final static int EMPTY = 0;
    final static int WIDTH = 10;
    final static int HEIGHT = 10;
    final static int board[][] = new int[WIDTH][HEIGHT];

    /**
     *  Decide if the move is legal
     *
     *  @param    r          row in the game matrix
     *  @param    c          column in the game matrix
     *  @param    color      color of the player - Black or White
     *  @param    flip       true if the player wants to flip the discs
     *
     *  @return              true if the move is legal, else false
     */
    public boolean legalMove(int r, int c, int color, boolean flip) {
        boolean legal = true;
	if( board[r][c] != 0 ) legal = false; //check if square is empty
	if( r == 0 || c == 0 || 
		r == 10 || c == 10) legal = false; // check if square is within
						   // legal bounds.
	
	
	
        return legal;
    }
}
