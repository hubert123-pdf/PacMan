package src.ghosts;

import src.Ghost;
import resources.GameConsts;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.*;

/**
 * Clyde is a ghost, that randomly moves in a possible direction. 
 */
public class Clyde extends Ghost
{
    private int movesInOneDirection;
    private int lastMove;
    Threader  thread;

    /**
     * Constructor sets default position and proper image. 
     * @throws IOException
     */
    public Clyde() throws IOException 
    {   
        lastMove = 0;
        movesInOneDirection = 3;
        x = GameConsts.NUM_OF_BLOCKS_X / 2 + 7;
        y = GameConsts.NUM_OF_BLOCKS_Y / 2 - 11;
        paintGhost();
    }

    /**
     * Sets proper layout for Clyde.
     */
    @Override
    protected void paintGhost() throws IOException
    {
        ghostImage = ImageIO.read(new File("resources/images/clyde.png"));
        resizedGhostImage = ghostImage.getScaledInstance(GameConsts.BLOCK_SIZE, GameConsts.BLOCK_SIZE, Image.SCALE_DEFAULT);
    }

    /**
     * This method starts new thread and randomly moves clyde to new position.
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
         * This method moves Clyde to the new postion and after few moves the direction is randomly changed.
         */
        @Override
        public void run()
        {
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
        }
    }
}
