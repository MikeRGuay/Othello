/*
 * Othello.java
 *
 * Version:
 *    $Id$
 *
 * Revisions:
 *    &Log$
 *
 */

import java.util.*;
import java.awt.*;        
import java.awt.event.*;
import javax.swing.*;

/** 
 * This program is the driver for the game of Othello.<br> 
 *
 * Run the program as one of the following:<br>
 *     java Othello          (GUI with a default delay time of 1 second)<br>
 *     java Othello delay    (GUI with a delay of (delay) milliseconds)<br>
 *     java Othello 0        (GUI with human (Black) vs. machine (White))<br>
 *     java Othello -delay   (No GUI - run program (delay) times)<br>
 *
 * @author     Roxanne Canosa
 *
 */

public class Othello extends JPanel {

    final static int BLACK = 1;          // Declare state of each square
    final static int WHITE = 2;
    final static int EMPTY = 0;
    final static int OFFBOARD = -1;

    Black black = new Black();          // The players
    White white = new White();

    private Game game = new Game();     // Game state
    private javax.swing.Timer timer;
    private static int delay;
    private static long startTime, stopTime, runTime = 0;
    private int turn = BLACK;
    private boolean black_done = false; 
    private boolean white_done = false;
    
    /**
     *  This constructor sets up the initial game configuration, 
     *  and starts the timer with a default delay of 1 second.
     */
    public Othello() { this(1000); }

    /**
     *  This constructor sets up the initial game configuration, 
     *  and starts the timer with a user specified delay.
     *
     *  @param    delay    number of milliseconds between player moves
     */
    public Othello(int delay) {

        // Initialize the game state
        initGame(game);
       
        // Run the game with GUI - computer vs. computer using a timer
        if (delay > 0) {
            setBackground(Color.GREEN);
            timer = new javax.swing.Timer(delay, new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        playerMove();
                        repaint();
                    }
                });
            
            // Create the Start and Stop buttons
            JButton start = new JButton("Start"); 
            start.setBounds(10,20,80,25); 
            add(start);
            start.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        timer.start();
                    }
                });

            JButton stop = new JButton("Stop"); 
            stop.setBounds(10,80,80,25); 
            add(stop);
            stop.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent evt){
                        timer.stop();
                    }
                });
        }

        // Run the game with GUI - human vs. computer. 
        // The human player always plays with the black discs.
        if (delay == 0) {
            setBackground(Color.GREEN);
            addMouseListener( new MouseAdapter() {
                    public void mousePressed(MouseEvent evt) {
                        // Find out which square was clicked
                        int x = evt.getX();
                        int y = evt.getY();
                        int screenWidth = getWidth();
                        int screenHeight = getHeight();
                        int column = (x*(game.WIDTH-2))/screenWidth+1;
                        int row = (y*(game.HEIGHT-2))/screenHeight+1;
                        
                        if (!game.legalMove(row,column,BLACK,true)) 
                           System.out.println("Not a legal move - try again!");
                        else {
                            game.board[row][column] = BLACK;
                            repaint();
                            black_done = true;
                            for (int i=1; i<game.HEIGHT-1; i++)
                                for (int j=1; j<game.WIDTH-1; j++)
                                    if (game.legalMove(i,j,BLACK,false) )
                                        black_done=false;
                            whiteMove();
                        }
                    }
                });
        }

        // Run the game without the GUI - as many times as specified in delay.
        if (delay < 0) {

            // Start timing how long it takes to play "delay" games
            startTime = new Date().getTime();

            // Keep track of how many wins each color has
            int white_won = 0;
            int black_won = 0;
            int ties = 0;

            // Play a bunch of games!
            for (int times=0; times < -delay; times++) {
                initGame(game);
                boolean done = false;
                white_done = false;
                black_done = false;
         
                while (!done) {
                    playerMove();
                    int bC = 0;
                    int wC = 0;

                    for (int i=1; i<game.HEIGHT-1; i++) {        
                        for (int j=1; j<game.WIDTH-1; j++) {
                            if (game.board[i][j] == BLACK)       
                                bC++;
                            else if (game.board[i][j] == WHITE)   
                                wC++;
                        }
                    }
 
                    // Check if there are any more moves to make
                    done = true;
                    for (int i=1; i<game.HEIGHT-1; i++)
                        for (int j=1; j<game.WIDTH-1; j++)
                            if ((game.legalMove(i,j,BLACK,false)) ||
                                (game.legalMove(i,j,WHITE,false)))
                                done=false;    
                    
                    if (done) 
                        if (wC > bC) {
                            //System.out.println("White won with " + wC);
                            white_won++;
                        }
                        else if (bC > wC) {
                            //System.out.println("Black won with "+ bC);
                            black_won++;
                        }
                        else {
                            //System.out.println("Tied game");
                            ties++;
                        }
                }
            }

            stopTime = new Date().getTime();
            runTime = (stopTime - startTime);

            System.out.println("===========================");
            System.out.println("Total number of games = " + -delay);
            System.out.println("White won " + white_won + " times");
            System.out.println("Black won " + black_won + " times");
            System.out.println("Number of tied games = " + ties);
            System.out.println("\nWhite lost "+(-delay-white_won) + " times");
            System.out.print("Runtime for " + -delay + " games = ");
            System.out.println(runTime + " milliseconds");
            System.out.println("===========================");
        }
    }
   
    /** 
     *  Initialize the game state
     *
     *  @param    game    the Game state
     */
    public void initGame(Game game) {

         // Initialize off-board squares
        for (int i=0; i<game.WIDTH; i++) {     
            game.board[i][0] = OFFBOARD;
            game.board[i][game.WIDTH-1] = OFFBOARD;
            game.board[0][i] = OFFBOARD;
            game.board[game.HEIGHT-1][i] = OFFBOARD;
        }

        // Initialize game board to be empty except for initial setup
        for (int i=1; i<game.HEIGHT-1; i++)        
            for (int j=1; j<game.WIDTH-1; j++)
               game.board[i][j] = EMPTY;
        
        game.board[game.HEIGHT/2-1][game.WIDTH/2-1] = WHITE;        
        game.board[game.HEIGHT/2][game.WIDTH/2-1] = BLACK;
        game.board[game.HEIGHT/2-1][game.WIDTH/2] = BLACK;
        game.board[game.HEIGHT/2][game.WIDTH/2] = WHITE;
    }

    /**
     *  A player makes a move when the Timer goes off. Black goes
     *  first, and then Black and White take turns.
     */
    public void playerMove() {
        
        if (turn == BLACK) {
            blackMove();
            turn = WHITE;
        }
        else {
            whiteMove();
            turn = BLACK;
        }
    }

    /**
     *  Black takes a turn.
     */
    public void blackMove() {
       
        // Check if Black can move anywhere
        black_done = true;
        for (int i=1; i<game.HEIGHT-1; i++)
            for (int j=1; j<game.WIDTH-1; j++)
                if (game.legalMove(i,j,BLACK,false) ) 
                    black_done=false;

        game = black.strategy(game, black_done, BLACK);          
    } 

    /**
     *  White takes a turn.
     */
    public void whiteMove() {

        // Check if White can move
        white_done = true;
        for (int i=1; i<game.HEIGHT-1; i++)
            for (int j=1; j<game.WIDTH-1; j++)
                if (game.legalMove(i,j,WHITE,false) )
                    white_done=false;
        
        game = white.strategy(game, white_done, WHITE);
    }

   /**
    *  Draw the board and the current state of the game. 
    *
    *  @param    g    the graphics context of the game
    */
   public void paintComponent(Graphics g) {
      
       super.paintComponent(g);  // Fill panel with background color
       
       int width = getWidth();
       int height = getHeight();
       int xoff = width/(game.WIDTH-2);
       int yoff = height/(game.HEIGHT-2);

       int bCount=0;                     
       int wCount=0;                        

       // Draw the lines on the board
       g.setColor(Color.BLACK);
       for (int i=1; i<=game.HEIGHT-2; i++) {        
           g.drawLine(i*xoff, 0, i*xoff, height);
           g.drawLine(0, i*yoff, width, i*yoff);
       }

       // Draw discs on the board and show the legal moves
       for (int i=1; i<game.HEIGHT-1; i++) {        
           for (int j=1; j<game.WIDTH-1; j++) {
               // Draw the discs
               if (game.board[i][j] == BLACK) {       
                   g.setColor(Color.BLACK);
                   g.fillOval((j*yoff)-yoff+5,(i*xoff)-xoff+5,50,50); 
                   bCount++;
               }
               else if (game.board[i][j] == WHITE) {  
                   g.setColor(Color.WHITE);
                   g.fillOval((j*yoff)-yoff+5,(i*xoff)-xoff+5,50,50);
                   wCount++;
               }
               // Show the legal moves for the current player
               if (turn == BLACK && game.legalMove(i,j,BLACK,false)) {
                   g.setColor(Color.BLACK);
                   g.fillOval((j*yoff+27)-yoff,(i*xoff+27)-xoff,6,6);
               }
               // If other player cannot move, current player cleans up
               if (turn == WHITE && game.legalMove(i,j,WHITE,false)) {
                   g.setColor(Color.WHITE);
                   g.fillOval((j*yoff+27)-yoff,(i*xoff+27)-xoff,6,6);
               }
           }
       }
 
       // Check if there are any more moves to make
       boolean done = true;
       for (int i=1; i<game.HEIGHT-1; i++)
           for (int j=1; j<game.WIDTH-1; j++)
               if ((game.legalMove(i,j,BLACK,false)) ||
                   (game.legalMove(i,j,WHITE,false)))
                   done=false;

       g.setColor(Color.RED);
       if (done) {
           if (wCount > bCount)
               g.drawString("White won with " + wCount + " discs.",10,20);
           else if (bCount > wCount)
               g.drawString("Black won with " + bCount + " discs.",10,20);
           else g.drawString("Tied game",10,20);
       }
       else {     
           if (wCount > bCount)
               g.drawString("White is winning with " + wCount+" discs",10,20);
           else if (bCount > wCount)
               g.drawString("Black is winning with " + bCount+" discs",10,20);
           else g.drawString("Currently tied",10,20);
       }
      
   }

    /**
     * The main program.
     *
     * @param    args    command line arguments (ignored)
     */
    public static void main(String [] args) {

        Othello content;

        if (args.length > 1) {
            System.out.println("Usage: java Othello delayTime");
            System.exit(0);
        }

        if (args.length == 1) {   
            try {
                delay = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException e) {
                System.out.println("Command line arg must be an integer");
                System.exit(0);
            }
            content = new Othello(delay);
            if (delay >= 0) {
                JFrame window = new JFrame("Othello Game");
                window.setContentPane(content);
                window.setSize(492,516);
                window.setLocation(100,100);
                window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
                window.setVisible(true);
            }
        }
        else {
            content = new Othello();
            JFrame window = new JFrame("Othello Game");
            window.setContentPane(content);
            window.setSize(492,516);
            window.setLocation(100,100);
            window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
            window.setVisible(true);
        }
    }
}  // Othello