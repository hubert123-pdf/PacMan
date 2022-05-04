package src.ghosts;

import src.Ghost;
import resources.GameConsts;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;

/**
 * Pinky is a ghost, that waits for PacMan and attacks, when he comes close to him. 
 */
public class Pinky extends Ghost
{
    private int chaseMoves, waitMoves;
    private Threader thread;

    /**
     * Constructor sets default position and proper image. 
     * @throws IOException
     */
    public Pinky() throws IOException 
    {
        chaseMoves = 20;
        waitMoves = 5;
        x = GameConsts.NUM_OF_BLOCKS_X / 2 + 1;
        y = GameConsts.NUM_OF_BLOCKS_Y / 2 - 5;
        paintGhost();
    }

     /**
     * Sets proper layout for Pinky.
     */
    @Override
    protected void paintGhost() throws IOException
    {
        ghostImage = ImageIO.read(new File("resources/images/pinky.png"));
        resizedGhostImage = ghostImage.getScaledInstance(GameConsts.BLOCK_SIZE, GameConsts.BLOCK_SIZE, Image.SCALE_DEFAULT);
    }

    /**
     * This method starts new thread and moves Pinky closer to PacMan or makes him wait.
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
         * When PacMan comes close to Pinky, he makes constant number of movements, then stops and wait for him to again come closer. 
         */
        @Override
        public void run()
        {
            double currentDistanceToPlayer = calculateDistanceToPlayer(x, y);
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
        }
    }
}
