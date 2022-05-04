package src.ghosts;

import src.Ghost;
import resources.GameConsts;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;

/**
 * Blinky is a ghost, that is constatnly chasing PacMan.
 */
public class Blinky extends Ghost
{
    private int chaseMoves, retreatMoves;
    private boolean chaseOrRetreat;
    private Threader thread;

    /**
     * Constructor sets default position and proper image. 
     * @throws IOException
     */
    public Blinky() throws IOException 
    {
        chaseMoves = 0;
        retreatMoves = 0;
        chaseOrRetreat = true; 
        x = GameConsts.NUM_OF_BLOCKS_X  - 2;
        y = GameConsts.NUM_OF_BLOCKS_Y  - 2;
        paintGhost();
    }
    /**
     * Sets proper layout for Blinky.
     */
    @Override
    protected void paintGhost() throws IOException
    {
        ghostImage = ImageIO.read(new File("resources/images/blinky.png"));
        resizedGhostImage = ghostImage.getScaledInstance(GameConsts.BLOCK_SIZE, GameConsts.BLOCK_SIZE, Image.SCALE_DEFAULT);
    }
    /**
     * This method starts new thread and basing on the player's position, moves blinky towards PacMan.
     * @param playerPositionX PacMan's x position.
     * @param playerPositionY PacMan's y position.
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
         * This method calculates distance to pacman and if it can find a closer position, the ghost makes a move. 
         * After few chasing moves, ghost needs to retreat to prevent defeat too soon. 
         */
        @Override
        public void run()
        {
            double currentDistanceToPlayer = calculateDistanceToPlayer(x, y);
            if(chaseOrRetreat)
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
                chaseMoves++;
                if(chaseMoves == 13)
                {
                    chaseOrRetreat = false;
                    chaseMoves = 0;
                }
            }
            else
            {
                if(calculateDistanceToPlayer(x + 1, y) > currentDistanceToPlayer && GameConsts.MAP[y][x + 1] == 1)
                {
                    x++;
                }
                else if(calculateDistanceToPlayer(x - 1, y) > currentDistanceToPlayer && GameConsts.MAP[y][x - 1] == 1)
                {
                    x--;
                }
                else if(calculateDistanceToPlayer(x, y + 1) > currentDistanceToPlayer && GameConsts.MAP[y + 1][x] == 1)
                {
                    y++;
                }
                else if(calculateDistanceToPlayer(x, y - 1) > currentDistanceToPlayer && GameConsts.MAP[y - 1][x] == 1)
                {
                    y--;
                }
                retreatMoves++;
                if(retreatMoves == 1)
                {
                    chaseOrRetreat = true;
                    retreatMoves = 0;
                }
            }
        }
    }
}
