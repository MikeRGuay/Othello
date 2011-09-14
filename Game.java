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
 * @author     Mike Guay 
 * */ 
public class Game {
    
    final static int BLACK = 1;          // Declare state of each square
    final static int WHITE = 2;
    final static int EMPTY = 0;
    final static int WIDTH = 10;
    final static int HEIGHT = 10;
    final static int board[][] = new int[WIDTH][HEIGHT];
	private enum Direction { NORTH, SOUTH, EAST, WEST, NORTHEAST, NORTHWEST, 
		SOUTHEAST, SOUTHEAST }

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
	int otherColor = color%2 + 1;
	Position move = new Position(r,c);
	if( board[r][c] != 0 ) legal = false; //check if square is empty
	if( r == 0 || c == 0 || 
		r == 10 || c == 10) legal = false; // check if square is within
						   // legal bounds.
	// Test if the move is next to the opposite color and then check if
	// the move will result in flipping the color of the nearby opposite
	// color peice.
	for( Direction direction; Direction.values()){
		Position i = move.getPiece(direction);
		int flips = 1;
		while( i.color == otherColor ){
			flips++;
			i = i.getPiece(direction);
		}
		if( flips > 1 ){
			legal = true;
			if(flip) flipPieces(move, flips, direction, color);	
		}
	}	
	return legal;
    }
	private void flipPieces(Position from, int distance, int direction, int color){
		if( distance > 0 ){
			int r = from.getRow();
			int c = from.getColumn();
			board[r][c] = color;
			if( distance > 1 ){
				flipPieces(from.getPeice(direction), distance-1, direction, color);
			}
		}
	}
	private class Position{
		public Position(int r, int c){
			this.r = r;
			this.c = c;
		}
		private int getPiece(Direction dir){
			switch(dir){
				case NORTH: 
					return board[r][c-1];
				case SOUTH: 
					return board[r][c+1];
				case EAST: 
					return board[r-1][c];
				case WEST: 
					return board[r+1][c];
				case NORTHEAST: 
					return board[r-1][c-1];
				case NORTHWEST: 
					return board[r+1][c-1];
				case SOUTHEAST: 
					return board[r-1][c+1];
				case SOUTHWEST: 
					return board[r+1][c+1];
				default:
					return board[r][c];
			}
		}
		private int getRow(){
			return r;
		}
		private int getColumn(){
			return c;
		}
		private int r;
		private int c;
	} 
}
