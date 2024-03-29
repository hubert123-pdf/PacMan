package src.ghosts;

import src.Ghost;
import resources.GameConsts;

import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.*;

/**
 * Inky is a ghost, that randomly copies movement of one of the other ghosts. 
 */
public class Inky extends Ghost
{
    private Threader thread;
    private int randomMovement, lastMove, movesInOneDirection;
    private int chaseMoves, waitMoves;
    private int movementMoves;

    /**
     * Constructor sets default position and proper image. 
     * @throws IOException
     */
    public Inky() throws IOException 
    {
        movementMoves = 30;
        chaseMoves = 20;
        waitMoves = 5;
        randomMovement = 0;
        movesInOneDirection = 3;
        lastMove = 1;
        x = GameConsts.NUM_OF_BLOCKS_X / 2 - 8;
        y = GameConsts.NUM_OF_BLOCKS_Y / 2 + 5;
        paintGhost();
    }

    /**
     * Sets proper layout for Inky.
     */
    @Override
    protected void paintGhost() throws IOException
    {
        ghostImage = ImageIO.read(new File("resources/images/inky.png"));
        resizedGhostImage = ghostImage.getScaledInstance(GameConsts.BLOCK_SIZE, GameConsts.BLOCK_SIZE, Image.SCALE_DEFAULT);
    }

    /**
     * This method starts new thread and randomly chooses Inky's movement.
     */
    @Override
    public void move(int playerPositionX, int playerPositionY) 
    {
        this.playerPositionX = playerPositionX;
        this.playerPositionY = playerPositionY;
        thread = new Threader();
        thread.start();
    }
    
    /**
     * . This class inherits from Thread class to allow multithreading in the program. 
     */
    private class Threader extends Thread
    {
        /**
         * This method sets Inky's posistion based on one of the other three ghosts movement.
         * 0 - Blinky's movement.
         * 1 - Clyde's movement.
         * 2 - Pinky's moement.
         */
        @Override
        public void run()
        {
            double currentDistanceToPlayer = calculateDistanceToPlayer(x, y);
            switch(randomMovement)
            {
                case 0: //Blinky Movement
                    if(calculateDistanceToPlayer(x + 1, y) < currentDistanceToPlayer && GameConsts.MAP[y][x + 1] == 1)
                    {
                        x++; 
                    }
                    else if(calculateDistanceToPlayer(x - 1, y) < currentDistanceToPlayer && GameConsts.MAP[y][x - 1] == 1)
                    {
                        x--;
                    }
                    else if(calculateDistanceToPlayer(x, y + 1) < currentDistanceToPlayer && GameConsts.MAP[y + 1][x] == 1)
                    {
                        y++;
                    }
                    else if(calculateDistanceToPlayer(x, y - 1) < currentDistanceToPlayer && GameConsts.MAP[y - 1][x] == 1)
                    {
                        y--;
                    }
                    break;
                case 1: //Clyde Movement
                    switch (lastMove) {
                        case 0:
                            if(GameConsts.MAP[y][x + 1] == 1)
                            {
                                x++;
                            }
                            break;
                        case 1:
                            if(GameConsts.MAP[y + 1][x] == 1)
                            {
                                y++;
                            }
                            break;
                        case 2:
                            if(GameConsts.MAP[y][x - 1] == 1)
                            {
                                x--;
                            }
                            break;
                        case 3:
                            if(GameConsts.MAP[y - 1][x] == 1)
                            {
                                y--;
                            }
                            break;
                        default:
                            break;
                    }
                    movesInOneDirection--;
                    if(movesInOneDirection == 0)
                    {
                        lastMove = getRandomDirection(0, 4);
                        movesInOneDirection = 3;
                    }
                case 2: //Pinky Movement
                    if(currentDistanceToPlayer < 10 && chaseMoves > 0)
                    {
                        if(calculateDistanceToPlayer(x + 1, y) < currentDistanceToPlayer && GameConsts.MAP[y][x + 1] == 1)
                        {
                            x++; 
                        }
                        else if(calculateDistanceToPlayer(x - 1, y) < currentDistanceToPlayer && GameConsts.MAP[y][x - 1] == 1)
                        {
                            x--;
                        }
                        else if(calculateDistanceToPlayer(x, y + 1) < currentDistanceToPlayer && GameConsts.MAP[y + 1][x] == 1)
                        {
                            y++;
                        }
                        else if(calculateDistanceToPlayer(x, y - 1) < currentDistanceToPlayer && GameConsts.MAP[y - 1][x] == 1)
                        {
                            y--;
                        }
                        chaseMoves--;
                    }
                    else
                    {
                        waitMoves--;
                        if(waitMoves < 0)
                        {
                            chaseMoves = 20;
                            waitMoves = 5;
                        }
                    }
                default:
                break;                
            }
            movementMoves--;
            if(movementMoves == 0)
            {
                randomMovement = getRandomDirection(0, 3);
                movementMoves = 30;
            }
        }
    }
}
