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
	int otherColor = color%2 + 1;
	Position move = new Position(r,c);
	if( board[r][c] != 0 ) legal = false; //check if square is empty
	if( r == 0 || c == 0 || 
		r == 10 || c == 10) legal = false; // check if square is within
						   // legal bounds.
	// Test if the move is next to the opposite color and then check if
	// the move will result in flipping the color of the nearby opposite
	// color peice.
	Position i = move.getNorth();
	if( i == otherColor ){ // if the right piece is othercolor
		while(i == otherColor){
			i = i.getNorth();
		}
		if(i == color){
			legal = true;
		} else {
			legal = false;
		}
	}
	i = move.getSouth();
	if( i == otherColor ){ // if the right piece is othercolor
		while(i == otherColor){
			i = i.getSouth();
		}
		if(i == color){
			legal = true;
		} else {
			legal = false;
		}
	}
	i = move.getEast();
	if( i == otherColor ){ // if the right piece is othercolor
		while(i == otherColor){
			i = i.getEast();
		}
		if(i == color){
			legal = true;
		} else {
			legal = false;
		}
	}
	i = move.getWest();
	if( i == otherColor ){ // if the right piece is othercolor
		while(i == otherColor){
			i = i.getWest();
		}
		if(i == color){
			legal = true;
		} else {
			legal = false;
		}
	}
	i = move.getNorthEast();
	if( i == otherColor ){ // if the right piece is othercolor
		while(i == otherColor){
			i = i.getNorthEast();
		}
		if(i == color){
			legal = true;
		} else {
			legal = false;
		}
	}
	i = move.getNorthWest();
	if( i == otherColor ){ // if the right piece is othercolor
		while(i == otherColor){
			i = i.getNorthWest();
		}
		if(i == color){
			legal = true;
		} else {
			legal = false;
		}
	}
	i = move.getSouthEast();
	if( i == otherColor ){ // if the right piece is othercolor
		while(i == otherColor){
			i = i.getSouthEast();
		}
		if(i == color){
			legal = true;
		} else {
			legal = false;
		}
	}
	i = move.getSouthWest();
	if( i == otherColor ){ // if the right piece is othercolor
		while(i == otherColor){
			i = i.getSouthWest();
		}
		if(i == color){
			legal = true;
		} else {
			legal = false;
		}
	}


/*					
	if( board[r+1][c] == otherColor ){ // if the right piece is othercolor
	}else if( board[r+1][c+1] == otherColor ){ // if the lower right
	}else if( board[r+1][c-1] == otherColor ){ // if the upper right
	}else if( board[r][c+1] == otherColor ){ // if the lower
	}else if( board[r][c-1] == otherColor ){ // if the upper
	}else if( board[r-1][c+1] == otherColor ){ // if the lower left
	}else if( board[r-1][c-1] == otherColor ){ // if the upper left
	}else if( board[r-1][c] == otherColor ){ // if the left
*/					
	/*
	if( board[r+1][c] != otherColor ){
		if ( board[r+1][c+1] != otherColor ){
			if( board[r+1][c-1] != otherColor ){	
				if( board[r][c+1] != otherColor ){
					if( board[r][c-1] != otherColor ){
						if( board[r-1][c+1] != otherColor ){
							if( board[r-1][c-1] != otherColor ){
								if( board[r-1][c] != otherColor ){
	*/
	return legal;
    }
	private void flip(Position from, int distance, int direction){
		
	}
	private class Position{
		public Position(int r, int c){
			r = r;
			c = c;
		}
		private int getNorth(){ return board[r][c-1];}
		private int getSouth(){ return board[r][c+1];}
		private int getEast(){ return board[r-1][c];}
		private int getWest(){ return board[r+1][c];}
		private int getNorthEast(){ return board[r-1][c-1];}
		private int getNorthWest(){ return board[r+1][c-1];}
		private int getSouthEast(){ return board[r-1][c+1];}
		private int getSouthWest(){ return board[r+1][c+1];}
		private int r;
		private int c;
	} 
}
