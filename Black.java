/*
 * Black.java
 *
 * Version:
 *    $Id$
 *
 * Revisions:
 *    &Log$
 *
 */

/** 
 * This class represents a player who knows how to play the game of Othello. 
 *
 * @author     YOUR NAME GOES HERE
 *
 */

public class Black {

    /** 
     *  This method calls the appropriate strategy.
     *
     *  @param    game    the current state of the game
     *  @param    done    true if the player cannot move anywhere
     *  @param    color   the color (Black or White) of the player
     *
     *  @return   game    the resulting state of the game
     */
    public Game strategy(Game game, boolean done, int color) {
        
        return randStrategy(game,done,color);
    }

    /**
     *  Take a turn using a random strategy.
     *
     *  @param    game    the current state of the game
     *  @param    done    true if the player cannot move anywhere
     *  @param    color   the color (Black or White) of the player
     *
     *  @return   game    the resulting state of the game
     */
    public Game randStrategy(Game game, boolean done, int color) {

        int row = (int)(Math.random()*game.HEIGHT-2) + 1;
        int column = (int)(Math.random()*game.WIDTH-2) + 1;
        
        while (!done && !game.legalMove(row,column,color,true)) {
            row = (int)(Math.random()*game.HEIGHT-2) + 1;
            column = (int)(Math.random()*game.WIDTH-2) + 1;
        }
        
        if (!done)
            game.board[row][column] = color;

        return game;
    }

    /**
     *  Take a turn using another strategy.
     *
     *  @param    game    the current state of the game
     *  @param    done    true if the player cannot move anywhere
     *  @param    color   the color (Black or White) of the player
     *
     *  @return   game    the resulting state of the game
     */
    public Game strategy1(Game game, boolean done, int color) {
      
        return game;
    }
}
